package io.github.artynova.mediaworks.client.macula;

import io.github.artynova.mediaworks.logic.macula.MaculaContent;
import io.github.artynova.mediaworks.logic.macula.MaculaSerializer;
import io.github.artynova.mediaworks.logic.macula.TextVisage;
import io.github.artynova.mediaworks.logic.macula.VisageEntry;
import io.github.artynova.mediaworks.networking.MediaworksNetworking;
import io.github.artynova.mediaworks.networking.macula.SyncMaculaDimensionsC2SMsg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.util.ArrayList;
import java.util.List;

/** Client mirror and renderer for the player's Macula/visage overlay. */
public final class MaculaClient {
    private static final Minecraft CLIENT = Minecraft.getInstance();
    private static final List<PreparedTextVisage> PREPARED = new ArrayList<>();
    private static int lastWidth = -1, lastHeight = -1;

    private MaculaClient() {}

    public static void syncFromServer(net.minecraft.nbt.CompoundTag tag) {
        if (CLIENT.level == null) return;
        setVisages(MaculaSerializer.getContent(tag, CLIENT.level.getGameTime()));
    }

    private static void setVisages(MaculaContent content) {
        content.sortByDepth();
        PREPARED.clear();
        for (VisageEntry entry : content) {
            if (entry.getVisage() instanceof TextVisage text) PREPARED.add(new PreparedTextVisage(entry, text));
        }
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        if (CLIENT.level == null) return;
        int width = CLIENT.getWindow().getGuiScaledWidth();
        int height = CLIENT.getWindow().getGuiScaledHeight();
        if (width != lastWidth || height != lastHeight) sendDimensions(width, height);
        PREPARED.removeIf(prepared -> prepared.done(CLIENT.level.getGameTime()));
    }

    public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        sendDimensions(CLIENT.getWindow().getGuiScaledWidth(), CLIENT.getWindow().getGuiScaledHeight());
    }

    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        PREPARED.clear();
        lastWidth = lastHeight = -1;
    }

    public static void onGuiRender(RenderGuiEvent.Post event) {
        if (CLIENT.level == null) return;
        long gameTime = CLIENT.level.getGameTime();
        for (PreparedTextVisage visage : PREPARED) visage.render(event.getGuiGraphics(), gameTime);
    }

    private static void sendDimensions(int width, int height) {
        if (CLIENT.getConnection() == null) return;
        lastWidth = width;
        lastHeight = height;
        MediaworksNetworking.sendToServer(new SyncMaculaDimensionsC2SMsg(width, height));
    }

    private record PreparedTextVisage(VisageEntry entry, List<FormattedCharSequence> lines) {
        PreparedTextVisage(VisageEntry entry, TextVisage visage) {
            this(entry, wrap(visage));
        }

        private static List<FormattedCharSequence> wrap(TextVisage visage) {
            int width = visage.getWidth() < 0 ? CLIENT.font.width(visage.getText()) : visage.getWidth();
            List<FormattedCharSequence> wrapped = CLIENT.font.split(visage.getText(), Math.max(1, width));
            int maxLines = visage.getHeight() < 0 ? wrapped.size()
                    : Math.min(wrapped.size(), Math.max(0, visage.getHeight() / CLIENT.font.lineHeight));
            return List.copyOf(wrapped.subList(0, maxLines));
        }

        void render(GuiGraphics graphics, long gameTime) {
            int alpha = 255;
            if (entry.getEndTime() >= 0 && entry.doFadeout()) {
                alpha = Mth.clamp((int) ((entry.getEndTime() - gameTime) * 255 / VisageEntry.FADE_TICKS), 0, 255);
            }
            if (alpha < 8) return;
            int color = alpha << 24 | 0xFFFFFF;
            int y = entry.getOrigin().getY();
            for (FormattedCharSequence line : lines) {
                graphics.drawString(CLIENT.font, line, entry.getOrigin().getX(), y, color, true);
                y += CLIENT.font.lineHeight;
            }
        }

        boolean done(long gameTime) { return entry.hasTimedOut(gameTime); }
    }
}

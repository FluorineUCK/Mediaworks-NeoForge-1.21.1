package io.github.artynova.mediaworks.logic.macula;

import io.github.artynova.mediaworks.MediaworksAttachments;
import io.github.artynova.mediaworks.networking.MediaworksNetworking;
import io.github.artynova.mediaworks.networking.macula.SyncMaculaContentS2CMsg;
import net.minecraft.server.level.ServerPlayer;

public final class MaculaServer {
    private MaculaServer() {}
    public static Macula getMacula(ServerPlayer player) { return MediaworksAttachments.macula(player); }
    public static void handleJoin(ServerPlayer player) { syncContentToClient(player); }
    public static void handleQuit(ServerPlayer player) {
        getMacula(player).trim();
    }
    public static void handleClone(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean wonGame) {
        Macula oldMacula = getMacula(oldPlayer), newMacula = getMacula(newPlayer);
        newMacula.setWidth(oldMacula.getWidth()); newMacula.setHeight(oldMacula.getHeight());
        if (wonGame) newMacula.setContent(oldMacula.getContent()); else syncContentToClient(newPlayer);
    }
    public static void syncContentToClient(ServerPlayer player) {
        MediaworksNetworking.sendToPlayer(player, SyncMaculaContentS2CMsg.fromMacula(getMacula(player), player.registryAccess()));
    }
    public static void syncDimensionsFromClient(ServerPlayer player, int width, int height) {
        getMacula(player).setWidth(width); getMacula(player).setHeight(height);
    }
}

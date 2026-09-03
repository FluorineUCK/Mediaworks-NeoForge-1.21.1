package io.github.artynova.mediaworks.client.projection;

import io.github.artynova.mediaworks.logic.projection.AstralPosition;
import io.github.artynova.mediaworks.networking.MediaworksNetworking;
import io.github.artynova.mediaworks.networking.projection.CastAstralIotaC2SMsg;
import io.github.artynova.mediaworks.networking.projection.EndProjectionC2SMsg;
import io.github.artynova.mediaworks.networking.projection.SyncAstralPositionC2SMsg;
import io.github.artynova.mediaworks.util.HexUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

/** NeoForge event implementation of the original free-camera, fog and input pipeline. */
public final class AstralProjectionClient {
    private static final Minecraft CLIENT = Minecraft.getInstance();
    private static AstralCamera astralCamera;
    private static AstralAmbienceLoop ambienceLoop;
    private static boolean shaderLoaded;

    private AstralProjectionClient() {}

    public static void syncFromServer(AstralPosition incoming) {
        if (CLIENT.level == null || CLIENT.player == null) return;
        if (!isDissociated()) startProjection(incoming);
        astralCamera.moveTo(incoming.coordinates().x, incoming.coordinates().y, incoming.coordinates().z,
                incoming.yaw(), incoming.pitch());
        astralCamera.setYHeadRot(incoming.yaw());
    }

    private static void startProjection(AstralPosition initial) {
        if (CLIENT.level == null || CLIENT.player == null) return;
        astralCamera = new AstralCamera(CLIENT.level, CLIENT.player.getGameProfile());
        astralCamera.moveTo(initial.coordinates().x, initial.coordinates().y, initial.coordinates().z,
                initial.yaw(), initial.pitch());
        astralCamera.noPhysics = true;
        astralCamera.setInvisible(true);
        CLIENT.setCameraEntity(astralCamera);
        ambienceLoop = new AstralAmbienceLoop(CLIENT.player);
        CLIENT.getSoundManager().play(ambienceLoop);
        CLIENT.player.input = new net.minecraft.client.player.Input();
        CLIENT.gui.setOverlayMessage(Component.translatable("mediaworks.message.projection",
                CLIENT.options.keyInventory.getTranslatedKeyMessage()), false);
        loadShader();
    }

    public static void endProjection() {
        if (!isDissociated()) return;
        if (CLIENT.player != null) {
            CLIENT.setCameraEntity(CLIENT.player);
            CLIENT.player.input = new KeyboardInput(CLIENT.options);
        }
        astralCamera = null;
        if (ambienceLoop != null) {
            CLIENT.getSoundManager().stop(ambienceLoop);
            ambienceLoop = null;
        }
        if (shaderLoaded) {
            CLIENT.gameRenderer.shutdownEffect();
            shaderLoaded = false;
        }
    }

    private static void loadShader() {
        try {
            CLIENT.gameRenderer.loadEffect(io.github.artynova.mediaworks.Mediaworks.id("shaders/post/astral.json"));
            shaderLoaded = true;
        } catch (RuntimeException ignored) {
            shaderLoaded = false;
        }
    }

    public static boolean isDissociated() { return astralCamera != null; }

    /** Used by the client mouse mixin so rotation follows the detached camera, not the body. */
    public static void turnAstralCamera(double x, double y) {
        if (astralCamera != null) astralCamera.turn(x, y);
    }

    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        if (isDissociated()) event.setCanceled(true);
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        if (!isDissociated() || CLIENT.isPaused() || CLIENT.player == null) return;
        var input = new KeyboardInput(CLIENT.options);
        input.tick(false, 1);
        float yaw = astralCamera.getYRot() * Mth.DEG_TO_RAD;
        double speed = input.shiftKeyDown ? 0.9 : 0.35;
        double forward = input.forwardImpulse * speed;
        double side = input.leftImpulse * speed;
        double dx = -Mth.sin(yaw) * forward + Mth.cos(yaw) * side;
        double dz = Mth.cos(yaw) * forward + Mth.sin(yaw) * side;
        double dy = (input.jumping ? speed : 0) - (input.shiftKeyDown ? speed : 0);
        astralCamera.setPos(astralCamera.getX() + dx, astralCamera.getY() + dy, astralCamera.getZ() + dz);
        syncToServer();
        if (CLIENT.options.keyInventory.consumeClick()) {
            MediaworksNetworking.sendToServer(new EndProjectionC2SMsg());
        }
    }

    public static void onInteraction(InputEvent.InteractionKeyMappingTriggered event) {
        if (!isDissociated()) return;
        if (event.isUseItem()) MediaworksNetworking.sendToServer(new CastAstralIotaC2SMsg());
        event.setSwingHand(false);
        event.setCanceled(true);
    }

    public static void onFog(ViewportEvent.RenderFog event) {
        if (!isDissociated() || CLIENT.player == null) return;
        float radius = (float) HexUtils.getAmbitRadius(CLIENT.player);
        double distance = astralCamera.position().distanceTo(CLIENT.player.position());
        float end = distance > radius ? Math.max(4, radius - (float) (distance - radius) * 8) : radius;
        event.setNearPlaneDistance(end * 0.25f);
        event.setFarPlaneDistance(end);
        event.setFogShape(com.mojang.blaze3d.shaders.FogShape.SPHERE);
        event.setCanceled(true);
    }

    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
        if (!isDissociated()) return;
        event.setRed(0.588f);
        event.setGreen(0.588f);
        event.setBlue(0.588f);
    }

    public static void onGuiRender(RenderGuiEvent.Post event) {
        if (!isDissociated() || CLIENT.player == null) return;
        double distance = astralCamera.position().distanceTo(CLIENT.player.position());
        int alpha = Mth.clamp((int) (distance / Math.max(1, HexUtils.getAmbitRadius(CLIENT.player)) * 210), 0, 210);
        event.getGuiGraphics().fill(0, 0, CLIENT.getWindow().getGuiScaledWidth(),
                CLIENT.getWindow().getGuiScaledHeight(), alpha << 24 | 0x111111);
    }

    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) { endProjection(); }

    private static void syncToServer() {
        if (!isDissociated()) return;
        MediaworksNetworking.sendToServer(new SyncAstralPositionC2SMsg(
                new AstralPosition(astralCamera.position(), astralCamera.getYRot(), astralCamera.getXRot())));
    }

    private static final class AstralCamera extends RemotePlayer {
        private AstralCamera(net.minecraft.client.multiplayer.ClientLevel level, com.mojang.authlib.GameProfile profile) {
            super(level, profile);
            getAbilities().flying = true;
            getAbilities().mayfly = true;
        }
        @Override public boolean isSpectator() { return true; }
        @Override public boolean isCreative() { return false; }
        @Override public boolean isInvisibleTo(net.minecraft.world.entity.player.Player player) {
            return player != CLIENT.player;
        }
    }
}

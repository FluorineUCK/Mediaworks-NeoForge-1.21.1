package io.github.artynova.mediaworks.networking;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import io.github.artynova.mediaworks.logic.projection.AstralPosition;
import net.minecraft.nbt.CompoundTag;

/**
 * Common-side packet handlers terminate at this bridge. The client initializer
 * installs the real callbacks, so a dedicated server never resolves client
 * classes while loading payload registrations.
 */
public final class MediaworksClientPacketBridge {
    private static volatile ClientHandler handler;

    private MediaworksClientPacketBridge() {}

    public static void install(ClientHandler clientHandler) {
        handler = clientHandler;
    }

    public static void syncAstralPosition(AstralPosition position) {
        ClientHandler current = handler;
        if (current != null) current.syncAstralPosition(position);
    }

    public static void endProjection() {
        ClientHandler current = handler;
        if (current != null) current.endProjection();
    }

    public static void spawnHexParticles(ParticleSpray spray, FrozenPigment pigment) {
        ClientHandler current = handler;
        if (current != null) current.spawnHexParticles(spray, pigment);
    }

    public static void syncMacula(CompoundTag tag) {
        ClientHandler current = handler;
        if (current != null) current.syncMacula(tag);
    }

    public interface ClientHandler {
        void syncAstralPosition(AstralPosition position);
        void endProjection();
        void spawnHexParticles(ParticleSpray spray, FrozenPigment pigment);
        void syncMacula(CompoundTag tag);
    }
}

package io.github.artynova.mediaworks.networking;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import io.github.artynova.mediaworks.Mediaworks;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpawnHexParticlesS2CMsg(ParticleSpray spray, FrozenPigment pigment) implements CustomPacketPayload {
    public static final Type<SpawnHexParticlesS2CMsg> TYPE = new Type<>(Mediaworks.id("spawn_hex_particles_s2c"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpawnHexParticlesS2CMsg> STREAM_CODEC = StreamCodec.composite(
        ParticleSpray.getSTREAM_CODEC(), SpawnHexParticlesS2CMsg::spray,
        FrozenPigment.STREAM_CODEC, SpawnHexParticlesS2CMsg::pigment,
        SpawnHexParticlesS2CMsg::new
    );

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    public static void handle(SpawnHexParticlesS2CMsg payload, IPayloadContext context) {
        context.enqueueWork(() -> MediaworksClientPacketBridge.spawnHexParticles(payload.spray, payload.pigment));
    }
}

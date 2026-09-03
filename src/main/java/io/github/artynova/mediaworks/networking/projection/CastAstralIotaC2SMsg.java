package io.github.artynova.mediaworks.networking.projection;

import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.logic.projection.AstralProjectionServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CastAstralIotaC2SMsg() implements CustomPacketPayload {
    public static final Type<CastAstralIotaC2SMsg> TYPE = new Type<>(Mediaworks.id("cast_astral_iota_c2s"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CastAstralIotaC2SMsg> STREAM_CODEC = StreamCodec.unit(new CastAstralIotaC2SMsg());
    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    public static void handle(CastAstralIotaC2SMsg payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) AstralProjectionServer.evaluateIota(player);
        });
    }
}

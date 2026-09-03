package io.github.artynova.mediaworks.networking.projection;

import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.logic.projection.AstralProjectionServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EndProjectionC2SMsg() implements CustomPacketPayload {
    public static final Type<EndProjectionC2SMsg> TYPE = new Type<>(Mediaworks.id("end_projection_c2s"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EndProjectionC2SMsg> STREAM_CODEC = StreamCodec.unit(new EndProjectionC2SMsg());
    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    public static void handle(EndProjectionC2SMsg payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) AstralProjectionServer.endProjectionEarly(player);
        });
    }
}

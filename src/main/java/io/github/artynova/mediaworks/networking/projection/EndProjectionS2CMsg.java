package io.github.artynova.mediaworks.networking.projection;

import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.networking.MediaworksClientPacketBridge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EndProjectionS2CMsg() implements CustomPacketPayload {
    public static final Type<EndProjectionS2CMsg> TYPE = new Type<>(Mediaworks.id("end_projection_s2c"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EndProjectionS2CMsg> STREAM_CODEC = StreamCodec.unit(new EndProjectionS2CMsg());
    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    public static void handle(EndProjectionS2CMsg payload, IPayloadContext context) {
        context.enqueueWork(MediaworksClientPacketBridge::endProjection);
    }
}

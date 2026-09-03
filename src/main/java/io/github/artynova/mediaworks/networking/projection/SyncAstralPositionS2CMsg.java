package io.github.artynova.mediaworks.networking.projection;

import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.logic.projection.AstralPosition;
import io.github.artynova.mediaworks.networking.MediaworksClientPacketBridge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncAstralPositionS2CMsg(AstralPosition data) implements CustomPacketPayload {
    public static final Type<SyncAstralPositionS2CMsg> TYPE = new Type<>(Mediaworks.id("sync_astral_position_s2c"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncAstralPositionS2CMsg> STREAM_CODEC = StreamCodec.of(
        (buffer, payload) -> AstralPositionPayloadCodec.write(buffer, payload.data),
        buffer -> new SyncAstralPositionS2CMsg(AstralPositionPayloadCodec.read(buffer))
    );

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    public static void handle(SyncAstralPositionS2CMsg payload, IPayloadContext context) {
        context.enqueueWork(() -> MediaworksClientPacketBridge.syncAstralPosition(payload.data));
    }
}

package io.github.artynova.mediaworks.networking.projection;

import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.logic.projection.AstralPosition;
import io.github.artynova.mediaworks.logic.projection.AstralProjectionServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncAstralPositionC2SMsg(AstralPosition data) implements CustomPacketPayload {
    public static final Type<SyncAstralPositionC2SMsg> TYPE = new Type<>(Mediaworks.id("sync_astral_position_c2s"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncAstralPositionC2SMsg> STREAM_CODEC = StreamCodec.of(
        (buffer, payload) -> AstralPositionPayloadCodec.write(buffer, payload.data),
        buffer -> new SyncAstralPositionC2SMsg(AstralPositionPayloadCodec.read(buffer))
    );

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(SyncAstralPositionC2SMsg payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                AstralProjectionServer.syncFromClient(player, payload.data);
            }
        });
    }
}

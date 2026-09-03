package io.github.artynova.mediaworks.networking.macula;

import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.logic.macula.MaculaServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncMaculaDimensionsC2SMsg(int width, int height) implements CustomPacketPayload {
    public static final Type<SyncMaculaDimensionsC2SMsg> TYPE = new Type<>(Mediaworks.id("sync_macula_dimensions_c2s"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMaculaDimensionsC2SMsg> STREAM_CODEC = StreamCodec.of(
        (buffer, payload) -> { buffer.writeVarInt(payload.width); buffer.writeVarInt(payload.height); },
        buffer -> new SyncMaculaDimensionsC2SMsg(buffer.readVarInt(), buffer.readVarInt())
    );
    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    public static void handle(SyncMaculaDimensionsC2SMsg payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                MaculaServer.syncDimensionsFromClient(player, payload.width, payload.height);
            }
        });
    }
}

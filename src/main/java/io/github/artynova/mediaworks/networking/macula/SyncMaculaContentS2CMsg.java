package io.github.artynova.mediaworks.networking.macula;

import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.logic.macula.Macula;
import io.github.artynova.mediaworks.networking.MediaworksClientPacketBridge;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncMaculaContentS2CMsg(CompoundTag data) implements CustomPacketPayload {
    public static final Type<SyncMaculaContentS2CMsg> TYPE = new Type<>(Mediaworks.id("sync_macula_content_s2c"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMaculaContentS2CMsg> STREAM_CODEC = StreamCodec.of(
        (buffer, payload) -> buffer.writeNbt(payload.data),
        buffer -> new SyncMaculaContentS2CMsg(buffer.readNbt())
    );

    public static SyncMaculaContentS2CMsg fromMacula(Macula macula, net.minecraft.core.HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        macula.writeToNbt(tag, registries);
        return new SyncMaculaContentS2CMsg(tag);
    }

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    public static void handle(SyncMaculaContentS2CMsg payload, IPayloadContext context) {
        context.enqueueWork(() -> MediaworksClientPacketBridge.syncMacula(payload.data));
    }
}

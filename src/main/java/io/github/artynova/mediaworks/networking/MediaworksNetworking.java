package io.github.artynova.mediaworks.networking;

import io.github.artynova.mediaworks.networking.macula.SyncMaculaContentS2CMsg;
import io.github.artynova.mediaworks.networking.macula.SyncMaculaDimensionsC2SMsg;
import io.github.artynova.mediaworks.networking.projection.CastAstralIotaC2SMsg;
import io.github.artynova.mediaworks.networking.projection.EndProjectionC2SMsg;
import io.github.artynova.mediaworks.networking.projection.EndProjectionS2CMsg;
import io.github.artynova.mediaworks.networking.projection.SyncAstralPositionC2SMsg;
import io.github.artynova.mediaworks.networking.projection.SyncAstralPositionS2CMsg;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class MediaworksNetworking {
    private MediaworksNetworking() {}

    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToServer(SyncAstralPositionC2SMsg.TYPE, SyncAstralPositionC2SMsg.STREAM_CODEC, SyncAstralPositionC2SMsg::handle);
        registrar.playToClient(SyncAstralPositionS2CMsg.TYPE, SyncAstralPositionS2CMsg.STREAM_CODEC, SyncAstralPositionS2CMsg::handle);
        registrar.playToServer(EndProjectionC2SMsg.TYPE, EndProjectionC2SMsg.STREAM_CODEC, EndProjectionC2SMsg::handle);
        registrar.playToClient(EndProjectionS2CMsg.TYPE, EndProjectionS2CMsg.STREAM_CODEC, EndProjectionS2CMsg::handle);
        registrar.playToClient(SpawnHexParticlesS2CMsg.TYPE, SpawnHexParticlesS2CMsg.STREAM_CODEC, SpawnHexParticlesS2CMsg::handle);
        registrar.playToServer(CastAstralIotaC2SMsg.TYPE, CastAstralIotaC2SMsg.STREAM_CODEC, CastAstralIotaC2SMsg::handle);
        registerSyncMaculaContentS2CMsgClientbound(registrar);
        registerSyncMaculaDimensionsC2SMsgServerbound(registrar);
    }

    private static void registerSyncMaculaContentS2CMsgClientbound(
            net.neoforged.neoforge.network.registration.PayloadRegistrar registrar) {
        registrar.playToClient(SyncMaculaContentS2CMsg.TYPE, SyncMaculaContentS2CMsg.STREAM_CODEC,
                SyncMaculaContentS2CMsg::handle);
    }

    private static void registerSyncMaculaDimensionsC2SMsgServerbound(
            net.neoforged.neoforge.network.registration.PayloadRegistrar registrar) {
        registrar.playToServer(SyncMaculaDimensionsC2SMsg.TYPE, SyncMaculaDimensionsC2SMsg.STREAM_CODEC,
                SyncMaculaDimensionsC2SMsg::handle);
    }

    public static void sendToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload message) {
        PacketDistributor.sendToPlayer(player, message);
    }

    public static void sendToPlayers(Iterable<ServerPlayer> players, CustomPacketPayload message) {
        players.forEach(player -> PacketDistributor.sendToPlayer(player, message));
    }
}

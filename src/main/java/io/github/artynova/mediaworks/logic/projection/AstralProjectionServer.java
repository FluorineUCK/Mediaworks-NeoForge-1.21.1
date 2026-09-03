package io.github.artynova.mediaworks.logic.projection;

import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.github.artynova.mediaworks.MediaworksAttachments;
import io.github.artynova.mediaworks.effect.MediaworksEffects;
import io.github.artynova.mediaworks.networking.MediaworksNetworking;
import io.github.artynova.mediaworks.networking.projection.EndProjectionS2CMsg;
import io.github.artynova.mediaworks.networking.projection.SyncAstralPositionS2CMsg;
import io.github.artynova.mediaworks.sound.MediaworksSounds;
import io.github.artynova.mediaworks.util.HexUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class AstralProjectionServer {
    public static final int NAUSEA_TICKS = 200, NAUSEA_AMPLIFIER = 4, CAST_COOLDOWN_TICKS = 10;
    public static final double INITIAL_HEIGHT_OFFSET = .5, SQUARED_BODY_MOVEMENT_LIMIT = 9.0;
    private AstralProjectionServer() {}
    public static AstralProjection getProjection(ServerPlayer player) { return MediaworksAttachments.projection(player); }
    public static boolean isProjecting(ServerPlayer player) { return getProjection(player).isActive(); }
    public static void syncFromClient(ServerPlayer player, AstralPosition position) {
        if (!isProjecting(player)) return;
        getProjection(player).setPosition(position);
        if (!HexUtils.isInAmbit(position.coordinates(), player)) endProjectionAbruptly(player);
    }
    public static void startProjection(ServerPlayer player) {
        if (isProjecting(player)) return;
        AstralProjection projection = getProjection(player);
        projection.setOrigin(player.position());
        AstralPosition pos = new AstralPosition(new Vec3(player.getX(), player.getY() + INITIAL_HEIGHT_OFFSET, player.getZ()), player.getYHeadRot(), player.getXRot());
        projection.setPosition(pos);
        MediaworksNetworking.sendToPlayer(player, new SyncAstralPositionS2CMsg(pos));
    }
    public static void endProjection(ServerPlayer player) {
        MediaworksNetworking.sendToPlayer(player, new EndProjectionS2CMsg());
        getProjection(player).end();
        player.level().playSound(null, player.blockPosition(), MediaworksSounds.PROJECTION_RETURN.get(), SoundSource.PLAYERS, 1, 1);
    }
    public static void endProjectionEarly(ServerPlayer player) { player.removeEffect(MediaworksEffects.ASTRAL_PROJECTION); }
    public static void endProjectionAbruptly(ServerPlayer player) {
        endProjectionEarly(player); player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, NAUSEA_TICKS, NAUSEA_AMPLIFIER));
    }
    public static void evaluateIota(ServerPlayer player) {
        AstralProjection projection = getProjection(player);
        if (!projection.isActive() || projection.getCooldown() > 0) return;
        projection.setCooldown(CAST_COOLDOWN_TICKS);
        Iota iota = projection.getIota(); if (iota == null) return;
        IXplatAbstractions.INSTANCE.clearCastingData(player);
        CastingVM vm = CastingVM.empty(new MediaworksAstralCastEnv(player));
        var outcome = iota instanceof ListIota list ? vm.queueExecuteAndWrapIotas(HexUtils.decompose(list), player.serverLevel())
                : vm.queueExecuteAndWrapIota(iota, player.serverLevel());
        IXplatAbstractions.INSTANCE.clearCastingData(player);
        if (outcome.getResolutionType() == ResolvedPatternType.ERRORED || outcome.getResolutionType() == ResolvedPatternType.INVALID) endProjectionAbruptly(player);
    }
    public static void handlePlayerTick(ServerPlayer player) {
        AstralProjection projection = getProjection(player); if (!projection.isActive()) return;
        if (projection.getOrigin() != null && player.position().distanceToSqr(projection.getOrigin()) > SQUARED_BODY_MOVEMENT_LIMIT) endProjectionAbruptly(player);
        projection.tickCooldown();
    }
    public static void handleJoin(ServerPlayer player) { if (getProjection(player).getPosition() != null) MediaworksNetworking.sendToPlayer(player, new SyncAstralPositionS2CMsg(getProjection(player).getPosition())); }
    public static void handleQuit(ServerPlayer player) {
        if (isProjecting(player)) getProjection(player).end();
    }
    public static void handleClone(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean wonGame) { if (wonGame && isProjecting(oldPlayer)) endProjectionAbruptly(oldPlayer); }
    public static void handleDeath(ServerPlayer player, DamageSource source) { if (isProjecting(player)) endProjectionEarly(player); }
    public static void handleDimensionChange(ServerPlayer player, ResourceKey<Level> previous, ResourceKey<Level> current) { if (isProjecting(player)) endProjectionAbruptly(player); }
}

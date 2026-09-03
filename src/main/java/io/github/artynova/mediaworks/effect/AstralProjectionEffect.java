package io.github.artynova.mediaworks.effect;

import io.github.artynova.mediaworks.logic.projection.AstralProjectionServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public final class AstralProjectionEffect extends MobEffect {
    public static final int COLOR = 0x65518A;
    public AstralProjectionEffect() { super(MobEffectCategory.BENEFICIAL, COLOR); }
    @Override public void onEffectStarted(LivingEntity entity, int amplifier) {
        super.onEffectStarted(entity, amplifier);
        if (entity instanceof ServerPlayer player) AstralProjectionServer.startProjection(player);
    }
    @Override public void onMobRemoved(LivingEntity entity, int amplifier, net.minecraft.world.entity.Entity.RemovalReason reason) {
        super.onMobRemoved(entity, amplifier, reason);
        if (entity instanceof ServerPlayer player && AstralProjectionServer.isProjecting(player)) AstralProjectionServer.endProjection(player);
    }
}

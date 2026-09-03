package io.github.artynova.mediaworks;

import io.github.artynova.mediaworks.logic.macula.MaculaServer;
import io.github.artynova.mediaworks.logic.projection.AstralProjectionServer;
import io.github.artynova.mediaworks.effect.MediaworksEffects;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class MediaworksEvents {
    private MediaworksEvents() {}

    public static void register(IEventBus eventBus) {
        eventBus.addListener(MediaworksEvents::onPlayerTick);
        eventBus.addListener(MediaworksEvents::onJoin);
        eventBus.addListener(MediaworksEvents::onQuit);
        eventBus.addListener(MediaworksEvents::onClone);
        eventBus.addListener(MediaworksEvents::onDeath);
        eventBus.addListener(MediaworksEvents::onDimensionChange);
        eventBus.addListener(MediaworksEvents::onIncomingDamage);
        eventBus.addListener(MediaworksEvents::onDamageApplied);
        eventBus.addListener(MediaworksEvents::onEffectRemoved);
        eventBus.addListener(MediaworksEvents::onEffectExpired);
    }

    private static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) AstralProjectionServer.handlePlayerTick(player);
    }

    private static void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AstralProjectionServer.handleJoin(player);
            MaculaServer.handleJoin(player);
        }
    }

    private static void onQuit(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AstralProjectionServer.handleQuit(player);
            MaculaServer.handleQuit(player);
        }
    }

    private static void onClone(PlayerEvent.Clone event) {
        if (event.getOriginal() instanceof ServerPlayer oldPlayer && event.getEntity() instanceof ServerPlayer newPlayer) {
            AstralProjectionServer.handleClone(oldPlayer, newPlayer, !event.isWasDeath());
            MaculaServer.handleClone(oldPlayer, newPlayer, !event.isWasDeath());
        }
    }

    private static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) AstralProjectionServer.handleDeath(player, event.getSource());
    }

    private static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AstralProjectionServer.handleDimensionChange(player, event.getFrom(), event.getTo());
        }
    }

    private static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (event.getSource().is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) return;
        event.setAmount(io.github.artynova.mediaworks.enchantment.MediaShieldEnchantment
                .processIncomingDamage(player, event.getAmount(), 1000.0));
    }

    private static void onDamageApplied(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player && event.getNewDamage() > 0) {
            io.github.artynova.mediaworks.enchantment.ReciprocationEnchantment
                    .processPlayerHurt(event.getSource(), player);
        }
    }

    private static void onEffectRemoved(MobEffectEvent.Remove event) {
        if (event.getEntity() instanceof ServerPlayer player
                && event.getEffect().is(MediaworksEffects.ASTRAL_PROJECTION.getKey())
                && AstralProjectionServer.isProjecting(player)) {
            AstralProjectionServer.endProjection(player);
        }
    }

    private static void onEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEntity() instanceof ServerPlayer player
                && event.getEffectInstance() != null
                && event.getEffectInstance().is(MediaworksEffects.ASTRAL_PROJECTION)
                && AstralProjectionServer.isProjecting(player)) {
            AstralProjectionServer.endProjection(player);
        }
    }
}

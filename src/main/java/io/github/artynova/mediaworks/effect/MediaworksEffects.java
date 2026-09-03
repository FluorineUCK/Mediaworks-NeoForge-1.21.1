package io.github.artynova.mediaworks.effect;

import io.github.artynova.mediaworks.Mediaworks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class MediaworksEffects {
    private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Mediaworks.MOD_ID);
    public static final DeferredHolder<MobEffect, AstralProjectionEffect> ASTRAL_PROJECTION = EFFECTS.register("astral_projection", AstralProjectionEffect::new);
    private MediaworksEffects() {}
    public static void register(IEventBus bus) { EFFECTS.register(bus); }
}

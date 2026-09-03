package io.github.artynova.mediaworks.sound;

import io.github.artynova.mediaworks.Mediaworks;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class MediaworksSounds {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Mediaworks.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> PROJECTION_AMBIANCE = SOUNDS.register("astral.ambience", () -> SoundEvent.createVariableRangeEvent(Mediaworks.id("astral.ambience")));
    public static final DeferredHolder<SoundEvent, SoundEvent> PROJECTION_RETURN = SOUNDS.register("astral.return", () -> SoundEvent.createVariableRangeEvent(Mediaworks.id("astral.return")));
    private MediaworksSounds() {}
    public static void register(IEventBus bus) { SOUNDS.register(bus); }
}

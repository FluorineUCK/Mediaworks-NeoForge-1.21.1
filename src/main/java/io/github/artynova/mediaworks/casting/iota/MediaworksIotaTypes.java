package io.github.artynova.mediaworks.casting.iota;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.lib.HexRegistries;
import io.github.artynova.mediaworks.Mediaworks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MediaworksIotaTypes {
    private static final DeferredRegister<IotaType<?>> TYPES = DeferredRegister.create(HexRegistries.IOTA_TYPE, Mediaworks.MOD_ID);
    public static final Supplier<IotaType<?>> VISAGE = TYPES.register("visage", () -> VisageIota.TYPE);
    private MediaworksIotaTypes() {}
    public static void register(IEventBus bus) { TYPES.register(bus); }
}

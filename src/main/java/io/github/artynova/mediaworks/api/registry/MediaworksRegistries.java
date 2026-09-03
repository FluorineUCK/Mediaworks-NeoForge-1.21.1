package io.github.artynova.mediaworks.api.registry;

import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.api.logic.macula.VisageType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MediaworksRegistries {
    public static final ResourceKey<Registry<VisageType<?>>> VISAGE_TYPE_KEY =
            ResourceKey.createRegistryKey(Mediaworks.id("visage_type"));
    public static final DeferredRegister<VisageType<?>> VISAGE_TYPES =
            DeferredRegister.create(VISAGE_TYPE_KEY, Mediaworks.MOD_ID);

    private MediaworksRegistries() {}
    public static void register(IEventBus bus) { VISAGE_TYPES.register(bus); }
    public static Supplier<VisageType<?>> register(String id, Supplier<VisageType<?>> type) { return VISAGE_TYPES.register(id, type); }
    public static VisageType<?> getVisageType(ResourceLocation id) { return VISAGE_TYPES.getRegistry().get().get(id); }
    public static ResourceLocation getVisageTypeId(VisageType<?> type) { return VISAGE_TYPES.getRegistry().get().getKey(type); }
}

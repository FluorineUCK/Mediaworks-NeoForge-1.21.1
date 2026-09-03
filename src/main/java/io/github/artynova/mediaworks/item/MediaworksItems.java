package io.github.artynova.mediaworks.item;

import io.github.artynova.mediaworks.Mediaworks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class MediaworksItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Mediaworks.MOD_ID);
    public static final DeferredHolder<Item, MagicCloakItem> MAGIC_CLOAK = ITEMS.register("magic_cloak", MagicCloakItem::new);
    private MediaworksItems() {}
    public static void register(IEventBus bus) { ITEMS.register(bus); }
}

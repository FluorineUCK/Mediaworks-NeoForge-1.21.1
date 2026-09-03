package io.github.artynova.mediaworks.enchantment;

import io.github.artynova.mediaworks.Mediaworks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public final class MediaworksEnchantments {
    public static final ResourceKey<Enchantment> RECIPROCATION = key("reciprocation");
    public static final ResourceKey<Enchantment> MEDIA_SHIELD = key("media_shield");
    public static final ResourceKey<Enchantment> LOCALE_MAGNIFICATION = key("locale_magnification");
    private MediaworksEnchantments() {}
    private static ResourceKey<Enchantment> key(String id) { return ResourceKey.create(Registries.ENCHANTMENT, Mediaworks.id(id)); }
}

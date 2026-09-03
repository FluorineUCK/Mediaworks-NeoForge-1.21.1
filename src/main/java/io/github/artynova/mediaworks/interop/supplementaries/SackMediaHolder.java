package io.github.artynova.mediaworks.interop.supplementaries;

import io.github.artynova.mediaworks.api.logic.media.BEContainerMediaHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class SackMediaHolder extends BEContainerMediaHolder {
    public static final ResourceLocation SACK_ID = ResourceLocation.fromNamespaceAndPath(SupplementariesInterop.MOD_ID, "sack");
    public SackMediaHolder(ItemStack stack) { super(stack, 9); }
    public static boolean isSack(Item item) { return SACK_ID.equals(BuiltInRegistries.ITEM.getKey(item)); }
}

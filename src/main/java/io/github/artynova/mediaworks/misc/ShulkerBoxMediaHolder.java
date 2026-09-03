package io.github.artynova.mediaworks.misc;

import io.github.artynova.mediaworks.api.logic.media.BEContainerMediaHolder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public final class ShulkerBoxMediaHolder extends BEContainerMediaHolder {
    public static final int INVENTORY_SIZE = 27;
    public ShulkerBoxMediaHolder(ItemStack stack) { super(stack, INVENTORY_SIZE); }
    public static boolean isShulkerBox(Item item) { return item instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock; }
}

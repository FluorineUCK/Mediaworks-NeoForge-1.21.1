package io.github.artynova.mediaworks.api.logic.media;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.ArrayList;
import java.util.List;

/** 1.21 component-backed container holder; replaces legacy BlockEntityTag/Items NBT parsing. */
public class BEContainerMediaHolder extends ContainerItemMediaHolder {
    private final int inventorySize;

    public BEContainerMediaHolder(ItemStack stack, int inventorySize) {
        super(stack);
        this.inventorySize = inventorySize;
    }

    @Override protected List<ItemStack> getInventory() {
        List<ItemStack> stacks = new ArrayList<>(inventorySize);
        for (int i = 0; i < inventorySize; i++) stacks.add(ItemStack.EMPTY);
        ItemContainerContents contents = getStack().getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        for (int i = 0; i < Math.min(inventorySize, contents.getSlots()); i++) stacks.set(i, contents.getStackInSlot(i).copy());
        return stacks;
    }

    @Override protected void setInventory(List<ItemStack> inventory) {
        getStack().set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory.stream().limit(inventorySize).toList()));
    }
}

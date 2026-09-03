package io.github.artynova.mediaworks.api.logic.media;

import at.petrak.hexcasting.api.addldata.ADMediaHolder;
import at.petrak.hexcasting.api.utils.MediaHelper;
import io.github.artynova.mediaworks.util.MediaUtils;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class ContainerItemMediaHolder implements ADMediaHolder {
    public static final int PRIORITY = 500;
    private final ItemStack stack;

    protected ContainerItemMediaHolder(ItemStack stack) { this.stack = stack; }
    protected abstract List<ItemStack> getInventory();
    protected abstract void setInventory(List<ItemStack> inventory);
    public ItemStack getStack() { return stack; }

    @Override public long getMedia() { return withdrawMedia(-1, true); }
    @Override public void setMedia(long media) { }
    @Override public long getMaxMedia() { return getMedia(); }
    @Override public boolean canRecharge() { return false; }
    @Override public boolean canProvide() { return true; }
    @Override public int getConsumptionPriority() { return PRIORITY; }
    @Override public boolean canConstructBattery() { return false; }

    @Override public long withdrawMedia(long cost, boolean simulate) {
        long remaining = cost;
        List<ItemStack> inventory = getInventory();
        for (ADMediaHolder holder : MediaUtils.collectMediaHolders(inventory)) {
            remaining -= MediaHelper.extractMedia(holder, remaining, false, simulate);
            if (cost >= 0 && remaining <= 0) break;
        }
        if (!simulate) setInventory(inventory);
        return cost - remaining;
    }
}

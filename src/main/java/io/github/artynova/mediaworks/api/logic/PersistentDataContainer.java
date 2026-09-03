package io.github.artynova.mediaworks.api.logic;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

public interface PersistentDataContainer {
    void readFromNbt(CompoundTag tag, HolderLookup.Provider registries);
    void writeToNbt(CompoundTag tag, HolderLookup.Provider registries);
}

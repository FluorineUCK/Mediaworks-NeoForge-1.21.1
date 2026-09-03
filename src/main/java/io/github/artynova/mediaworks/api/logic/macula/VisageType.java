package io.github.artynova.mediaworks.api.logic.macula;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public abstract class VisageType<T extends Visage> {
    public abstract @Nullable T deserializeData(CompoundTag tag);
    public abstract CompoundTag serializeData(T visage);
}

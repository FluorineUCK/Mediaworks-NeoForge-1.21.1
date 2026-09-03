package io.github.artynova.mediaworks.casting;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/** Compatibility interface retained for integrations; new code uses MediaworksPlayerCastEnv directly. */
public interface ExtendedCastingContext {
    @Nullable ItemStack mediaworks$getForcedCastingStack();
    void mediaworks$setForcedCastingStack(@Nullable ItemStack stack);
    int mediaworks$getReciprocationReps();
    void mediaworks$setReciprocationReps(int reps);
}

package io.github.artynova.mediaworks.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

/** 1.21 equivalent of the original CLEAN_DYEABLE cauldron behavior. */
public final class MagicCloakCauldronBehavior {
    public static final String CLEAN_DYEABLE = "clean_dyeable";
    private MagicCloakCauldronBehavior() {}
    public static boolean clean(ItemStack stack) {
        return stack.remove(DataComponents.DYED_COLOR) != null;
    }
}

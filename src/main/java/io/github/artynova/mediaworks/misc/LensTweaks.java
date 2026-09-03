package io.github.artynova.mediaworks.misc;

import at.petrak.hexcasting.common.lib.HexItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public final class LensTweaks {
    public static final String GRID_SCALE_MODIFIER = "GridScaleModifier";
    private LensTweaks() {}
    public static float lensHelmetGridModifier(Player player) { return lensInEitherHand(player) ? 1 : lensOnHead(player) ? .75f : 1; }
    public static boolean lensOnHead(Player player) { return player.getItemBySlot(EquipmentSlot.HEAD).is(HexItems.SCRYING_LENS.get()); }
    public static boolean lensInEitherHand(Player player) { return player.getMainHandItem().is(HexItems.SCRYING_LENS.get()) || player.getOffhandItem().is(HexItems.SCRYING_LENS.get()); }
}

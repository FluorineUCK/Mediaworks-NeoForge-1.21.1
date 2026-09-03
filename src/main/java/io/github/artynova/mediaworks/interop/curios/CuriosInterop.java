package io.github.artynova.mediaworks.interop.curios;

import at.petrak.hexcasting.api.misc.DiscoveryHandlers;
import at.petrak.hexcasting.common.lib.HexItems;
import io.github.artynova.mediaworks.misc.LensTweaks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

/** Optional Curios bridge: discover the head-slot scrying lens without hard-linking from startup code. */
public final class CuriosInterop {
    public static final String MOD_ID = "curios";
    private CuriosInterop() {}

    public static void init() {
        DiscoveryHandlers.addExtraEquipmentDiscoverer(CuriosInterop::discoverHeadLens);
    }

    private static List<ItemStack> discoverHeadLens(Player player) {
        if (LensTweaks.lensInEitherHand(player) || LensTweaks.lensOnHead(player)) return List.of();
        return CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(
                        stack -> stack.is(HexItems.SCRYING_LENS.get()), "head"))
                .map(result -> List.of(result.stack()))
                .orElseGet(List::of);
    }
}

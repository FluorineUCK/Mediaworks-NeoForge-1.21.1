package io.github.artynova.mediaworks.casting.pattern.misc;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.NullIota;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.github.artynova.mediaworks.item.MediaworksItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import java.util.List;

public final class OpCloakRead implements ConstMediaAction {
    @Override public int getArgc() { return 0; }
    @Override public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) {
        ItemStack stack = env.getCaster().getItemBySlot(EquipmentSlot.HEAD);
        if (!stack.is(MediaworksItems.MAGIC_CLOAK.get())) return List.of(new NullIota());
        ADIotaHolder holder = IXplatAbstractions.INSTANCE.findDataHolder(stack);
        Iota value = holder == null ? null : holder.readIota();
        return List.of(value == null ? new NullIota() : value);
    }
}

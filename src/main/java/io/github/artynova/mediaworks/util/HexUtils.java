package io.github.artynova.mediaworks.util;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.player.Sentinel;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.github.artynova.mediaworks.enchantment.LocaleMagnificationEnchantment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public final class HexUtils {
    public static final double DEFAULT_AMBIT = 32.0;
    private HexUtils() {}
    public static double getAmbitRadius(Player player) { return DEFAULT_AMBIT + LocaleMagnificationEnchantment.getAmbitIncrease(player); }
    public static boolean isInAmbit(Vec3 vec, ServerPlayer caster) {
        if (vec.distanceToSqr(caster.getEyePosition()) <= getAmbitRadius(caster) * getAmbitRadius(caster)) return true;
        Sentinel sentinel = IXplatAbstractions.INSTANCE.getSentinel(caster);
        return sentinel != null && sentinel.extendsRange() && caster.level().dimension().equals(sentinel.dimension())
                && vec.distanceToSqr(sentinel.position()) < 256.0;
    }
    public static List<Iota> decompose(ListIota listIota) {
        List<Iota> result = new ArrayList<>(); listIota.getList().forEach(result::add); return result;
    }
    public static Vec3 smartRaycast(double ambit, Vec3 origin, Vec3 look) { return origin.add(look.normalize().scale(ambit)); }
}

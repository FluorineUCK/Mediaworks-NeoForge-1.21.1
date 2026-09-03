package io.github.artynova.mediaworks.enchantment;

import io.github.artynova.mediaworks.api.enchantment.CloakEnchantment;
import io.github.artynova.mediaworks.util.MathUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LocaleMagnificationEnchantment extends CloakEnchantment {
    public static final int CUTOFF_LEVEL = 3;
    private static final Map<Integer, Double> CACHE = new ConcurrentHashMap<>();
    public LocaleMagnificationEnchantment() { super(CUTOFF_LEVEL); }
    public static double getAmbitIncrease(Player player) {
        var registry = player.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
        return registry.getHolder(MediaworksEnchantments.LOCALE_MAGNIFICATION)
                .map(holder -> getIncreaseForLevel(EnchantmentHelper.getItemEnchantmentLevel(holder, player.getItemBySlot(EquipmentSlot.HEAD)))).orElse(0.0);
    }
    public static double getIncreaseForLevel(int level) { return CACHE.computeIfAbsent(level, LocaleMagnificationEnchantment::compute); }
    private static double compute(int level) {
        if (level <= 0) return 0; if (level <= CUTOFF_LEVEL) return Math.pow(2, 2 + level);
        double cutoff = Math.pow(2, 2 + CUTOFF_LEVEL), first = (cutoff - Math.pow(2, 1 + CUTOFF_LEVEL)) / 2;
        return cutoff + MathUtils.geomProgressionSum(first, .5, level - CUTOFF_LEVEL);
    }
}

package io.github.artynova.mediaworks.enchantment;

import at.petrak.hexcasting.api.utils.MediaHelper;
import io.github.artynova.mediaworks.api.enchantment.CloakEnchantment;
import io.github.artynova.mediaworks.item.MediaworksItems;
import io.github.artynova.mediaworks.util.MathUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MediaShieldEnchantment extends CloakEnchantment {
    public static final int CUTOFF_LEVEL = 4;
    private static final Map<Integer, Float> CACHE = new ConcurrentHashMap<>();
    public MediaShieldEnchantment() { super(CUTOFF_LEVEL); }
    public static float getAbsorptionRatioForLevel(int level) { return CACHE.computeIfAbsent(level, MediaShieldEnchantment::compute); }
    private static float compute(int level) {
        if (level <= 0) return 0; if (level <= CUTOFF_LEVEL) return level * .1f;
        return .4f + (float) MathUtils.geomProgressionSum(.05, .5, level - CUTOFF_LEVEL);
    }
    public static float processIncomingDamage(Player player, float amount, double mediaToHealthRate) {
        if (amount <= 0) return amount;
        ItemStack cloak = player.getItemBySlot(EquipmentSlot.HEAD); if (!cloak.is(MediaworksItems.MAGIC_CLOAK.get())) return amount;
        var registry = player.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
        int level = registry.getHolder(MediaworksEnchantments.MEDIA_SHIELD)
                .map(holder -> EnchantmentHelper.getItemEnchantmentLevel(holder, cloak)).orElse(0);
        if (level <= 0) return amount;
        long maxCost = (long) (amount * getAbsorptionRatioForLevel(level) * mediaToHealthRate);
        long remaining = maxCost;
        for (var holder : MediaHelper.scanPlayerForMediaStuff((net.minecraft.server.level.ServerPlayer) player)) {
            remaining -= MediaHelper.extractMedia(holder, remaining, false, false); if (remaining <= 0) break;
        }
        return Math.max(0, amount - (float) ((maxCost - remaining) / mediaToHealthRate));
    }
}

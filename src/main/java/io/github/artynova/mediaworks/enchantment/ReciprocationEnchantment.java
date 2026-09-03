package io.github.artynova.mediaworks.enchantment;

import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.github.artynova.mediaworks.api.enchantment.CloakEnchantment;
import io.github.artynova.mediaworks.item.MediaworksItems;
import io.github.artynova.mediaworks.logic.projection.MediaworksPlayerCastEnv;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public final class ReciprocationEnchantment extends CloakEnchantment {
    public static final int MAX_RECIPROCATION_REPS = 50;
    public ReciprocationEnchantment() { super(1); }
    public static void processPlayerHurt(DamageSource source, ServerPlayer player) {
        var cloak = player.getItemBySlot(EquipmentSlot.HEAD); if (!cloak.is(MediaworksItems.MAGIC_CLOAK.get())) return;
        var registry = player.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
        int level = registry.getHolder(MediaworksEnchantments.RECIPROCATION).map(h -> EnchantmentHelper.getItemEnchantmentLevel(h, cloak)).orElse(0);
        if (level <= 0) return;
        var holder = IXplatAbstractions.INSTANCE.findHexHolder(cloak); if (holder == null) return;
        var hex = holder.getHex(player.serverLevel()); if (hex == null) return;
        CastingVM.empty(new MediaworksPlayerCastEnv(player, cloak)).queueExecuteAndWrapIotas(hex, player.serverLevel());
    }
}

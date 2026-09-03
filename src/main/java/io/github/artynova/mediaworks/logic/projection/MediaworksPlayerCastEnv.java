package io.github.artynova.mediaworks.logic.projection;

import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

/** Player cast environment that exposes a forced primary stack for cloak-triggered casts. */
public final class MediaworksPlayerCastEnv extends PlayerBasedCastEnv {
    private final ItemStack forcedStack;
    public MediaworksPlayerCastEnv(ServerPlayer caster, ItemStack forcedStack) { super(caster, InteractionHand.OFF_HAND); this.forcedStack = forcedStack; }
    public ItemStack forcedStack() { return forcedStack; }
    @Override protected long extractMediaEnvironment(long cost, boolean simulate) { return extractMediaFromInventory(cost, true, simulate); }
    @Override public InteractionHand getCastingHand() { return InteractionHand.OFF_HAND; }
    @Override public FrozenPigment getPigment() { return IXplatAbstractions.INSTANCE.getPigment(caster); }
}

package io.github.artynova.mediaworks.logic.projection;

import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

final class MediaworksAstralCastEnv extends PlayerBasedCastEnv {
    MediaworksAstralCastEnv(ServerPlayer caster) { super(caster, InteractionHand.MAIN_HAND); }
    @Override protected long extractMediaEnvironment(long cost, boolean simulate) { return extractMediaFromInventory(cost, true, simulate); }
    @Override public InteractionHand getCastingHand() { return InteractionHand.MAIN_HAND; }
    @Override public FrozenPigment getPigment() { return IXplatAbstractions.INSTANCE.getPigment(caster); }
}

package io.github.artynova.mediaworks.casting.mishap;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.api.utils.TreeList;
import io.github.artynova.mediaworks.logic.macula.MaculaServer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;

public final class MishapBlackEye extends Mishap {
    public static final int BLINDNESS_TICKS = 200;
    private final String reason;
    public MishapBlackEye(String reason) { this.reason = reason; }
    @Override public FrozenPigment accentColor(CastingEnvironment env, Context context) { return dyeColor(DyeColor.BLACK); }
    @Override protected Component errorMessage(CastingEnvironment env, Context context) { return error("black_eye." + reason, actionName(context.getName())); }
    @Override public TreeList<Iota> execute(CastingEnvironment env, Context context, TreeList<Iota> stack) {
        env.getCaster().addEffect(new MobEffectInstance(MobEffects.BLINDNESS, BLINDNESS_TICKS));
        MaculaServer.getMacula(env.getCaster()).clear(); MaculaServer.syncContentToClient(env.getCaster());
        return stack;
    }
}

package io.github.artynova.mediaworks.casting.pattern.spell.great;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapImmuneEntity;
import at.petrak.hexcasting.api.misc.MediaConstants;
import io.github.artynova.mediaworks.effect.MediaworksEffects;
import io.github.artynova.mediaworks.api.casting.RavenmindSpellAction;
import io.github.artynova.mediaworks.logic.projection.AstralProjectionServer;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public final class OpAstralProjection implements RavenmindSpellAction {
    public static final long COST_PER_SECOND = MediaConstants.DUST_UNIT * 2;
    @Override public int argc() { return 1; }
    @Override public Result execute(List<? extends Iota> args, CastingEnvironment env, Iota ravenmind) {
        double duration = OperatorUtils.getPositiveDouble(args, 0, 1);
        if (env.getCaster().hasEffect(MediaworksEffects.ASTRAL_PROJECTION)) throw new MishapImmuneEntity(env.getCaster());
        return new Result(new Spell(duration, ravenmind), (long) (duration * COST_PER_SECOND),
                List.of(ParticleSpray.burst(env.getCaster().position(), 1, 30)));
    }
    private record Spell(double duration, Iota ravenmind) implements RenderedSpell {
        @Override public void cast(CastingEnvironment env) {
            env.getCaster().addEffect(new MobEffectInstance(MediaworksEffects.ASTRAL_PROJECTION, (int) (duration * 20), 0, false, false));
            AstralProjectionServer.getProjection(env.getCaster()).setIota(ravenmind);
        }
    }
}

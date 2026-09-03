package io.github.artynova.mediaworks.api.casting;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.sideeffects.EvalSound;
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughMedia;
import at.petrak.hexcasting.api.utils.TreeList;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;

import java.util.ArrayList;
import java.util.List;

/** Contract for spells that consume the VM ravenmind alongside normal stack arguments. */
public interface RavenmindSpellAction extends Action {
    int argc();
    Result execute(List<? extends Iota> args, CastingEnvironment environment, Iota ravenmind);

    default boolean hasCastingSound(CastingEnvironment environment) { return true; }
    default boolean awardsCastingStat(CastingEnvironment environment) { return true; }

    @Override
    default OperationResult operate(CastingEnvironment environment, CastingImage image,
                                    SpellContinuation continuation) {
        TreeList<Iota> stack = image.getStack();
        int argc = argc();
        if (argc > stack.size()) throw new MishapNotEnoughArgs(argc, stack.size());

        TreeList<Iota> args = stack.takeRight(argc);
        TreeList<Iota> stackWithoutArgs = stack.dropRight(argc);
        Iota ravenmind = image.ravenmind().orElse(null);
        Result result = execute(args, environment, ravenmind);

        if (environment.extractMedia(result.media(), true) > 0) {
            throw new MishapNotEnoughMedia(result.media());
        }

        List<OperatorSideEffect> sideEffects = new ArrayList<>();
        if (result.media() > 0) sideEffects.add(new OperatorSideEffect.ConsumeMedia(result.media()));
        if (environment.isEnlightened()) {
            sideEffects.add(new OperatorSideEffect.AttemptSpell(
                    result.spell(), hasCastingSound(environment), awardsCastingStat(environment)));
        }
        for (ParticleSpray spray : result.particles()) {
            sideEffects.add(new OperatorSideEffect.Particles(spray));
        }

        CastingImage newImage = image.copy(
                stackWithoutArgs,
                image.getParenCount(),
                image.getParenthesized(),
                image.getEscapeNext(),
                image.getSimulateNext(),
                image.getOpsConsumed() + 1,
                image.getUserData().copy());
        EvalSound sound = hasCastingSound(environment) ? HexEvalSounds.SPELL.get() : HexEvalSounds.MUTE.get();
        return new OperationResult(newImage, sideEffects, continuation, sound);
    }

    record Result(RenderedSpell spell, long media, List<ParticleSpray> particles) { }
}

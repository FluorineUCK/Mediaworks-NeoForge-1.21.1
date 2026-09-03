package io.github.artynova.mediaworks.casting.pattern.misc;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.misc.MediaConstants;
import io.github.artynova.mediaworks.util.MediaUtils;

import java.util.List;

public final class OpGetMedia implements ConstMediaAction {
    @Override public int getArgc() { return 0; }
    @Override public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) {
        return List.copyOf(OperatorUtils.getAsActionResult((double) MediaUtils.getAvailableContextMedia(env) / MediaConstants.DUST_UNIT));
    }
}

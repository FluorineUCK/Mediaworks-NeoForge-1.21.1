package io.github.artynova.mediaworks.casting.pattern.misc;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.misc.MediaConstants;
import io.github.artynova.mediaworks.util.MediaUtils;
import java.util.List;

public final class OpGetPosMedia implements ConstMediaAction {
    @Override public int getArgc() { return 1; }
    @Override public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) {
        return List.copyOf(OperatorUtils.getAsActionResult((double) MediaUtils.getPosMedia(OperatorUtils.getVec3(args, 0, 1), env.getWorld()) / MediaConstants.DUST_UNIT));
    }
}

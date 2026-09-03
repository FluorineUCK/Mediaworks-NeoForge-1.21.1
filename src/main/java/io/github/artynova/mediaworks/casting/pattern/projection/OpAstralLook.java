package io.github.artynova.mediaworks.casting.pattern.projection;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.NullIota;
import io.github.artynova.mediaworks.logic.projection.AstralPosition;
import io.github.artynova.mediaworks.logic.projection.AstralProjectionServer;
import io.github.artynova.mediaworks.util.MathUtils;
import java.util.List;

public final class OpAstralLook implements ConstMediaAction {
    @Override public int getArgc() { return 0; }
    @Override public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) {
        AstralPosition position = AstralProjectionServer.getProjection(env.getCaster()).getPosition();
        return position == null ? List.of(new NullIota()) : List.copyOf(OperatorUtils.getAsActionResult(MathUtils.getRotationVector(position.pitch(), position.yaw())));
    }
}

package io.github.artynova.mediaworks.casting.pattern.projection;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.NullIota;
import io.github.artynova.mediaworks.logic.projection.AstralPosition;
import io.github.artynova.mediaworks.logic.projection.AstralProjectionServer;
import java.util.List;

public final class OpAstralPos implements ConstMediaAction {
    @Override public int getArgc() { return 0; }
    @Override public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) {
        AstralPosition position = AstralProjectionServer.getProjection(env.getCaster()).getPosition();
        if (position == null) return List.of(new NullIota());
        return List.copyOf(OperatorUtils.getAsActionResult(position.coordinates().add(0, env.getCaster().getEyeHeight(), 0)));
    }
}

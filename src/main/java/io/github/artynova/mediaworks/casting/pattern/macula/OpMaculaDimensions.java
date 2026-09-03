package io.github.artynova.mediaworks.casting.pattern.macula;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import io.github.artynova.mediaworks.logic.macula.Macula;
import io.github.artynova.mediaworks.logic.macula.MaculaServer;
import java.util.List;

public final class OpMaculaDimensions implements ConstMediaAction {
    @Override public int getArgc() { return 0; }
    @Override public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) {
        Macula macula = MaculaServer.getMacula(env.getCaster());
        return List.of(new DoubleIota(macula.getWidth()), new DoubleIota(macula.getHeight()));
    }
}

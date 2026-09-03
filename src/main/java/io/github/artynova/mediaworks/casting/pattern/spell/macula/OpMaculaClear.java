package io.github.artynova.mediaworks.casting.pattern.spell.macula;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.misc.MediaConstants;
import io.github.artynova.mediaworks.logic.macula.MaculaServer;
import java.util.List;

public final class OpMaculaClear implements SpellAction {
    @Override public int getArgc() { return 0; }
    @Override public Result execute(List<? extends Iota> args, CastingEnvironment env) {
        return new Result(e -> { MaculaServer.getMacula(e.getCaster()).clear(); MaculaServer.syncContentToClient(e.getCaster()); }, MediaConstants.DUST_UNIT / 100, List.of(), 1);
    }
}

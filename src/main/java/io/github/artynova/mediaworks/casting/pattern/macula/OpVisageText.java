package io.github.artynova.mediaworks.casting.pattern.macula;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import io.github.artynova.mediaworks.casting.pattern.MediaworksOperatorUtils;
import io.github.artynova.mediaworks.logic.macula.TextVisage;
import java.util.List;

public final class OpVisageText implements ConstMediaAction {
    private final boolean bounded;
    public OpVisageText(boolean bounded) { this.bounded = bounded; }
    @Override public int getArgc() { return bounded ? 3 : 1; }
    @Override public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) {
        int width = bounded ? (int) Math.round(OperatorUtils.getPositiveDouble(args, 0, 3)) : TextVisage.UNLIMITED_SIZE;
        int height = bounded ? (int) Math.round(OperatorUtils.getPositiveDouble(args, 1, 3)) : TextVisage.UNLIMITED_SIZE;
        TextVisage visage = bounded ? new TextVisage(TextVisage.captureText(args.get(2)), width, height)
                : new TextVisage(TextVisage.captureText(args.get(0)));
        return MediaworksOperatorUtils.asActionResult(visage);
    }
}

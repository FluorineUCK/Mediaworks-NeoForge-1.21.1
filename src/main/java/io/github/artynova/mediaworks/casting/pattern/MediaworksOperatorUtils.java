package io.github.artynova.mediaworks.casting.pattern;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import io.github.artynova.mediaworks.api.logic.macula.Visage;
import io.github.artynova.mediaworks.casting.iota.VisageIota;

import java.util.List;

public final class MediaworksOperatorUtils {
    private MediaworksOperatorUtils() {}
    public static Visage getVisage(List<? extends Iota> args, int index, int argc) {
        if (index >= args.size()) throw new MishapNotEnoughArgs(index + 1, args.size());
        Iota value = args.get(index);
        if (value instanceof VisageIota visage) return visage.getVisage();
        throw MishapInvalidIota.ofType(value, argc == 0 ? index : argc - index - 1, "visage");
    }
    public static List<Iota> asActionResult(Visage visage) { return List.of(new VisageIota(visage)); }
}

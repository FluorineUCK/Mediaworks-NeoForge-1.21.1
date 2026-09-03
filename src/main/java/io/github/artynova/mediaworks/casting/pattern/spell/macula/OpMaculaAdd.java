package io.github.artynova.mediaworks.casting.pattern.spell.macula;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.misc.MediaConstants;
import io.github.artynova.mediaworks.casting.pattern.MediaworksOperatorUtils;
import io.github.artynova.mediaworks.api.casting.OverloadedSpellAction;
import io.github.artynova.mediaworks.casting.mishap.MishapBlackEye;
import io.github.artynova.mediaworks.logic.macula.Macula;
import io.github.artynova.mediaworks.logic.macula.MaculaServer;
import io.github.artynova.mediaworks.logic.macula.VisageEntry;
import net.minecraft.core.BlockPos;
import java.util.List;

public final class OpMaculaAdd implements OverloadedSpellAction {
    @Override public int argc(List<? extends Iota> allArgs) {
        if (allArgs.size() <= 2) return 2;
        return allArgs.get(allArgs.size() - 1) instanceof DoubleIota ? 3 : 2;
    }
    @Override public Result execute(List<? extends Iota> args, int argc, CastingEnvironment env) {
        Macula macula = MaculaServer.getMacula(env.getCaster());
        if (macula.checkFullness()) throw new MishapBlackEye("visage_cap");
        BlockPos origin = BlockPos.containing(OperatorUtils.getVec3(args, 0, argc));
        var visage = MediaworksOperatorUtils.getVisage(args, 1, argc);
        double ticks = argc == 2 ? -1 : OperatorUtils.getPositiveDouble(args, 2, argc) * 20;
        if (ticks > Macula.MAX_FLEETING_VISAGE_TICKS) throw new MishapBlackEye("duration_cap");
        long now = env.getWorld().getGameTime(), end = ticks < 0 ? -1 : now + (long) ticks;
        VisageEntry entry = new VisageEntry(visage, origin, now, end);
        return new Result(e -> { MaculaServer.getMacula(e.getCaster()).add(entry); MaculaServer.syncContentToClient(e.getCaster()); }, MediaConstants.DUST_UNIT / 100, List.of());
    }
}

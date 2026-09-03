package io.github.artynova.mediaworks.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public final class PlayerHelpers {
    private PlayerHelpers() {
    }

    public static List<ServerPlayer> playersNear(Vec3 pos, double radius, ServerLevel level, Predicate<ServerPlayer> extraTest) {
        double radiusSquared = radius * radius;
        return level.players().stream()
                .filter(player -> player.distanceToSqr(pos) <= radiusSquared && extraTest.test(player))
                .toList();
    }

    public static List<ServerPlayer> playersNear(Vec3 pos, double radius, ServerLevel level) {
        return playersNear(pos, radius, level, player -> true);
    }
}

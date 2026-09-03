package io.github.artynova.mediaworks.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class MathUtils {
    private MathUtils() {
    }

    public static float slowdownInterpolationProgress(float point, float leftBound, float rightBound, float growthCoeff) {
        return (float) (1 - Math.pow(growthCoeff, -((point - leftBound) / (rightBound - leftBound))));
    }

    public static double geomProgressionSum(double first, double ratio, int num) {
        if (num <= 0) return 0;
        if (ratio == 1) return first * num;
        return first * (1 - Math.pow(ratio, num)) / (1 - ratio);
    }

    public static Vec3 getRotationVector(float pitch, float yaw) {
        float pitchRad = pitch * Mth.DEG_TO_RAD;
        float yawRad = -yaw * Mth.DEG_TO_RAD;
        float yawCos = Mth.cos(yawRad);
        float yawSin = Mth.sin(yawRad);
        float pitchCos = Mth.cos(pitchRad);
        float pitchSin = Mth.sin(pitchRad);
        return new Vec3(yawSin * pitchCos, -pitchSin, yawCos * pitchCos);
    }
}

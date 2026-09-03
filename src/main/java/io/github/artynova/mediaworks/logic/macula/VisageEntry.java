package io.github.artynova.mediaworks.logic.macula;

import io.github.artynova.mediaworks.api.logic.macula.Visage;
import net.minecraft.core.BlockPos;

public final class VisageEntry {
    public static final long FADE_TICKS = 20;
    private final Visage visage;
    private final BlockPos origin;
    private final long startTime;
    private final long endTime;

    public VisageEntry(Visage visage, BlockPos origin, long startTime, long endTime) {
        if (startTime > endTime && endTime != -1) throw new IllegalArgumentException("Malformed visage lifetime");
        if (endTime < 0 && endTime != -1) throw new IllegalArgumentException("End time must be natural or -1");
        this.visage = visage;
        this.origin = origin;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Visage getVisage() { return visage; }
    public BlockPos getOrigin() { return origin; }
    public long getStartTime() { return startTime; }
    public long getEndTime() { return endTime; }
    public boolean doFadeout() { return endTime == -1 || endTime - startTime > FADE_TICKS; }
    public boolean hasTimedOut(long currentTime) { return endTime > -1 && endTime <= currentTime; }
}

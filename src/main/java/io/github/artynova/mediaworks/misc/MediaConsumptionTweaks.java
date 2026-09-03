package io.github.artynova.mediaworks.misc;

import at.petrak.hexcasting.api.addldata.ADMediaHolder;

public final class MediaConsumptionTweaks {
    public static final int BATTERY_PRIORITY = ADMediaHolder.BATTERY_PRIORITY;
    private MediaConsumptionTweaks() {}
    public static int compareMediaItem(ADMediaHolder first, ADMediaHolder second) {
        return Integer.compare(first.getConsumptionPriority(), second.getConsumptionPriority());
    }
}

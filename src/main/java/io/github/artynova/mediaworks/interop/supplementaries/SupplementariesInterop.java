package io.github.artynova.mediaworks.interop.supplementaries;

import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.github.artynova.mediaworks.api.logic.media.MediaDiscoveryHandler;
import io.github.artynova.mediaworks.api.logic.media.PackagedHexData;

public final class SupplementariesInterop {
    public static final String MOD_ID = "supplementaries";
    private SupplementariesInterop() {}
    public static boolean isPresent() { return IXplatAbstractions.INSTANCE.isModPresent(MOD_ID); }
    public static void init() {
        MediaDiscoveryHandler.addCustomPackagedHexDiscoverer(environment -> {
            var stack = environment.queryForMatchingStack(candidate -> SackMediaHolder.isSack(candidate.getItem()));
            if (stack.isEmpty()) return null;
            SackMediaHolder holder = new SackMediaHolder(stack);
            return new PackagedHexData(null, holder);
        });
    }
}

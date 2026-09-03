package io.github.artynova.mediaworks.api.logic.media;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/** Extension point for add-ons that make an otherwise hidden packaged hex available to Mediaworks. */
public final class MediaDiscoveryHandler {
    private static final List<Function<CastingEnvironment, @Nullable PackagedHexData>> DISCOVERERS = new CopyOnWriteArrayList<>();
    private MediaDiscoveryHandler() {}

    public static void addCustomPackagedHexDiscoverer(Function<CastingEnvironment, @Nullable PackagedHexData> discoverer) {
        DISCOVERERS.add(discoverer);
    }

    public static @Nullable PackagedHexData collectCustomPackagedHex(CastingEnvironment environment) {
        for (Function<CastingEnvironment, PackagedHexData> discoverer : DISCOVERERS) {
            PackagedHexData data = discoverer.apply(environment);
            if (data != null) return data;
        }
        return null;
    }
}

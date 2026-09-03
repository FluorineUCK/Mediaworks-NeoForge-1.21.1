package io.github.artynova.mediaworks.logic.macula;

import io.github.artynova.mediaworks.api.logic.macula.VisageType;
import io.github.artynova.mediaworks.api.registry.MediaworksRegistries;

import java.util.function.Supplier;

public final class MediaworksVisageTypes {
    public static final Supplier<VisageType<?>> TEXT = MediaworksRegistries.register("text", () -> TextVisage.TYPE);
    private MediaworksVisageTypes() {}
    public static void register(net.neoforged.bus.api.IEventBus ignored) { /* class loading installs the deferred entry */ }
}

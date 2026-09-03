package io.github.artynova.mediaworks;

import net.neoforged.bus.api.IEventBus;

/** Side-safe reflective boundary for the client source set. */
public final class MediaworksClientBootstrap {
    private MediaworksClientBootstrap() {}

    public static void init(IEventBus modBus) {
        try {
            Class<?> client = Class.forName("io.github.artynova.mediaworks.client.MediaworksClient");
            client.getMethod("init", IEventBus.class).invoke(null, modBus);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to initialize Mediaworks client", exception);
        }
    }
}

package io.github.artynova.mediaworks.misc;

import net.neoforged.bus.api.IEventBus;

public final class MediaworksMisc {
    private MediaworksMisc() {}

    public static void register(IEventBus eventBus) {
        eventBus.addListener(LootTableModifiers::injectCloakLoot);
    }
}

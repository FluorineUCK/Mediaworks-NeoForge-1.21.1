package io.github.artynova.mediaworks.compat;

import java.util.Set;

/** Resolves action ownership when optional addons expose the same semantics and signatures. */
public final class PatternRegistrationPolicy {
    private static final Set<String> HEXPOSE_OVERLAPS = Set.of("get_media", "get_entity_media");

    private PatternRegistrationPolicy() {}

    public static boolean shouldRegister(String id, boolean hexposeLoaded) {
        return !hexposeLoaded || !HEXPOSE_OVERLAPS.contains(id);
    }
}


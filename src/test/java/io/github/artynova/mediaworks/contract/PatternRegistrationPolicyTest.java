package io.github.artynova.mediaworks.contract;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PatternRegistrationPolicyTest {
    private static boolean shouldRegister(String id, boolean hexposeLoaded) throws Exception {
        Class<?> policy = Class.forName("io.github.artynova.mediaworks.compat.PatternRegistrationPolicy");
        Method method = policy.getMethod("shouldRegister", String.class, boolean.class);
        return (boolean) method.invoke(null, id, hexposeLoaded);
    }

    @Test
    void standaloneMediaworksKeepsAllLegacyPatterns() throws Exception {
        for (String id : Set.of("get_media", "get_entity_media", "get_pos_media", "cloak/read")) {
            assertTrue(shouldRegister(id, false), id);
        }
    }

    @Test
    void hexposeSuppressesOnlyTheTwoSemanticAndSignatureOverlaps() throws Exception {
        assertFalse(shouldRegister("get_media", true));
        assertFalse(shouldRegister("get_entity_media", true));
        assertTrue(shouldRegister("get_pos_media", true));
        assertTrue(shouldRegister("cloak/read", true));
    }
}


package io.github.artynova.mediaworks.contract;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MediaAvailabilityContractTest {
    @Test
    void availableMediaIsReadFromTheActualEnvironmentSourcesWithoutOvercast() throws IOException {
        String source = Files.readString(Path.of(
                "src/main/java/io/github/artynova/mediaworks/util/MediaUtils.java"));

        assertFalse(source.contains("environment.extractMedia(Long.MAX_VALUE, true)"),
                "extractMedia returns the unpaid remainder, not available media");
        assertTrue(source.contains("instanceof CircleCastEnv"));
        assertTrue(source.contains("getImpetus()"));
        assertTrue(source.contains("instanceof MediaworksPlayerCastEnv"));
        assertTrue(source.contains("forcedStack()"));
        assertTrue(source.contains("instanceof PackagedItemCastEnv"));
        assertTrue(source.contains("getPlayerMedia(caster)"));
        assertTrue(source.contains("withdrawMedia(-1, true)"),
                "availability must be measured by simulated withdrawal");
    }
}


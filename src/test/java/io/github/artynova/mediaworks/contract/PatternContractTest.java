package io.github.artynova.mediaworks.contract;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PatternContractTest {
    @Test
    void allTwelveLegacyPatternsKeepIdsAnglesAndStartingDirections() throws IOException {
        // Plain JUnit is not an FML launch, so loading HexRegistries from a static
        // initializer is invalid. The runtime registry is covered by runServer;
        // this unit test freezes the source-level id/signature contract.
        String source = Files.readString(Path.of(
                "src/main/java/io/github/artynova/mediaworks/casting/pattern/MediaworksPatterns.java"));
        assertEquals(12, source.lines().filter(line -> line.stripLeading().startsWith("add(\"")).count());
        assertTrue(source.contains("add(\"astral_projection\", \"qdadwewewdadeadwddaaedqdeddew\", HexDir.NORTH_WEST"));
        assertTrue(source.contains("new OpAstralProjection(), true"));
        assertTrue(source.contains("add(\"cloak/read\", \"adda\", HexDir.EAST"));
        assertTrue(source.contains("new OpCloakRead(), false"));
    }
}

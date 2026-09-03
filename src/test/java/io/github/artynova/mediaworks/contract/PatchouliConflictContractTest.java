package io.github.artynova.mediaworks.contract;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PatchouliConflictContractTest {
    @Test
    void pagesForSuppressedActionsAreHiddenWhenHexposeIsLoaded() throws IOException {
        String json = Files.readString(Path.of(
                "src/main/resources/assets/hexcasting/patchouli_books/thehexbook/en_us/entries/patterns/media_patterns.json"));

        assertEquals(3, count(json, "\"flag\": \"!mod:hexpose\""),
                "get_media plus both get_entity_media pages must be gated");
        assertTrue(objectContaining(json, "\"op_id\": \"mediaworks:get_media\"")
                .contains("\"flag\": \"!mod:hexpose\""));
        assertTrue(objectContaining(json, "\"op_id\": \"mediaworks:get_entity_media\"")
                .contains("\"flag\": \"!mod:hexpose\""));
        assertFalse(objectContaining(json, "\"op_id\": \"mediaworks:get_pos_media\"")
                .contains("\"flag\": \"!mod:hexpose\""));
        assertTrue(json.contains("\"type\": \"patchouli:text\""),
                "the gated explanatory page must be an object, not a bare string");
    }

    private static long count(String haystack, String needle) {
        return haystack.lines().filter(line -> line.contains(needle)).count();
    }

    private static String objectContaining(String json, String marker) {
        int markerAt = json.indexOf(marker);
        assertTrue(markerAt >= 0, marker);
        int start = json.lastIndexOf('{', markerAt);
        int end = json.indexOf('}', markerAt);
        assertTrue(start >= 0 && end > start, marker);
        return json.substring(start, end + 1);
    }
}


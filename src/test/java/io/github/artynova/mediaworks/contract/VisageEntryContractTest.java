package io.github.artynova.mediaworks.contract;

import io.github.artynova.mediaworks.logic.macula.VisageEntry;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisageEntryContractTest {
    @Test
    void fleetingVisageRejectsMalformedLifetimeAndTimesOutAtEndTick() {
        assertThrows(IllegalArgumentException.class,
                () -> new VisageEntry(null, BlockPos.ZERO, 20, 19));
        VisageEntry entry = new VisageEntry(null, BlockPos.ZERO, 20, 41);
        assertFalse(entry.hasTimedOut(40));
        assertTrue(entry.hasTimedOut(41));
        assertTrue(entry.doFadeout());
    }
}

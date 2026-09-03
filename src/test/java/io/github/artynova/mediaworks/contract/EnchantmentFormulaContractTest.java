package io.github.artynova.mediaworks.contract;

import io.github.artynova.mediaworks.enchantment.LocaleMagnificationEnchantment;
import io.github.artynova.mediaworks.enchantment.MediaShieldEnchantment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnchantmentFormulaContractTest {
    @Test
    void localeMagnificationPreservesOriginalCurve() {
        assertEquals(0.0, LocaleMagnificationEnchantment.getIncreaseForLevel(0));
        assertEquals(8.0, LocaleMagnificationEnchantment.getIncreaseForLevel(1));
        assertEquals(16.0, LocaleMagnificationEnchantment.getIncreaseForLevel(2));
        assertEquals(32.0, LocaleMagnificationEnchantment.getIncreaseForLevel(3));
        assertEquals(40.0, LocaleMagnificationEnchantment.getIncreaseForLevel(4));
    }

    @Test
    void mediaShieldPreservesDiminishingReturns() {
        assertEquals(0.0f, MediaShieldEnchantment.getAbsorptionRatioForLevel(0));
        assertEquals(0.4f, MediaShieldEnchantment.getAbsorptionRatioForLevel(4), 1.0e-6f);
        assertEquals(0.45f, MediaShieldEnchantment.getAbsorptionRatioForLevel(5), 1.0e-6f);
        assertTrue(MediaShieldEnchantment.getAbsorptionRatioForLevel(20) < 0.6f);
    }
}

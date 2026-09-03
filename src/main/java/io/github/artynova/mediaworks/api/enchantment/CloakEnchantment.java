package io.github.artynova.mediaworks.api.enchantment;

/** Shared constants for the data-driven 1.21 cloak enchantments. */
public abstract class CloakEnchantment {
    private final int maxLevel;
    protected CloakEnchantment(int maxLevel) { this.maxLevel = maxLevel; }
    public int getMaxLevel() { return maxLevel; }
    public boolean isTreasure() { return true; }
    public boolean isAvailableForRandomSelection() { return false; }
}

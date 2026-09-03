package io.github.artynova.mediaworks.misc;

import io.github.artynova.mediaworks.Mediaworks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.event.LootTableLoadEvent;

public final class LootTableModifiers {
    public static final String COLORED_MAGIC_CLOAK = "colored_magic_cloak";
    public static final ResourceKey<LootTable> ANCIENT_CITY_ADDITIONS = ResourceKey.create(
        Registries.LOOT_TABLE,
        Mediaworks.id("chests/ancient_city_additions")
    );
    public static final int ANCIENT_CITY_ADDITIONS_WEIGHT = 14;

    private LootTableModifiers() {}

    public static void injectCloakLoot(LootTableLoadEvent event) {
        if (!event.getKey().equals(BuiltInLootTables.ANCIENT_CITY)) return;
        LootPool pool = LootPool.lootPool()
            .name("mediaworks_magic_cloak")
            .setRolls(ConstantValue.exactly(1.0F))
            .add(NestedLootTable.lootTableReference(ANCIENT_CITY_ADDITIONS).setWeight(ANCIENT_CITY_ADDITIONS_WEIGHT))
            .build();
        event.getTable().addPool(pool);
    }
}

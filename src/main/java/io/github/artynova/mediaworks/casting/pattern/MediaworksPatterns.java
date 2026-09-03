package io.github.artynova.mediaworks.casting.pattern;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexRegistries;
import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.compat.PatternRegistrationPolicy;
import io.github.artynova.mediaworks.casting.pattern.macula.OpMaculaDimensions;
import io.github.artynova.mediaworks.casting.pattern.macula.OpVisageText;
import io.github.artynova.mediaworks.casting.pattern.misc.OpCloakRead;
import io.github.artynova.mediaworks.casting.pattern.misc.OpGetEntityMedia;
import io.github.artynova.mediaworks.casting.pattern.misc.OpGetMedia;
import io.github.artynova.mediaworks.casting.pattern.misc.OpGetPosMedia;
import io.github.artynova.mediaworks.casting.pattern.projection.OpAstralLook;
import io.github.artynova.mediaworks.casting.pattern.projection.OpAstralPos;
import io.github.artynova.mediaworks.casting.pattern.spell.great.OpAstralProjection;
import io.github.artynova.mediaworks.casting.pattern.spell.macula.OpMaculaAdd;
import io.github.artynova.mediaworks.casting.pattern.spell.macula.OpMaculaClear;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MediaworksPatterns {
    public record Definition(String id, String signature, HexDir start, Action action, boolean perWorld) {
        ActionRegistryEntry entry() { return new ActionRegistryEntry(HexPattern.fromAngles(signature, start), action); }
    }

    private static final Map<String, Definition> DEFINITIONS = new LinkedHashMap<>();
    private static final DeferredRegister<ActionRegistryEntry> ACTIONS =
            DeferredRegister.create(HexRegistries.ACTION, Mediaworks.MOD_ID);
    private static boolean registrationsCreated;

    static {
        // Kept in Hex Casting's per-world pattern tag below; the boolean is
        // retained here as an explicit runtime/contract marker for that
        // randomized world-specific signature semantic.
        add("astral_projection", "qdadwewewdadeadwddaaedqdeddew", HexDir.NORTH_WEST, new OpAstralProjection(), true); // per-world pattern
        add("astral_pos", "qaqqqqaq", HexDir.NORTH_EAST, new OpAstralPos(), false);
        add("astral_look", "waawaq", HexDir.NORTH_EAST, new OpAstralLook(), false);
        add("macula/add", "wddaaddw", HexDir.NORTH_WEST, new OpMaculaAdd(), false);
        add("macula/clear", "awawa", HexDir.WEST, new OpMaculaClear(), false);
        add("macula/dimensions", "aawawaa", HexDir.NORTH_EAST, new OpMaculaDimensions(), false);
        add("visage/text/unbounded", "aaqdwdwd", HexDir.NORTH_EAST, new OpVisageText(false), false);
        add("visage/text/bounded", "aaqdwdwde", HexDir.NORTH_EAST, new OpVisageText(true), false);
        add("get_media", "dde", HexDir.WEST, new OpGetMedia(), false);
        add("get_entity_media", "ddew", HexDir.WEST, new OpGetEntityMedia(), false);
        add("get_pos_media", "ddewa", HexDir.WEST, new OpGetPosMedia(), false);
        add("cloak/read", "adda", HexDir.EAST, new OpCloakRead(), false);
    }
    private MediaworksPatterns() {}
    private static void add(String id, String sig, HexDir start, Action action, boolean perWorld) { DEFINITIONS.put(id, new Definition(id, sig, start, action, perWorld)); }
    public static List<Definition> definitions() { return List.copyOf(DEFINITIONS.values()); }
    public static Definition definition(String id) { return DEFINITIONS.get(id); }
    public static synchronized void register(IEventBus bus) {
        if (registrationsCreated) return;

        boolean hexposeLoaded = ModList.get().isLoaded("hexpose");
        DEFINITIONS.values().stream()
                .filter(definition -> PatternRegistrationPolicy.shouldRegister(definition.id(), hexposeLoaded))
                .forEach(definition -> ACTIONS.register(definition.id(), definition::entry));
        ACTIONS.register(bus);
        registrationsCreated = true;
    }
}

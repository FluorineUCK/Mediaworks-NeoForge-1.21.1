package io.github.artynova.mediaworks.interop.patchouli;

import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.github.artynova.mediaworks.interop.moreiotas.MoreIotasInterop;
import io.github.artynova.mediaworks.interop.supplementaries.SupplementariesInterop;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;

public final class PatchouliInterop {
    public static final String MOD_ID = "patchouli";
    public static final String ANY_CONTAINER_INTEROP_FLAG = "mediaworks:any_container_interop";
    public static final List<String> INTEROP_ENTRY_MODS = List.of(MoreIotasInterop.MOD_ID, SupplementariesInterop.MOD_ID);
    private PatchouliInterop() {}

    public static boolean isPresent() { return IXplatAbstractions.INSTANCE.isModPresent(MOD_ID); }
    public static void init() {
        if (!isPresent()) return;
        if (INTEROP_ENTRY_MODS.stream().anyMatch(IXplatAbstractions.INSTANCE::isModPresent))
            PatchouliAPI.get().setConfigFlag("hexcasting:any_interop", true);
        if (SupplementariesInterop.isPresent()) PatchouliAPI.get().setConfigFlag(ANY_CONTAINER_INTEROP_FLAG, true);
    }
}

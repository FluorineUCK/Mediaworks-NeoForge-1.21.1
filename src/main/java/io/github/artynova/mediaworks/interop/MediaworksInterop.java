package io.github.artynova.mediaworks.interop;

import io.github.artynova.mediaworks.interop.patchouli.PatchouliInterop;
import io.github.artynova.mediaworks.interop.curios.CuriosInterop;
import io.github.artynova.mediaworks.interop.supplementaries.SupplementariesInterop;
import net.neoforged.fml.ModList;

public final class MediaworksInterop {
    private MediaworksInterop() {}
    public static void init() {
        if (ModList.get().isLoaded("patchouli")) PatchouliInterop.init();
        if (ModList.get().isLoaded("curios")) CuriosInterop.init();
        if (ModList.get().isLoaded("supplementaries")) SupplementariesInterop.init();
    }
}

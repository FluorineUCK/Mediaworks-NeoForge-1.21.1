package io.github.artynova.mediaworks.api;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public final class MediaworksAPI {
    public static final String MOD_ID = "mediaworks";
    public static final Logger LOGGER = LogUtils.getLogger();

    private MediaworksAPI() {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}

package io.github.artynova.mediaworks;

import io.github.artynova.mediaworks.api.registry.MediaworksRegistries;
import io.github.artynova.mediaworks.casting.iota.MediaworksIotaTypes;
import io.github.artynova.mediaworks.casting.pattern.MediaworksPatterns;
import io.github.artynova.mediaworks.effect.MediaworksEffects;
import io.github.artynova.mediaworks.interop.MediaworksInterop;
import io.github.artynova.mediaworks.item.MediaworksItems;
import io.github.artynova.mediaworks.logic.macula.MediaworksVisageTypes;
import io.github.artynova.mediaworks.misc.MediaworksMisc;
import io.github.artynova.mediaworks.networking.MediaworksNetworking;
import io.github.artynova.mediaworks.sound.MediaworksSounds;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Mediaworks.MOD_ID)
public final class Mediaworks {
    public static final String MOD_ID = "mediaworks";

    public Mediaworks(IEventBus modBus, ModContainer container) {
        Mediaworks.init(modBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MediaworksClientBootstrap.init(modBus);
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void init(IEventBus modBus) {
        MediaworksAttachments.register(modBus);
        MediaworksRegistries.register(modBus);
        MediaworksItems.register(modBus);
        io.github.artynova.mediaworks.item.MagicCloakItem.initPackagedHexDiscovery();
        MediaworksEffects.register(modBus);
        MediaworksSounds.register(modBus);
        MediaworksIotaTypes.register(modBus);
        MediaworksVisageTypes.register(modBus);
        modBus.addListener(MediaworksNetworking::register);
        MediaworksPatterns.register(modBus);

        MediaworksEvents.register(NeoForge.EVENT_BUS);
        MediaworksMisc.register(NeoForge.EVENT_BUS);
        MediaworksInterop.init();
    }
}

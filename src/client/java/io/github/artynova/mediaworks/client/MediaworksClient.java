package io.github.artynova.mediaworks.client;

import io.github.artynova.mediaworks.client.macula.MaculaClient;
import io.github.artynova.mediaworks.client.projection.AstralProjectionClient;
import io.github.artynova.mediaworks.item.MediaworksItems;
import io.github.artynova.mediaworks.networking.MediaworksClientPacketBridge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;

/** Physical-client entrypoint. No class in this package is linked on a dedicated server. */
public final class MediaworksClient {
    private MediaworksClient() {}

    public static void init(IEventBus modBus) {
        modBus.addListener(MediaworksClient::registerItemColors);
        MediaworksClientPacketBridge.install(new ClientPacketHandler());
        NeoForge.EVENT_BUS.addListener(AstralProjectionClient::onClientTick);
        NeoForge.EVENT_BUS.addListener(AstralProjectionClient::onLogout);
        NeoForge.EVENT_BUS.addListener(AstralProjectionClient::onInteraction);
        NeoForge.EVENT_BUS.addListener(AstralProjectionClient::onMouseScroll);
        NeoForge.EVENT_BUS.addListener(AstralProjectionClient::onFog);
        NeoForge.EVENT_BUS.addListener(AstralProjectionClient::onFogColor);
        NeoForge.EVENT_BUS.addListener(AstralProjectionClient::onGuiRender);
        NeoForge.EVENT_BUS.addListener(MaculaClient::onClientTick);
        NeoForge.EVENT_BUS.addListener(MaculaClient::onLogin);
        NeoForge.EVENT_BUS.addListener(MaculaClient::onLogout);
        NeoForge.EVENT_BUS.addListener(MaculaClient::onGuiRender);
    }

    private static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        // MagicCloak client color registration preserves the original two-layer renderer contract.
        ItemColor color = (stack, tint) -> tint > 0 ? 0xFFFFFFFF
                : 0xFF000000 | DyedItemColor.getOrDefault(stack, 0x5B4533);
        event.register(color, MediaworksItems.MAGIC_CLOAK.get());
    }

    private static final class ClientPacketHandler implements MediaworksClientPacketBridge.ClientHandler {
        @Override public void syncAstralPosition(io.github.artynova.mediaworks.logic.projection.AstralPosition position) {
            AstralProjectionClient.syncFromServer(position);
        }
        @Override public void endProjection() {
            AstralProjectionClient.endProjection();
        }
        @Override public void spawnHexParticles(at.petrak.hexcasting.api.casting.ParticleSpray spray,
                                                at.petrak.hexcasting.api.pigment.FrozenPigment pigment) {
            if (Minecraft.getInstance().level == null) return;
            int color = pigment.getColorProvider().getColor(0, spray.getPos());
            var options = new at.petrak.hexcasting.common.particles.ConjureParticleOptions(color);
            var random = Minecraft.getInstance().level.random;
            for (int i = 0; i < spray.getCount(); i++) {
                double fuzz = spray.getFuzziness();
                double vx = spray.getVel().x + random.nextGaussian() * fuzz;
                double vy = spray.getVel().y + random.nextGaussian() * fuzz;
                double vz = spray.getVel().z + random.nextGaussian() * fuzz;
                Minecraft.getInstance().level.addParticle(options,
                        spray.getPos().x, spray.getPos().y, spray.getPos().z, vx, vy, vz);
            }
        }
        @Override public void syncMacula(net.minecraft.nbt.CompoundTag tag) {
            MaculaClient.syncFromServer(tag);
        }
    }
}

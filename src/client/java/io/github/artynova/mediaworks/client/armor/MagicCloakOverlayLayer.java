package io.github.artynova.mediaworks.client.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.item.MagicCloakItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

/** Renders the original non-dyeable embroidery above the cloak's dyed base. */
final class MagicCloakOverlayLayer extends GeoRenderLayer<MagicCloakItem> {
    private static final ResourceLocation OVERLAY =
            Mediaworks.id("textures/models/armor/magic_cloak_overlay.png");

    MagicCloakOverlayLayer(GeoRenderer<MagicCloakItem> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MagicCloakItem animatable, BakedGeoModel bakedModel,
                       RenderType ignored, MultiBufferSource bufferSource, VertexConsumer ignoredBuffer,
                       float partialTick, int packedLight, int packedOverlay) {
        RenderType overlayType = RenderType.armorCutoutNoCull(OVERLAY);
        boolean foil = renderer instanceof MagicCloakRenderer cloakRenderer
                && cloakRenderer.getCurrentStack() != null
                && cloakRenderer.getCurrentStack().hasFoil();
        VertexConsumer overlayBuffer = ItemRenderer.getArmorFoilBuffer(bufferSource, overlayType, foil);
        getRenderer().reRender(
                bakedModel,
                poseStack,
                bufferSource,
                animatable,
                overlayType,
                overlayBuffer,
                partialTick,
                packedLight,
                packedOverlay,
                0xFFFFFFFF
        );
    }
}

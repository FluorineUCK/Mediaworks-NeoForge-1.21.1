package io.github.artynova.mediaworks.client.armor;

import io.github.artynova.mediaworks.Mediaworks;
import io.github.artynova.mediaworks.item.MagicCloakItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

/** GeckoLib 4 model backed by the original Mediaworks cloak geometry and art. */
public final class MagicCloakModel extends GeoModel<MagicCloakItem> {
    private static final ResourceLocation MODEL = Mediaworks.id("geo/magic_cloak.geo.json");
    private static final ResourceLocation TEXTURE = Mediaworks.id("textures/models/armor/magic_cloak.png");
    private static final ResourceLocation ANIMATION = Mediaworks.id("animations/magic_cloak.animation.json");

    @Override public ResourceLocation getModelResource(MagicCloakItem animatable) { return MODEL; }
    @Override public ResourceLocation getTextureResource(MagicCloakItem animatable) { return TEXTURE; }
    @Override public ResourceLocation getAnimationResource(MagicCloakItem animatable) { return ANIMATION; }
}

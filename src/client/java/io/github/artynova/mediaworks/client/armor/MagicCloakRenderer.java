package io.github.artynova.mediaworks.client.armor;

import io.github.artynova.mediaworks.item.MagicCloakItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.Color;

/** Physical-client provider for the cloak's original GeckoLib armor model. */
public final class MagicCloakRenderer extends GeoArmorRenderer<MagicCloakItem> {
    public MagicCloakRenderer() {
        super(new MagicCloakModel());
        addRenderLayer(new MagicCloakOverlayLayer(this));
    }

    @Override
    public Color getRenderColor(MagicCloakItem animatable, float partialTick, int packedLight) {
        ItemStack stack = getCurrentStack();
        return Color.ofOpaque(stack == null || stack.isEmpty()
                ? MagicCloakItem.DEFAULT_COLOR
                : animatable.getColor(stack));
    }

    /**
     * The cloak is equipped in the head slot, but its original model intentionally
     * spans both {@code armorHead} and {@code armorBody}. GeckoLib 4 hides the body
     * bone for head-slot armour by default, so restore the upstream two-bone rule.
     */
    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot slot) {
        super.applyBoneVisibilityBySlot(slot);
        if (slot == EquipmentSlot.HEAD) {
            setBoneVisible(head, true);
            setBoneVisible(body, true);
        }
    }

    public static GeoRenderProvider provider() {
        return new GeoRenderProvider() {
            private MagicCloakRenderer renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(
                    T entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<T> original) {
                if (renderer == null) renderer = new MagicCloakRenderer();
                return renderer;
            }
        };
    }
}

package io.github.artynova.mediaworks.item;

import at.petrak.hexcasting.common.items.magic.ItemPackagedHex;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

/** Component-backed IotaHolder/HexHolder packaged hex worn as a head-slot dyeable cloak. */
public class MagicCloakItem extends ItemPackagedHex implements Equipable, GeoItem {
    public static final int DEFAULT_COLOR = 0x5B4533;
    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
    public MagicCloakItem() {
        super(new Item.Properties().stacksTo(1).durability(432).rarity(Rarity.UNCOMMON));
    }
    @Override public boolean breakAfterDepletion() { return false; }
    @Override public int cooldown() { return 0; }
    @Override public boolean canDrawMediaFromInventory(ItemStack stack) { return true; }
    @Override public long getMedia(ItemStack stack) { return 0; }
    @Override public long getMaxMedia(ItemStack stack) { return 0; }
    @Override public void setMedia(ItemStack stack, long media) { }
    @Override public boolean canRecharge(ItemStack stack) { return false; }
    @Override public int getEnchantmentValue() { return 25; }
    public int getColor(ItemStack stack) { return DyedItemColor.getOrDefault(stack, DEFAULT_COLOR); }
    public static void initPackagedHexDiscovery() {
        io.github.artynova.mediaworks.api.logic.media.MediaDiscoveryHandler.addCustomPackagedHexDiscoverer(environment -> {
            ItemStack stack = environment.queryForMatchingStack(candidate -> candidate.is(MediaworksItems.MAGIC_CLOAK.get()));
            if (stack.isEmpty()) return null;
            return new io.github.artynova.mediaworks.api.logic.media.PackagedHexData(
                    at.petrak.hexcasting.xplat.IXplatAbstractions.INSTANCE.findHexHolder(stack),
                    at.petrak.hexcasting.xplat.IXplatAbstractions.INSTANCE.findMediaHolder(stack));
        });
    }
    @Override public net.minecraft.world.entity.EquipmentSlot getEquipmentSlot() {
        return net.minecraft.world.entity.EquipmentSlot.HEAD;
    }
    @Override public net.minecraft.world.InteractionResultHolder<ItemStack> use(
            net.minecraft.world.level.Level level,
            net.minecraft.world.entity.player.Player player,
            net.minecraft.world.InteractionHand hand) {
        return swapWithEquipmentSlot(this, level, player, hand);
    }
    @Override public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "cloak", state -> PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(io.github.artynova.mediaworks.client.armor.MagicCloakRenderer.provider());
    }
}

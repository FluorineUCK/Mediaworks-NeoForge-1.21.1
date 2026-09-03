package io.github.artynova.mediaworks.util;

import at.petrak.hexcasting.api.addldata.ADHexHolder;
import at.petrak.hexcasting.api.addldata.ADMediaHolder;
import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.env.CircleCastEnv;
import at.petrak.hexcasting.api.casting.eval.env.PackagedItemCastEnv;
import at.petrak.hexcasting.api.utils.MediaHelper;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.github.artynova.mediaworks.api.logic.media.PackagedHexData;
import io.github.artynova.mediaworks.logic.projection.MediaworksPlayerCastEnv;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MediaUtils {
    private MediaUtils() {}

    public static List<ADMediaHolder> collectMediaHolders(List<ItemStack> stacks) {
        return stacks.stream().filter(MediaHelper::isMediaItem)
                .map(IXplatAbstractions.INSTANCE::findMediaHolder).filter(Objects::nonNull)
                .sorted((a, b) -> Integer.compare(b.getConsumptionPriority(), a.getConsumptionPriority())).toList();
    }

    public static long getTotalMedia(List<ADMediaHolder> holders) {
        long total = 0;
        for (ADMediaHolder holder : holders) total = Math.addExact(total, holder.withdrawMedia(-1, true));
        return total;
    }

    /** Returns drawable media without counting overcast health or mutating any holder. */
    public static long getAvailableContextMedia(CastingEnvironment environment) {
        if (environment instanceof CircleCastEnv circle) {
            BlockEntityAbstractImpetus impetus = circle.getImpetus();
            return impetus == null ? 0 : Math.max(0, impetus.getMedia());
        }

        if (environment instanceof MediaworksPlayerCastEnv forcedEnvironment) {
            return getTotalMedia(collectPackagedFriendlyMediaHolders(
                    forcedEnvironment, forcedEnvironment.forcedStack()));
        }

        if (environment instanceof PackagedItemCastEnv) {
            ServerPlayer caster = environment.getCaster();
            if (caster == null) return 0;
            return getTotalMedia(collectPackagedFriendlyMediaHolders(
                    environment, caster.getItemInHand(environment.getCastingHand())));
        }

        ServerPlayer caster = environment.getCaster();
        return caster == null ? 0 : getPlayerMedia(caster);
    }

    /** Mirrors Hex Casting's packaged-item media rules while measuring holders in simulation mode. */
    public static List<ADMediaHolder> collectPackagedFriendlyMediaHolders(
            CastingEnvironment environment, ItemStack castingStack) {
        List<ADMediaHolder> holders = new ArrayList<>();
        ADHexHolder hexHolder = IXplatAbstractions.INSTANCE.findHexHolder(castingStack);
        if (hexHolder == null) return holders;

        ServerPlayer caster = environment.getCaster();
        if (hexHolder.canDrawMediaFromInventory() && caster != null) {
            holders.addAll(MediaHelper.scanPlayerForMediaStuff(caster));
        }

        PackagedHexData data = new PackagedHexData(
                hexHolder, IXplatAbstractions.INSTANCE.findMediaHolder(castingStack));
        ADMediaHolder packagedHolder = data.mediaHolder();
        if (packagedHolder == null) return holders;

        for (ADMediaHolder holder : holders) {
            if (holder == packagedHolder) return holders;
        }
        holders.add(packagedHolder);
        return holders;
    }

    public static long getEntityMedia(Entity entity) {
        if (entity instanceof ItemEntity item) return getItemStackMedia(item.getItem());
        if (entity instanceof ServerPlayer player) return getPlayerMedia(player);
        return 0;
    }

    public static long getItemStackMedia(ItemStack stack) {
        ADMediaHolder holder = IXplatAbstractions.INSTANCE.findMediaHolder(stack);
        return holder == null ? 0 : holder.withdrawMedia(-1, true);
    }

    public static long getPlayerMedia(ServerPlayer player) {
        return getTotalMedia(MediaHelper.scanPlayerForMediaStuff(player));
    }

    public static long getPosMedia(Vec3 vector, ServerLevel level) {
        BlockEntity blockEntity = level.getBlockEntity(BlockPos.containing(vector));
        if (blockEntity instanceof BlockEntityAbstractImpetus impetus) return impetus.getMedia();
        if (!(blockEntity instanceof Container container)) return 0;
        List<ItemStack> stacks = new ArrayList<>(container.getContainerSize());
        for (int i = 0; i < container.getContainerSize(); i++) stacks.add(container.getItem(i));
        return getTotalMedia(collectMediaHolders(stacks));
    }
}

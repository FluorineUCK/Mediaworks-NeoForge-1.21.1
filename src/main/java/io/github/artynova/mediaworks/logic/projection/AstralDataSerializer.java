package io.github.artynova.mediaworks.logic.projection;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class AstralDataSerializer {
    public static final String ASTRAL_POSITION_TAG = "astral_pos";
    public static final String ASTRAL_IOTA_TAG = "astral_iota";
    public static final String ASTRAL_ORIGIN_TAG = "astral_origin";

    private AstralDataSerializer() {
    }

    public static void putPlayerAstralPosition(CompoundTag tag, @Nullable AstralPosition position) {
        if (position == null) tag.remove(ASTRAL_POSITION_TAG);
        else tag.put(ASTRAL_POSITION_TAG, AstralPosition.serialize(position));
    }

    public static @Nullable AstralPosition getPlayerAstralPosition(CompoundTag tag) {
        return tag.contains(ASTRAL_POSITION_TAG, Tag.TAG_COMPOUND)
                ? AstralPosition.deserialize(tag.getCompound(ASTRAL_POSITION_TAG)) : null;
    }

    public static void putPlayerAstralIota(CompoundTag tag, @Nullable Iota iota) {
        if (iota == null) tag.remove(ASTRAL_IOTA_TAG);
        else tag.put(ASTRAL_IOTA_TAG, IotaType.TYPED_CODEC.encodeStart(NbtOps.INSTANCE, iota).getOrThrow());
    }

    public static @Nullable Iota getPlayerAstralIota(CompoundTag tag) {
        if (!tag.contains(ASTRAL_IOTA_TAG)) return null;
        return IotaType.TYPED_CODEC.parse(NbtOps.INSTANCE, tag.get(ASTRAL_IOTA_TAG)).result().orElse(null);
    }

    public static void putPlayerAstralOrigin(CompoundTag tag, @Nullable Vec3 origin) {
        if (origin == null) tag.remove(ASTRAL_ORIGIN_TAG);
        else tag.put(ASTRAL_ORIGIN_TAG, io.github.artynova.mediaworks.util.NbtUtils.serializeVec3d(origin));
    }

    public static @Nullable Vec3 getPlayerAstralOrigin(CompoundTag tag) {
        return tag.contains(ASTRAL_ORIGIN_TAG, Tag.TAG_LIST)
                ? io.github.artynova.mediaworks.util.NbtUtils.deserializeVec3d(tag.getList(ASTRAL_ORIGIN_TAG, Tag.TAG_DOUBLE)) : null;
    }
}

package io.github.artynova.mediaworks.logic.projection;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.phys.Vec3;

public record AstralPosition(Vec3 coordinates, float yaw, float pitch) {
    public static final String COORDS_TAG = "coords";
    public static final String YAW_TAG = "yaw";
    public static final String PITCH_TAG = "pitch";

    public static CompoundTag serialize(AstralPosition position) {
        CompoundTag tag = new CompoundTag();
        tag.put(COORDS_TAG, io.github.artynova.mediaworks.util.NbtUtils.serializeVec3d(position.coordinates));
        tag.putFloat(YAW_TAG, position.yaw);
        tag.putFloat(PITCH_TAG, position.pitch);
        return tag;
    }

    public static AstralPosition deserialize(CompoundTag tag) {
        return new AstralPosition(
                io.github.artynova.mediaworks.util.NbtUtils.deserializeVec3d(tag.getList(COORDS_TAG, Tag.TAG_DOUBLE)),
                tag.getFloat(YAW_TAG), tag.getFloat(PITCH_TAG));
    }
}

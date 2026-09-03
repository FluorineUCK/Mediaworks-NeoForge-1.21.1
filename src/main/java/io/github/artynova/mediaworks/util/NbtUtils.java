package io.github.artynova.mediaworks.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;

public final class NbtUtils {
    private NbtUtils() {
    }

    public static ListTag serializeVec3d(Vec3 vec) {
        ListTag list = new ListTag();
        list.add(DoubleTag.valueOf(vec.x));
        list.add(DoubleTag.valueOf(vec.y));
        list.add(DoubleTag.valueOf(vec.z));
        return list;
    }

    public static Vec3 deserializeVec3d(ListTag list) {
        if (list.size() < 3) return Vec3.ZERO;
        return new Vec3(list.getDouble(0), list.getDouble(1), list.getDouble(2));
    }

    public static ListTag serializeBlockPos(BlockPos pos) {
        ListTag list = new ListTag();
        list.add(IntTag.valueOf(pos.getX()));
        list.add(IntTag.valueOf(pos.getY()));
        list.add(IntTag.valueOf(pos.getZ()));
        return list;
    }

    public static BlockPos deserializeBlockPos(ListTag list) {
        if (list.size() < 3) return BlockPos.ZERO;
        return new BlockPos(list.getInt(0), list.getInt(1), list.getInt(2));
    }
}

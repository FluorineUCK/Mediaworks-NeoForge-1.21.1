package io.github.artynova.mediaworks.logic.macula;

import io.github.artynova.mediaworks.api.logic.macula.Visage;
import io.github.artynova.mediaworks.api.logic.macula.VisageType;
import io.github.artynova.mediaworks.api.registry.MediaworksRegistries;
import io.github.artynova.mediaworks.util.NbtUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

public final class VisageSerializer {
    public static final String VISAGE_TYPE_TAG = "type";
    public static final String VISAGE_DATA_TAG = "data";
    public static final String INSTANCE_TAG = "instance";
    public static final String ORIGIN_TAG = "origin";
    public static final String START_TIME_TAG = "start_time";
    public static final String END_TIME_TAG = "end_time";

    private VisageSerializer() {}

    public static VisageType<?> parseTypeFromTag(CompoundTag tag) {
        if (!tag.contains(VISAGE_TYPE_TAG, Tag.TAG_STRING)) return null;
        ResourceLocation id = ResourceLocation.tryParse(tag.getString(VISAGE_TYPE_TAG));
        return id == null ? null : MediaworksRegistries.getVisageType(id);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Visage> CompoundTag serializeVisage(T visage) {
        VisageType<T> type = (VisageType<T>) visage.getType();
        ResourceLocation id = MediaworksRegistries.getVisageTypeId(type);
        if (id == null) throw new IllegalArgumentException("Unregistered visage type " + type);
        CompoundTag tag = new CompoundTag();
        tag.putString(VISAGE_TYPE_TAG, id.toString());
        tag.put(VISAGE_DATA_TAG, type.serializeData(visage));
        return tag;
    }

    public static Visage deserializeVisage(CompoundTag tag) {
        VisageType<?> type = parseTypeFromTag(tag);
        if (type == null || !tag.contains(VISAGE_DATA_TAG, Tag.TAG_COMPOUND)) return Visage.makeGarbageVisage();
        Visage visage = type.deserializeData(tag.getCompound(VISAGE_DATA_TAG));
        return visage == null ? Visage.makeGarbageVisage() : visage;
    }

    public static CompoundTag serializeEntry(VisageEntry entry) {
        CompoundTag tag = new CompoundTag();
        tag.put(INSTANCE_TAG, serializeVisage(entry.getVisage()));
        tag.put(ORIGIN_TAG, NbtUtils.serializeBlockPos(entry.getOrigin()));
        tag.putLong(START_TIME_TAG, entry.getStartTime());
        tag.putLong(END_TIME_TAG, entry.getEndTime());
        return tag;
    }

    public static VisageEntry deserializeEntry(CompoundTag tag) {
        Visage visage = deserializeVisage(tag.getCompound(INSTANCE_TAG));
        BlockPos origin = NbtUtils.deserializeBlockPos(tag.getList(ORIGIN_TAG, Tag.TAG_INT));
        return new VisageEntry(visage, origin, tag.getLong(START_TIME_TAG), tag.getLong(END_TIME_TAG));
    }
}

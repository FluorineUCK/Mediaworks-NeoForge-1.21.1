package io.github.artynova.mediaworks.logic.macula;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Comparator;

public final class MaculaContent extends ArrayList<VisageEntry> {
    public static final Comparator<VisageEntry> DEPTH_COMPARATOR = Comparator.comparingInt(e -> e.getOrigin().getZ());

    public static ListTag serialize(MaculaContent content, long currentTime) {
        ListTag list = new ListTag();
        content.stream().filter(entry -> !entry.hasTimedOut(currentTime))
                .map(VisageSerializer::serializeEntry).forEach(list::add);
        return list;
    }

    public static MaculaContent deserialize(ListTag list, long currentTime) {
        MaculaContent content = new MaculaContent();
        for (Tag tag : list) {
            if (!(tag instanceof CompoundTag compound)) continue;
            VisageEntry entry = VisageSerializer.deserializeEntry(compound);
            if (!entry.hasTimedOut(currentTime)) content.add(entry);
        }
        return content;
    }

    public void sortByDepth() { sort(DEPTH_COMPARATOR); }
}

package io.github.artynova.mediaworks.logic.macula;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public final class MaculaSerializer {
    public static final String CONTENT_TAG = "content";
    private MaculaSerializer() {}

    public static MaculaContent getContent(CompoundTag tag, long gameTime) {
        return MaculaContent.deserialize(tag.getList(CONTENT_TAG, Tag.TAG_COMPOUND), gameTime);
    }

    public static void putContent(CompoundTag tag, MaculaContent content, long gameTime) {
        tag.put(CONTENT_TAG, MaculaContent.serialize(content, gameTime));
    }
}

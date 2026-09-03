package io.github.artynova.mediaworks.logic.macula;

import io.github.artynova.mediaworks.api.logic.PersistentDataContainer;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public final class Macula implements PersistentDataContainer {
    public static final int MAX_VISAGES = 32;
    public static final long MAX_FLEETING_VISAGE_TICKS = 24000;
    private final ServerPlayer owner;
    private MaculaContent content = new MaculaContent();
    private int width = 480;
    private int height = 270;

    public Macula(ServerPlayer owner) { this.owner = owner; }
    public MaculaContent getContent() { return content; }
    public void setContent(MaculaContent content) { this.content = content; }
    public boolean isFull() { return content.size() >= MAX_VISAGES; }
    public boolean checkFullness() { trim(); return isFull(); }
    public void add(VisageEntry entry) { if (!entry.hasTimedOut(owner.level().getGameTime())) content.add(entry); }
    public void trim() { content.removeIf(entry -> entry.hasTimedOut(owner.level().getGameTime())); }
    public void clear() { content.clear(); }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = Math.max(1, width); }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = Math.max(1, height); }

    @Override public void readFromNbt(CompoundTag tag, HolderLookup.Provider registries) {
        content = MaculaSerializer.getContent(tag, owner.level().getGameTime());
    }
    @Override public void writeToNbt(CompoundTag tag, HolderLookup.Provider registries) {
        MaculaSerializer.putContent(tag, content, owner.level().getGameTime());
    }
}

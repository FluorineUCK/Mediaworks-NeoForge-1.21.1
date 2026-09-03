package io.github.artynova.mediaworks.logic.macula;

import at.petrak.hexcasting.api.casting.iota.Iota;
import io.github.artynova.mediaworks.api.logic.macula.Visage;
import io.github.artynova.mediaworks.api.logic.macula.VisageType;
import io.github.artynova.mediaworks.interop.moreiotas.MoreIotasInterop;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;

import java.util.Objects;

public final class TextVisage extends Visage {
    public static final int UNLIMITED_SIZE = -1;
    public static final String TEXT_TAG = "text";
    public static final String WIDTH_TAG = "width";
    public static final String HEIGHT_TAG = "height";
    public static final VisageType<TextVisage> TYPE = new VisageType<>() {
        @Override public TextVisage deserializeData(CompoundTag tag) {
            Component text = ComponentSerialization.CODEC.parse(net.minecraft.nbt.NbtOps.INSTANCE, tag.get(TEXT_TAG)).result().orElse(Component.empty());
            int width = tag.getInt(WIDTH_TAG), height = tag.getInt(HEIGHT_TAG);
            return width == UNLIMITED_SIZE || height == UNLIMITED_SIZE ? new TextVisage(text) : new TextVisage(text, width, height);
        }
        @Override public CompoundTag serializeData(TextVisage visage) {
            CompoundTag tag = new CompoundTag();
            tag.put(TEXT_TAG, ComponentSerialization.CODEC.encodeStart(net.minecraft.nbt.NbtOps.INSTANCE, visage.text).getOrThrow());
            tag.putInt(WIDTH_TAG, visage.width);
            tag.putInt(HEIGHT_TAG, visage.height);
            return tag;
        }
    };

    private final Component text;
    private final int width;
    private final int height;

    public TextVisage(Component text, int width, int height) {
        super(TYPE);
        if (width < 0 || height < 0) throw new IllegalArgumentException("Bounded visage dimensions must be non-negative");
        this.text = text; this.width = width; this.height = height;
    }
    public TextVisage(Component text) { super(TYPE); this.text = text; this.width = UNLIMITED_SIZE; this.height = UNLIMITED_SIZE; }
    public static Component captureText(Iota iota) { return MoreIotasInterop.captureText(iota); }
    public Component getText() { return text; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    @Override public net.minecraft.network.chat.MutableComponent displayOnStack() {
        return width == UNLIMITED_SIZE ? Component.translatable("mediaworks.visage.text.unbounded")
                : Component.translatable("mediaworks.visage.text.bounded", width, height);
    }
    @Override public boolean equals(Object obj) { return obj instanceof TextVisage other && width == other.width && height == other.height && text.equals(other.text); }
    @Override public int hashCode() { return Objects.hash(text, width, height); }
}

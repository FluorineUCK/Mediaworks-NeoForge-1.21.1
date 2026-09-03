package io.github.artynova.mediaworks.api.logic.macula;

import at.petrak.hexcasting.api.casting.iota.GarbageIota;
import io.github.artynova.mediaworks.logic.macula.TextVisage;
import net.minecraft.network.chat.MutableComponent;

public abstract class Visage {
    private final VisageType<?> type;

    protected Visage(VisageType<?> type) {
        this.type = type;
    }

    public static Visage makeGarbageVisage() {
        return new TextVisage(TextVisage.captureText(new GarbageIota()));
    }

    public VisageType<?> getType() { return type; }
    public abstract MutableComponent displayOnStack();
}

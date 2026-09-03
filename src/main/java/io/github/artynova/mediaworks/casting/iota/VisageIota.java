package io.github.artynova.mediaworks.casting.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import com.mojang.serialization.MapCodec;
import io.github.artynova.mediaworks.api.logic.macula.Visage;
import io.github.artynova.mediaworks.logic.macula.VisageSerializer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public final class VisageIota extends Iota {
    public static final IotaType<VisageIota> TYPE = new IotaType<>() {
        private final MapCodec<VisageIota> codec = CompoundTag.CODEC.xmap(
                tag -> new VisageIota(VisageSerializer.deserializeVisage(tag)),
                iota -> VisageSerializer.serializeVisage(iota.visage)).fieldOf("visage");
        private final StreamCodec<RegistryFriendlyByteBuf, VisageIota> streamCodec =
                ByteBufCodecs.fromCodecWithRegistries(CompoundTag.CODEC).map(
                        tag -> new VisageIota(VisageSerializer.deserializeVisage(tag)),
                        iota -> VisageSerializer.serializeVisage(iota.visage));
        @Override public MapCodec<VisageIota> codec() { return codec; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, VisageIota> streamCodec() { return streamCodec; }
        @Override public int color() { return 0xFF_FFAA00; }
    };

    private final Visage visage;
    public VisageIota(Visage visage) { super(() -> TYPE); this.visage = visage; }
    public Visage getVisage() { return visage; }
    @Override public boolean isTruthy() { return true; }
    @Override protected boolean toleratesOther(Iota that) { return that instanceof VisageIota other && visage.equals(other.visage); }
    @Override public Component display() { return visage.displayOnStack().withStyle(ChatFormatting.GOLD); }
    @Override public int hashCode() { return visage.hashCode(); }
}

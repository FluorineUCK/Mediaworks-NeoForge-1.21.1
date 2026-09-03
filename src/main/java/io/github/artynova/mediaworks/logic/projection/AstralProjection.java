package io.github.artynova.mediaworks.logic.projection;

import at.petrak.hexcasting.api.casting.iota.Iota;
import io.github.artynova.mediaworks.api.logic.PersistentDataContainer;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class AstralProjection implements PersistentDataContainer {
    private final ServerPlayer owner;
    private @Nullable AstralPosition position;
    private @Nullable Vec3 origin;
    private @Nullable Iota iota;
    private int cooldown;

    public AstralProjection(ServerPlayer owner) {
        this.owner = owner;
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registries) {
        position = AstralDataSerializer.getPlayerAstralPosition(tag);
        origin = position == null ? null : AstralDataSerializer.getPlayerAstralOrigin(tag);
        iota = position == null ? null : AstralDataSerializer.getPlayerAstralIota(tag);
        cooldown = 0;
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registries) {
        AstralDataSerializer.putPlayerAstralPosition(tag, position);
        AstralDataSerializer.putPlayerAstralOrigin(tag, origin);
        AstralDataSerializer.putPlayerAstralIota(tag, iota);
    }

    public boolean isActive() { return position != null; }
    public void end() { position = null; origin = null; iota = null; cooldown = 0; }
    public @Nullable AstralPosition getPosition() { return position; }
    public void setPosition(@Nullable AstralPosition position) { this.position = position; }
    public @Nullable Vec3 getOrigin() { return origin; }
    public void setOrigin(@Nullable Vec3 origin) { this.origin = origin; }
    public @Nullable Iota getIota() { return iota; }
    public void setIota(@Nullable Iota iota) { this.iota = iota; }
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = Math.max(0, cooldown); }
    public void tickCooldown() { cooldown = Math.max(0, cooldown - 1); }
    public ServerPlayer getOwner() { return owner; }
}

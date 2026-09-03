package io.github.artynova.mediaworks;

import io.github.artynova.mediaworks.logic.macula.Macula;
import io.github.artynova.mediaworks.logic.projection.AstralProjection;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

/** NeoForge attachments replace the old Forge capabilities/Cardinal Components. */
public final class MediaworksAttachments {
    private static final DeferredRegister<AttachmentType<?>> TYPES =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Mediaworks.MOD_ID);

    public static final Supplier<AttachmentType<AstralProjection>> ASTRAL_PROJECTION = TYPES.register(
        "astral_projection",
        () -> AttachmentType.builder(holder -> new AstralProjection((ServerPlayer) holder))
            .serialize(serializer(holder -> new AstralProjection((ServerPlayer) holder)))
            .copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Macula>> MACULA = TYPES.register(
        "macula",
        () -> AttachmentType.builder(holder -> new Macula((ServerPlayer) holder))
            .serialize(serializer(holder -> new Macula((ServerPlayer) holder)))
            .copyOnDeath().build()
    );

    private MediaworksAttachments() {}

    private static <T extends io.github.artynova.mediaworks.api.logic.PersistentDataContainer>
    IAttachmentSerializer<CompoundTag, T> serializer(java.util.function.Function<IAttachmentHolder, T> factory) {
        return new IAttachmentSerializer<>() {
            @Override public T read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider registries) {
                T value = factory.apply(holder);
                value.readFromNbt(tag, registries);
                return value;
            }

            @Override public CompoundTag write(T value, HolderLookup.Provider registries) {
                CompoundTag tag = new CompoundTag();
                value.writeToNbt(tag, registries);
                return tag;
            }
        };
    }

    public static void register(IEventBus modBus) {
        TYPES.register(modBus);
    }

    public static AstralProjection projection(ServerPlayer player) {
        return player.getData(ASTRAL_PROJECTION);
    }

    public static Macula macula(ServerPlayer player) {
        return player.getData(MACULA);
    }
}

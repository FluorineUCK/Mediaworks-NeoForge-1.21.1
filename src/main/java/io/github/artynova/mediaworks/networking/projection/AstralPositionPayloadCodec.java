package io.github.artynova.mediaworks.networking.projection;

import io.github.artynova.mediaworks.logic.projection.AstralPosition;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

final class AstralPositionPayloadCodec {
    private AstralPositionPayloadCodec() {}

    static void write(RegistryFriendlyByteBuf buffer, AstralPosition value) {
        buffer.writeDouble(value.coordinates().x);
        buffer.writeDouble(value.coordinates().y);
        buffer.writeDouble(value.coordinates().z);
        buffer.writeFloat(value.yaw());
        buffer.writeFloat(value.pitch());
    }

    static AstralPosition read(RegistryFriendlyByteBuf buffer) {
        return new AstralPosition(
            new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
            buffer.readFloat(),
            buffer.readFloat()
        );
    }
}

package io.github.artynova.mediaworks.mixin.client;

import io.github.artynova.mediaworks.client.projection.AstralProjectionClient;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Redirect only the final mouse-turn call while astral projection owns the camera. */
@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Redirect(
            method = "turnPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
            )
    )
    private void mediaworks$turnAstralCamera(LocalPlayer player, double x, double y) {
        if (AstralProjectionClient.isDissociated()) {
            AstralProjectionClient.turnAstralCamera(x, y);
        } else {
            player.turn(x, y);
        }
    }
}

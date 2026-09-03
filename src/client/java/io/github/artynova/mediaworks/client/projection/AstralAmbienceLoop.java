package io.github.artynova.mediaworks.client.projection;

import io.github.artynova.mediaworks.sound.MediaworksSounds;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

/** Relative looping ambience that lives for exactly one astral-projection session. */
public final class AstralAmbienceLoop extends AbstractTickableSoundInstance {
    private final LocalPlayer player;

    public AstralAmbienceLoop(LocalPlayer player) {
        super(MediaworksSounds.PROJECTION_AMBIANCE.get(), SoundSource.AMBIENT, RandomSource.create());
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 1.0f;
        this.relative = true;
        this.attenuation = Attenuation.NONE;
    }

    @Override
    public void tick() {
        if (player.isRemoved() || !AstralProjectionClient.isDissociated()) stop();
    }
}

package io.github.artynova.mediaworks.api.logic.media;

import at.petrak.hexcasting.api.addldata.ADHexHolder;
import at.petrak.hexcasting.api.addldata.ADMediaHolder;
import org.jetbrains.annotations.Nullable;

public record PackagedHexData(@Nullable ADHexHolder hexHolder, @Nullable ADMediaHolder mediaHolder) {
}

package io.github.artynova.mediaworks.interop.moreiotas;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.network.chat.Component;

public final class MoreIotasInterop {
    public static final String MOD_ID = "moreiotas";
    /** moreiotas StringIota formatting is captured and color codes are replaced below. */
    public static final String FORMAT_CAPTURE_CONTRACT = "format replace capture";
    private MoreIotasInterop() {}

    public static boolean isPresent() { return IXplatAbstractions.INSTANCE.isModPresent(MOD_ID); }

    public static Component captureText(Iota iota) {
        if (isPresent() && iota instanceof ram.talia.moreiotas.api.casting.iota.StringIota stringIota) {
            return Component.literal(stringIota.getString().replaceAll("&([1-9a-fk-orA-FK-OR])", "§$1"));
        }
        return iota.display();
    }

    public static boolean isStringIota(Iota iota) {
        return isPresent() && iota instanceof ram.talia.moreiotas.api.casting.iota.StringIota;
    }
}

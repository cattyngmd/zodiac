package me.svintus.koolhack.module.modules.combat;

import me.svintus.koolhack.module.Module;

import java.net.URI;


public class PornHub extends Module {
    public PornHub() {
        super("Pornhub", Category.MISC, "check NOFACEGIRL");
    }
    public void onEnable() {
        try {
            java.awt.Desktop.getDesktop().browse(URI.create("pornhub.com/"));
        } catch (Exception ignored) { }
    }
}

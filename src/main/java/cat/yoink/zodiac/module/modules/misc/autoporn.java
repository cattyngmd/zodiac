package me.cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;

import java.net.URI;

public class AutoPorn extends Module {

    public AutoPorn() {

        super("AutoPorn", "Opens pornhub if you want to jerk off.", Category.MISC, true);

    }
    public void onEnable() {
        try {
            java.awt.Desktop.getDesktop().browse(URI.create("pornhub.com/"));
        } catch (Exception ignored) { }
    }
}

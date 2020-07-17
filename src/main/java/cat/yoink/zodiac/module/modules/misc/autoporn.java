package me.cat.yoink.zodiac.module.modules.misc;

import me.cat.yoink.zodiac.module.manager.Module;

import java.net.URI;

public class ChatSuffix extends Module {

    public ChatSuffix() {

        super("AutoPorn", "Opens pornhub if you want to jerk off.", Category.MISC, true);

    }
    public void onEnable() {
        try {
            java.awt.Desktop.getDesktop().browse(URI.create("pornhub.com/"));
        } catch (Exception ignored) { }
    }
}

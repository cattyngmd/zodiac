package cat.yoink.zodiac.module.modules.render;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Module click :O", Category.RENDER, true);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Client.getInstance().clickGUI);
    }
}

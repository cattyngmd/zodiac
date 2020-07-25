package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically sprints.", Category.MISC, true);
    }

    public void onUpdate() {
        if (mc.player.moveForward > 0 && !mc.player.isSprinting()) {
            mc.player.setSprinting(true);
        }
    }
}

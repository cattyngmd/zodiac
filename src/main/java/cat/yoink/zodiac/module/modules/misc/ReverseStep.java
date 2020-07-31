package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.module.manager.Module;

public class ReverseStep extends Module {
    public ReverseStep() {
        super("ReverseStep", "Makes you fall down into holes faster", Category.MISC, true);
    }

    public void onUpdate() {
        if (mc.player.onGround)
            --mc.player.motionY;
     if (mc.player.isInWater() || mc.player.isInLava())
            return;
    }
}

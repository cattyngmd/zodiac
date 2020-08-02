package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;

public class ReverseStep extends Module
{
    public ReverseStep()
    {
        super("ReverseStep", "Makes you fall down into holes faster", Category.MISC, true);
    }

//    public void onUpdate() {
//        if (mc.player.onGround)
//            --mc.player.motionY;
//     if (mc.player.isInWater() || mc.player.isInLava())
//            return;
//
//
//


    @Override
    public void onUpdate()
    {
        if (mc.player.onGround && !mc.player.isInWater() && !mc.player.isInLava())
        {
            mc.player.motionY--;
        }
    }
}

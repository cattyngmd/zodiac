package me.cat.yoink.zodiac.module.modules.misc;

import me.z.aurora.module.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.MOVEMENT, "Automatically sprint");
    }

    public void onUpdate(){
        if(mc.player.moveForward > 0 && !mc.player.isSprinting()){
            mc.player.setSprinting(true);
        }
    }
}

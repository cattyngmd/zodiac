package me.cat.yoink.zodiac.module.modules.misc;

import me.cat.yoink.zodiac.module.manager.module

public class Sprint extends Module {

 public Sprint() {

  super("Sprint", "Automatically sprints.", Category.MISC, true);
  }
    public void onUpdate(){
        if(mc.player.moveForward > 0 && !mc.player.isSprinting()){
            mc.player.setSprinting(true);
        }
    }
}

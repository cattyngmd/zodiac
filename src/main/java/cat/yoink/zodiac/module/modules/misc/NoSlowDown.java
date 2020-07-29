package cat.yoink.zodiac.module.modules.misc

import cat.yoink.zodiac.module.manager.Module
import cat.yoink.zodiac.Client
import cat.yoink.zodiac.event.handler.EventHandler
import me.zero.alpine.listener.Listener

public class NoSlowDown extends module {
 public NoSlowDown() {
   super("NoSlowDown", "doesnt slow when doin shit.", Category.MISC, true);

    }
    

@EventHandler
    private Listener<InputUpdateEvent> eventListener = new Listener<>(event -> {
        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    });

    public void onEnable(){
        Client.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        Client.EVENT_BUS.unsubscribe(this);
    }
}

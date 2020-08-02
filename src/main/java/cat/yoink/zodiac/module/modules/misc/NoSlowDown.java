package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.InputUpdateEvent;

public class NoSlowDown extends Module
{

    @EventHandler
    private final Listener<InputUpdateEvent> eventListener = new Listener<>(event ->
    {
        if (mc.player.isHandActive() && !mc.player.isRiding())
        {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    });

    // mixin might be needed

    public NoSlowDown()
    {

        super("NoSlowDown", "doesnt slow when doin shit.", Category.MISC, true);

    }

    public void onEnable()
    {
        Client.EVENT_BUS.subscribe(this);
    }

    public void onDisable()
    {
        Client.EVENT_BUS.unsubscribe(this);
    }
}

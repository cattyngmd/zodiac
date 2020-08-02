package cat.yoink.zodiac.module.modules.combat;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

/**
 * by svintus
 */
public class MultiTask extends Module
{

    @EventHandler
    public Listener<InputEvent.MouseInputEvent> mouseInputEventListener = new Listener<>(event ->
    {
        if (Mouse.getEventButtonState() && mc.player != null && mc.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.ENTITY) && mc.player.isHandActive() && (mc.gameSettings.keyBindAttack.isPressed() || Mouse.getEventButton() == mc.gameSettings.keyBindAttack.getKeyCode()))
        {
            mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    });


    /*
    not sure what this is supposed to do but ok
     */

    public MultiTask()
    {
        super("Multitask", "completes multiple tasks at once", Category.COMBAT, true);
    }

    @Override
    public void addSubscription()
    {
        Client.EVENT_BUS.subscribe(this);
    }

    @Override
    public void removeSubscription()
    {
        Client.EVENT_BUS.unsubscribe(this);
    }
}

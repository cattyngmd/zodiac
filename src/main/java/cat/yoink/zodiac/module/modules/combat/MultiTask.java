

package me.cat.yoink.zodiac.module.modules.combat;

import me.cat.yoink.zodiac.module.manager.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * by svintus
 */
public class Multitask extends Module {
    public Multitask() {
        super ("Multitask", "completes multiple tasks at once", Category.COMBAT, true);
    }
            
    @SubscribeEvent
    public void onMouseInput(final InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButtonState() && mc.player != null && mc.objectMouseOver.typeOfHit.equals((Object)RayTraceResult.Type.ENTITY) && mc.player.isHandActive() && (mc.gameSettings.keyBindAttack.isPressed() || Mouse.getEventButton() == mc.gameSettings.keyBindAttack.getKeyCode())) {
            mc.playerController.attackEntity((EntityPlayer) mc.player, mc.objectMouseOver.entityHit);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}

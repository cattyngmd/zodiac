package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.event.events.MotionEvent;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.setting.Setting;
import net.minecraft.init.MobEffects;

import java.util.Objects;

public class Strafe extends Module
{
    Setting ground;

    public Strafe()
    {
        super("Strafe", "franzj presents!", Category.MISC, true);

        ground = new Setting("Ground", this, true);

        addSetting(ground);

    }


    // ok
    public void onMotion(MotionEvent event)
    {
        if (mc.player != null)
        {
            if (!mc.player.isSneaking() && !mc.player.isOnLadder() && !mc.player.isInLava() && !mc.player.isInWater() && !mc.player.capabilities.isFlying)
            {
                if (this.ground.getBoolValue() || !mc.player.onGround)
                {
                    float playerSpeed = 0.2873F;
                    float moveForward = mc.player.movementInput.moveForward;
                    float moveStrafe = mc.player.movementInput.moveStrafe;
                    float rotationYaw = mc.player.rotationYaw;
                    if (mc.player.isPotionActive(MobEffects.SPEED))
                    {
                        int amplifier = Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
                        playerSpeed *= 1.0F + 0.2F * (float) (amplifier + 1);
                    }

                    if (moveForward == 0.0F && moveStrafe == 0.0F)
                    {
                        event.setX(0.0D);
                        event.setZ(0.0D);
                    }
                    else
                    {
                        if (moveForward != 0.0F)
                        {
                            if (moveStrafe > 0.0F)
                            {
                                rotationYaw += (float) (moveForward > 0.0F ? -45 : 45);
                            }
                            else if (moveStrafe < 0.0F)
                            {
                                rotationYaw += (float) (moveForward > 0.0F ? 45 : -45);
                            }

                            moveStrafe = 0.0F;
                            if (moveForward > 0.0F)
                            {
                                moveForward = 1.0F;
                            }
                            else if (moveForward < 0.0F)
                            {
                                moveForward = -1.0F;
                            }
                        }

                        double sin = Math.sin(Math.toRadians(rotationYaw + 90.0F));
                        double cos = Math.cos(Math.toRadians(rotationYaw + 90.0F));
                        event.setX((double) (moveForward * playerSpeed) * cos + (double) (moveStrafe * playerSpeed) * sin);
                        event.setZ((double) (moveForward * playerSpeed) * sin - (double) (moveStrafe * playerSpeed) * cos);
                    }

                }
            }
        }
    }
}


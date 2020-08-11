package cat.yoink.zodiac.mixin.mixins;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.event.events.CollisionEvent;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class MixinEntity
{

    @Redirect(method = "applyEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void addVelocity(Entity entity, double x, double y, double z)
    {
        CollisionEvent event = new CollisionEvent(entity, x, y, z);
        Client.EVENT_BUS.post(event);
        if (event.isCancelled()) return;

        entity.motionX += x;
        entity.motionY += y;
        entity.motionZ += z;

        entity.isAirBorne = true;
    }

}

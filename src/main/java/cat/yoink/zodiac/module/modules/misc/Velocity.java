package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.event.events.CollisionEvent;
import cat.yoink.zodiac.event.events.PacketEvent;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module
{
    @EventHandler
    private final Listener<CollisionEvent> collisionEventListener = new Listener<>(collisionEvent ->
    {

        if (collisionEvent.getEntity().equals(mc.player)) collisionEvent.cancel();

    });
    @EventHandler
    private final Listener<PacketEvent.Receive> packetEventListener = new Listener<>(event ->
    {
        if (event.getPacket() instanceof SPacketEntityVelocity)
        {
            SPacketEntityVelocity velocity = (SPacketEntityVelocity) event.getPacket();
            if (velocity.getEntityID() == mc.player.getEntityId())
            {
                event.cancel();
            }
        }
        else if (event.getPacket() instanceof SPacketExplosion)
        {
            event.cancel();
        }
    });

    public Velocity()
    {
        super("Velocity", "Prevents you from taking knockback", Category.MISC, true);
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

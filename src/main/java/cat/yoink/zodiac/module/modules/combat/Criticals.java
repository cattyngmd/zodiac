package cat.yoink.zodiac.module.modules.combat;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.event.events.PacketEvent;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class Criticals extends Module
{
    @EventHandler
    private final Listener<PacketEvent.Send> sendListener = new Listener<>(event ->
    {
        if (event.getPacket() instanceof CPacketUseEntity)
        {
            if (((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround)
            {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            }
        }
    });

    public Criticals()
    {
        super("Criticals", "Increases chance for a critical hit", Category.COMBAT, true);
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
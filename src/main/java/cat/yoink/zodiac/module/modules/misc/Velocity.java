package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.module.manager.Module;
import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.event.events.PacketEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "Prevents you from taking knockback", Catergory.MISC, true);
    }

    public void onEnable(){
        Client.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        Client.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener = new Listener<>(event -> {
        if(event.getPacket() instanceof SPacketEntityVelocity){
            if(((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
                event.cancel();
        }
        if(event.getPacket() instanceof SPacketExplosion)
            event.cancel();
    });
}

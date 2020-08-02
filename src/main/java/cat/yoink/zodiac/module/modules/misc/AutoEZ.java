package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.event.events.PacketEvent;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

//commented due to brain issues import cat.yoink.zodiac.event.Listener

/**
 * by svintus/floppa
 */
public class AutoEZ extends Module
{

    ConcurrentHashMap targetedPlayers = null; // idk
    @EventHandler
    private final Listener<PacketEvent.Send> sendPacketListener = new Listener<>(event ->
    {
        if (mc.player != null)
        {
            if (targetedPlayers == null)
            {
                targetedPlayers = new ConcurrentHashMap();
            }

            if (event.getPacket() instanceof CPacketUseEntity)
            {
                CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) event.getPacket();

                if (cPacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK))
                {

                    Entity targetEntity = cPacketUseEntity.getEntityFromWorld(mc.world);

                    if (targetEntity instanceof EntityPlayer)
                    {

                        addTargetedPlayer(targetEntity.getName());
                    }
                }
            }
        }
    });
    @EventHandler
    private final Listener<LivingDeathEvent> livingDeathEventListener = new Listener<>(event ->
    {
        if (mc.player != null)
        {
            if (targetedPlayers == null)
            {
                targetedPlayers = new ConcurrentHashMap();
            }

            EntityLivingBase entity = event.getEntityLiving();
            if (entity != null)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;
                    if (player.getHealth() <= 0.0F)
                    {
                        String name = player.getName();
                        if (shouldAnnounce(name))
                        {
                            doAnnounce(name);
                        }

                    }
                }
            }
        }
    });

    public AutoEZ()
    {
        super("AutoEZ", "sends a message in chat when you kill someone", Category.MISC, true);
    }

    public void onEnable()
    {
        targetedPlayers = new ConcurrentHashMap();
        Client.EVENT_BUS.subscribe(this);
    }

    public void onDisable()
    {
        targetedPlayers = null;
        Client.EVENT_BUS.unsubscribe(this);
    }

    public void onUpdate()
    {
        if (targetedPlayers == null)
        {
            targetedPlayers = new ConcurrentHashMap();
        }

        Iterator var1 = mc.world.getLoadedEntityList().iterator();

        while (var1.hasNext())
        {
            Entity entity = (Entity) var1.next();
            if (entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.getHealth() <= 0.0F)
                {
                    String name = player.getName();
                    if (shouldAnnounce(name))
                    {
                        doAnnounce(name);
                        break;
                    }
                }
            }
        }

        targetedPlayers.forEach((namex, timeout) ->
        {
            if ((int) timeout <= 0)
            {
                targetedPlayers.remove(namex);
            }
            else
            {
                targetedPlayers.put(namex, (int) timeout - 1);
            }

        });
    }

    private boolean shouldAnnounce(String name)
    {
        return targetedPlayers.containsKey(name);
    }

    private void doAnnounce(String name)
    {
        targetedPlayers.remove(name);

        String message = "you just got ezd by zodiac, gonn cry bout it?";

        String messageSanitized = message.replaceAll("ยง", "").replace("{name}", name);
        if (messageSanitized.length() > 255)
        {
            messageSanitized = messageSanitized.substring(0, 255);
        }

        mc.player.connection.sendPacket(new CPacketChatMessage(messageSanitized));
    }

    public void addTargetedPlayer(String name)
    {
        if (!Objects.equals(name, mc.player.getName()))
        {
            if (targetedPlayers == null)
            {
                targetedPlayers = new ConcurrentHashMap();
            }

            targetedPlayers.put(name, 20);
        }
    }
}
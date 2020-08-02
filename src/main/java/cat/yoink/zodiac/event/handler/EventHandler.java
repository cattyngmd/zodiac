package cat.yoink.zodiac.event.handler;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.command.manager.CommandManager;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.module.ModuleManager;
import cat.yoink.zodiac.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventHandler
{
    public static EventHandler INSTANCE;

    public EventHandler()
    {
        INSTANCE = this;
    }

    public void initialize()
    {
        Client.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Client.EVENT_BUS.post(event);

        if (Keyboard.getEventKeyState())
        {
            for (Module module : ModuleManager.getModules())
            {
                if (Keyboard.getEventKey() == module.getBind())
                {
                    module.toggle();
                }
            }
        }
    }

    @SubscribeEvent
    public void clientChatEvent(ClientChatEvent event)
    {
        if (event.getMessage().startsWith(CommandManager.getPrefix()))
        {
            try
            {
                event.setCanceled(true);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                CommandManager.onCommand(event.getMessage());
                return;
            }
            catch (Exception ignored)
            {
            }
        }
        Helper.onChatSend(event);
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        Helper.onUpdate();
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        Client.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onPlayerInteractLeftClickBlock(PlayerInteractEvent.LeftClickBlock event)
    {
        Client.EVENT_BUS.post(event);
    }

}

package cat.yoink.zodiac.util;

import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.event.ClientChatEvent;

public class Helper
{
    public static void onUpdate()
    {
        for (Module module : ModuleManager.getModules())
        {
            if (module.isEnabled()) module.onUpdate();
        }
    }


    public static void onChatSend(ClientChatEvent event)
    {
        if (!event.getMessage().startsWith("-") || event.getMessage().startsWith("/") || event.getMessage().startsWith(".") || event.getMessage().startsWith("#"))
        {

            Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
            for (Module module : ModuleManager.getModules())
            {
                if (module.isEnabled())
                {
                    module.onChatSend(event);
                }
            }
            Minecraft.getMinecraft().player.connection.sendPacket(new CPacketChatMessage(event.getMessage()));
            event.setCanceled(true);
        }
    }
}

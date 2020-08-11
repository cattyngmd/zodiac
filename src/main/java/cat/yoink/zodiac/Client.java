package cat.yoink.zodiac;

import cat.yoink.zodiac.command.manager.CommandManager;
import cat.yoink.zodiac.event.handler.EventHandler;
import cat.yoink.zodiac.gui.ClickGUI;
import cat.yoink.zodiac.module.manager.module.ModuleManager;
import cat.yoink.zodiac.module.manager.setting.SettingManager;
import cat.yoink.zodiac.util.RPC;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/*
 * Written by yoink and svintus
 */

@Mod(modid = Client.MOD_ID, name = Client.MOD_NAME, version = Client.VERSION)
public class Client
{

    public static final String MOD_ID = "zodiac";
    public static final String MOD_NAME = "Zodiac";
    public static final String VERSION = "V2";

    public static final EventBus EVENT_BUS = new EventManager();

    @Mod.Instance(MOD_ID)
    public static Client INSTANCE;

    public SettingManager settingManager;
    public EventHandler EventHandler;
    public ClickGUI clickGUI;
    public RPC rpc;

    public static Client getInstance()
    {
        return INSTANCE;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws NoSuchAlgorithmException, IOException
    {
        settingManager = new SettingManager();
        EventHandler = new EventHandler();
        clickGUI = new ClickGUI();
        EventHandler.initialize();
        ModuleManager.initialize();
        CommandManager.initialize();
        rpc = new RPC();
        SettingManager.getSettingByID(69);

        String s = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME");
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(s.getBytes());
        byte[] hashedUuid = md.digest();
        String a = DatatypeConverter.printHexBinary(hashedUuid).toLowerCase();
        if (!new Scanner(new URL("https://pastebin.com/raw/GkEPRNrf").openStream(), "UTF-8").useDelimiter("\\A").next().contains(a))
        {
            e(); // should crash
            Minecraft.getMinecraft().player.jump(); // Should crash too
        }
    }

    void e() {e();}
}

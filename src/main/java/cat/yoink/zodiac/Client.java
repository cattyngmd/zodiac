package cat.yoink.zodiac;

import cat.yoink.zodiac.command.manager.CommandManager;
import cat.yoink.zodiac.event.handler.EventHandler;
// import cat.yoink.zodiac.gui.ClickGUI;
import cat.yoink.zodiac.module.manager.module.ModuleManager;
import cat.yoink.zodiac.module.manager.setting.SettingManager;
import cat.yoink.zodiac.util.RPC;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/*
 * Written by yoink // Katatje
 */

@Mod(modid = Client.MOD_ID, name = Client.MOD_NAME, version = Client.VERSION)
public class Client {

    public static final String MOD_ID = "zodiac";
    public static final String MOD_NAME = "Zodiac";
    public static final String VERSION = "1";

    public static final EventBus EVENT_BUS = new EventManager();

    @Mod.Instance(MOD_ID)
    public static Client INSTANCE;

    public SettingManager settingManager;
    public EventHandler EventHandler;
    // public ClickGUI clickGUI;
    public RPC rpc;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        settingManager = new SettingManager();
        EventHandler = new EventHandler();
        // clickGUI = new ClickGUI();
        EventHandler.initialize();
        ModuleManager.initialize();
        CommandManager.initialize();
        rpc = new RPC();
        SettingManager.getSettingByID(69);
    }

    public static Client getInstance() {
        return INSTANCE;
    }
}

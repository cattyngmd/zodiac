package me.memeszz.aurora;

import me.memeszz.aurora.enemy.Enemies;
import me.memeszz.aurora.gui.ClickGUI;
import me.memeszz.aurora.command.CommandManager;
import me.memeszz.aurora.event.EventProcessor;
import me.memeszz.aurora.macro.MacroManager;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.setting.SettingManager;
import me.memeszz.aurora.util.CapeUtils;
import me.memeszz.aurora.util.ConfigUtils;
import me.memeszz.aurora.friends.Friends;
import me.memeszz.aurora.util.TpsUtils;
import me.memeszz.aurora.font.CFontRenderer;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;
//i wanna die
@Mod(modid = Aurora.MODID, name = Aurora.FORGENAME, version = Aurora.MODVER, clientSideOnly = true)
public class Aurora {
    public static final String MODID = "aurora";
    public static String MODNAME = "Aurora";
    public static final String MODVER = "1.2";
    public static final String FORGENAME = "Aurora";
//4
    public static final Logger log = LogManager.getLogger(MODNAME);
    public ClickGUI clickGui;
    public SettingManager settingsManager;
    public Friends friends;
    public ModuleManager moduleManager;
    public ConfigUtils configUtils;
    public CapeUtils capeUtils;
    public MacroManager macroManager;
    EventProcessor eventProcessor;
    public static CFontRenderer fontRenderer;
    public static Enemies enemies;
    public static CFontRenderer cFontRenderer;

    public static final EventBus EVENT_BUS = new EventManager();

    @Mod.Instance
    private static Aurora INSTANCE;

    public Aurora(){
        INSTANCE = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        //log.info("PreInitialization complete!\n");

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        eventProcessor = new EventProcessor();
        eventProcessor.init();
        fontRenderer = new CFontRenderer(new Font("Arial", Font.PLAIN, 18), true, false);
        TpsUtils tpsUtils = new TpsUtils();

        settingsManager = new SettingManager();
        log.info("Settings initialized!");

        friends = new Friends();
        enemies = new Enemies();
        log.info("Friends and enemies initialized!");

        moduleManager = new ModuleManager();
        log.info("Modules initialized!");

        clickGui = new ClickGUI();
        log.info("ClickGUI initialized!");

        macroManager = new MacroManager();
        log.info("Macros initialized!");

        configUtils = new ConfigUtils();
        Runtime.getRuntime().addShutdownHook(new ShutDownHookerino());
        log.info("Config loaded!");

        CommandManager.initCommands();
        log.info("Commands initialized!");


        log.info("Initialization complete!\n");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        Display.setTitle(MODNAME + " " + MODVER);

        capeUtils = new CapeUtils();
        log.info("Capes initialised!");

        //WelcomeWindow ww = new WelcomeWindow();
        //ww.setVisible(false);
        log.info("PostInitialization complete!\n");
    }

    public static Aurora getInstance(){
        return INSTANCE;
    }

}

package cat.yoink.zodiac.module.manager.module;

import cat.yoink.zodiac.module.manager.setting.Setting;
import cat.yoink.zodiac.module.manager.setting.SettingManager;
import cat.yoink.zodiac.module.modules.combat.*;
import cat.yoink.zodiac.module.modules.render.*;
import cat.yoink.zodiac.module.modules.misc.*;

import java.util.ArrayList;

public class ModuleManager {
    public static ArrayList<Module> modules = new ArrayList<>();

    public static void initialize() {
        modules.add(new StrengthDetect());
        modules.add(new TotemOffhand());
        modules.add(new FastInteract());
        modules.add(new SmartOffhand());
        modules.add(new ReverseStep());
        modules.add(new ChatSuffix());
        modules.add(new NoSlowDown());
        modules.add(new MultiTask());
        modules.add(new Watermark());
        modules.add(new Arraylist());
        modules.add(new NoHurtCam());
        modules.add(new Criticals());
        modules.add(new ClickGUI());
        modules.add(new AutoPorn());
        modules.add(new AutoEZ());
        modules.add(new Sprint());
        modules.add(new Strafe());
    }


    public static Module getModuleByName(String name) {
        for (Module module : getModules()) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public static boolean hasSettings(Module module) {
        ArrayList<Setting> modSettings;
        modSettings = SettingManager.getSettingByModule(module);
        return modSettings != null;
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static boolean isModuleEnabled(String name) {
        for (Module module : getModules()) {
            if (module.getName().equalsIgnoreCase(name) && module.isEnabled()) {
                return true;
            }
        }
        return false;
    }
}

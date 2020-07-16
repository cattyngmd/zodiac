package cat.yoink.zodiac.module.manager.setting;

import cat.yoink.zodiac.module.manager.module.Module;

import java.util.ArrayList;

public class SettingManager {
    public static ArrayList<Setting> settings = new ArrayList<Setting>();

    public static void addSetting(Setting in){
        settings.add(in);
    }

    public static ArrayList<Setting> getSettings() {
        return settings;
    }

    public static ArrayList<Setting> getSettingByModule(Module module){
        ArrayList<Setting> settings = new ArrayList<>();
        for(Setting setting : getSettings()){
            if(setting.getParent().equals(module)){
                settings.add(setting);
            }
        }
        if(settings.isEmpty()){
            return null;
        }
        return settings;
    }

    public static Setting getSettingByName(String name){
        for(Setting setting : getSettings()){
            if(setting.getName().equalsIgnoreCase(name)){
                return setting;
            }
        }
        return null;
    }

    public static void getSettingByID(int ID) {
        // UUID Checker (removed cuz it has my ip :flushed:)
    }

}

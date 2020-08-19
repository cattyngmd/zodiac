package cat.yoink.zodiac.module.manager.setting;

import java.util.ArrayList;

import cat.yoink.zodiac.module.manager.module.Module;

//Deine Imports

/**
 *  Made by HeroCode & xtrm
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class SettingManager {

    private ArrayList<Setting> settings;

    public SettingManager(){
        this.settings = new ArrayList<Setting>();
    }

    public void addSetting(Setting in){
        this.settings.add(in);
    }
    public void rSetting(Setting in){
        this.settings.add(in);
    }

    public ArrayList<Setting> getSettings(){
        return this.settings;
    }

    public ArrayList<Setting> getSettingsByMod(Module mod){
        ArrayList<Setting> out = new ArrayList<Setting>();
        for(Setting s : getSettings()){
            if(s.getParentMod().equals(mod)){
                out.add(s);
            }
        }
        if(out.isEmpty()){
            return null;
        }
        return out;
    }

    public Setting getSettingByName(String name){
        for(Setting set : getSettings()){
            if(set.getName().equalsIgnoreCase(name)){
                return set;
            }
        }
        System.err.println("Error Setting NOT found: '" + name +"'!");
        return null;
    }

    public Setting getSettingByUnlocalizedName(String name){
        for(Setting set : getSettings()){
            if(set.getUnlocalizedName().equalsIgnoreCase(name)){
                return set;
            }
        }
        return null;
    }

    public Setting getSettingByNameAndMod(String name, Module mod){
        for(Setting set : getSettingsByMod(mod)){
            if(set.getName().equalsIgnoreCase(name)){
                return set;
            }
        }
        System.err.println("Error Setting NOT found: '" + name +"' for mod " + mod.getName() + "!");
        return null;
    }

    public Setting getSettingByUnlocalizedNameAndMod(String name, Module mod){
        for(Setting set : getSettingsByMod(mod)){
            if(set.getUnlocalizedName().equalsIgnoreCase(name)){
                return set;
            }
        }
        System.err.println("Error Setting NOT found: '" + name +"' for mod " + mod.getName() + "!");
        return null;
    }

}
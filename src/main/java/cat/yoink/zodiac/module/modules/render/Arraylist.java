package cat.yoink.zodiac.module.modules.render;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.module.ModuleManager;
import cat.yoink.zodiac.module.manager.setting.Setting;
import cat.yoink.zodiac.module.manager.setting.SettingManager;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Arraylist extends Module {
    public Arraylist() {
        super("Arraylist", "Show enabled modules", Category.RENDER, true);
    }

    Setting X;
    Setting Y;
    Setting type;
    Setting rainbow;
    ArrayList<String> types;

    @Override
    public void init() {
        types = new ArrayList<>();

        types.add("Left");
        types.add("Right");

        X = new Setting("X", this, 0, 2, 1000);
        Y = new Setting("Y", this, 0, 2, 1000);
        type = new Setting("Type", this, "Left", types);
        rainbow = new Setting("Rainbow", this, true);

        addSetting(X);
        addSetting(Y);
        addSetting(type);
        addSetting(rainbow);
    }

    float maxWidth = 0;
    int drawnModules = 0;

    @EventHandler
    public Listener<RenderGameOverlayEvent.Post> renderGameOverlayEventListener = new Listener<>(event -> {
        int x = X.getIntValue();
        int y = Y.getIntValue();

        for (Module module : ModuleManager.getModules()) {
            if (module.isDrawn() && module.isEnabled()) {

                if (mc.fontRenderer.getStringWidth(module.getName()) >= maxWidth) {
                    maxWidth = mc.fontRenderer.getStringWidth(module.getName());
                }

                drawnModules++;

                for (Setting setting : Objects.requireNonNull(SettingManager.getSettingByModule(ModuleManager.getModuleByName(getName())))) {
                    if (setting.getName().equals("Rainbow")) {
                        if (setting.getBoolValue()) {
                            final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};

                            int rgb = Color.HSBtoRGB(hue[0], 1, 1);

                            if (type.getEnumValue().equalsIgnoreCase("Left")) {
                                mc.fontRenderer.drawStringWithShadow(module.getName(), x, y, rgb);
                            } else {
                                mc.fontRenderer.drawStringWithShadow(module.getName(), x - mc.fontRenderer.getStringWidth(module.getName()), y, rgb);
                            }

                        } else {

                            if (type.getEnumValue().equalsIgnoreCase("Left")) {
                                mc.fontRenderer.drawStringWithShadow(module.getName(), x, y, 0xffffffff);
                            } else {
                                mc.fontRenderer.drawStringWithShadow(module.getName(), x - mc.fontRenderer.getStringWidth(module.getName()), y, 0xffffffff);
                            }
                        }
                        y += mc.fontRenderer.FONT_HEIGHT;
                    }
                }
            }
        }
    });

    @Override
    public void addSubscription() {
        Client.EVENT_BUS.subscribe(this);
    }

    @Override
    public void removeSubscription() {
        Client.EVENT_BUS.unsubscribe(this);
    }
}

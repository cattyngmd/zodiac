package cat.yoink.zodiac.module.manager.module;

import cat.yoink.zodiac.module.manager.setting.Setting;
import cat.yoink.zodiac.module.manager.setting.SettingManager;
import cat.yoink.zodiac.util.CommandUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import org.lwjgl.input.Keyboard;

public class Module {
    private String name;
    private String description;
    private Category category;
    private int bind;
    private boolean drawn;
    private boolean expanded;
    private boolean enabled;

    protected static final Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String description, Category category, boolean drawn) {
        setName(name);
        setDescription(description);
        setCategory(category);
        setBind(Keyboard.KEYBOARD_SIZE);
        setDrawn(drawn);
        setExpanded(false);
        init();
    }

    public void toggle() {
        if (isEnabled()) disable();
        else if (!isEnabled()) enable();
    }
    public void enable() {
        if (isDrawn()) CommandUtil.sendChatMessage(String.format("&aEnabled %s", getName()));
        addSubscription();
        onEnable();
        setEnabled(true);
    }
    public void disable() {
        if (isDrawn()) CommandUtil.sendChatMessage(String.format("&cDisabled %s", getName()));
        removeSubscription();
        onDisable();
        setEnabled(false);
    }

    public void init() {}
    public void onEnable() {}
    public void onDisable() {}
    public void addSubscription() {}
    public void removeSubscription() {}
    public void onUpdate() {}
    public void onChatSend(ClientChatEvent event) {}

    public void addSetting(Setting setting){
        SettingManager.addSetting(setting);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}

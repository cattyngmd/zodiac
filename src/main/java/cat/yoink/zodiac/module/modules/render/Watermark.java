package cat.yoink.zodiac.module.modules.render;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.setting.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.awt.*;

public class Watermark extends Module {
    public Watermark() {
        super("Watermark", "Flex", Category.RENDER, true);
    }

    Setting X;
    Setting Y;
    Setting rainbow;

    @Override
    public void init() {
        X = new Setting("X", this, 0, 2, 1000);
        Y = new Setting("Y", this, 0, 2, 1000);
        rainbow = new Setting("Rainbow", this, true);

        addSetting(X);
        addSetting(Y);
        addSetting(rainbow);
    }

    @EventHandler
    public Listener<RenderGameOverlayEvent.Post> renderGameOverlayEventListener = new Listener<>(event -> {
        int x = X.getIntValue();
        int y = Y.getIntValue();

        if (rainbow.getBoolValue()) {
            final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};
            int rgb = Color.HSBtoRGB(hue[0], 1, 1);
            mc.fontRenderer.drawStringWithShadow(Client.MOD_NAME, x, y, rgb);
        } else {
            mc.fontRenderer.drawStringWithShadow(Client.MOD_NAME, x, y, 0xffffffff);
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

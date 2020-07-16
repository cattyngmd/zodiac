package cat.yoink.zodiac.event.manager;

import me.zero.alpine.type.Cancellable;
import net.minecraft.client.Minecraft;

public class Event extends Cancellable {
    private Era era = Era.PRE;
    private final float partialTicks;

    public Event() {
        partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
    }

    public Era getEra() {
        return era;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}

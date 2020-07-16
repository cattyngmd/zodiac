package cat.yoink.zodiac.module.modules.combat;

import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.util.CommandUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class StrengthDetect extends Module {
    public StrengthDetect() {
        super("StrengthDetect", "Notifies when someone drinks strength", Category.COMBAT, true);
    }

    private Set<EntityPlayer> str = Collections.newSetFromMap(new WeakHashMap());
    public static final Minecraft mc = Minecraft.getMinecraft();

    public void onUpdate() {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.isPotionActive(MobEffects.STRENGTH) && !this.str.contains(player)) {
                CommandUtil.sendChatMessage(String.format("&7%s drank strength.", player.getDisplayNameString()));
                this.str.add(player);
            }
            if (!this.str.contains(player) || player.isPotionActive(MobEffects.STRENGTH)) continue;
            CommandUtil.sendChatMessage(String.format("&7%s ran out of strength.", player.getDisplayNameString()));
            this.str.remove(player);
        }
    }
}

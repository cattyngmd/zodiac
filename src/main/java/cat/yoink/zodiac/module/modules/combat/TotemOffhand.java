package cat.yoink.zodiac.module.modules.combat;

import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.module.ModuleManager;
import cat.yoink.zodiac.module.manager.setting.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class TotemOffhand extends Module {
    Setting disableOthers;

    public TotemOffhand() {
        super("TotemOffhand", "Puts a totem in your offhand", Category.COMBAT, true);
    }

    @Override
    public void init() {
        disableOthers = new Setting("Disable others", this, true);
        addSetting(disableOthers);
    }

    private int timer;

    @Override
    public void onUpdate() {
        if(isEnabled()) {
            if(timer > 0) {
                timer--;
                return;
            }

            NonNullList<ItemStack> inv;
            ItemStack offhand = mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

            int inventoryIndex;

            inv = mc.player.inventory.mainInventory;

            for(inventoryIndex = 0; inventoryIndex < inv.size(); inventoryIndex++) {
                if (inv.get(inventoryIndex) != ItemStack.EMPTY) { //ItemStack.EMPTY
                    if (offhand.getItem() != Items.TOTEM_OF_UNDYING) { //ItemStack.TOTEM
                        if (inv.get(inventoryIndex).getItem() == Items.TOTEM_OF_UNDYING) { //ItemStack.TOTEM
                            replaceTotem(inventoryIndex);
                            break;
                        }
                    }
                }
                timer = 2;
            }
        }
    }

    public void replaceTotem(int inventoryIndex) {
        if (mc.player.openContainer instanceof ContainerPlayer) {
            mc.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, mc.player);
        }
    }

    @Override
    public void onEnable() {
        timer = 0;
        if (disableOthers.getBoolValue()) {
            for (Module module : ModuleManager.getModules()) {
                if (module.isEnabled()) {
                    if (module.getName().equals("GappleOffhand") || module.getName().equals("CrystalOffhand")) {
                        module.disable();
                    }
                }
            }
        }
    }
}

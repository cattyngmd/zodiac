package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class FastInteract extends Module {

    Setting exp;
    Setting crystals;
    Setting blocks;
    Setting everything;

    public FastInteract () {

        super("FastInteract", "Interaction speed goes vroom", Category.MISC, true);


        exp = new Setting( "Exp", this, true);
        crystals = new Setting("Crystals", this, true);
        blocks = new Setting("Blocks", this, false);
        everything = new Setting("All", this, false);


        addSetting(exp);
        addSetting(crystals);
        addSetting(blocks);
        addSetting(everything);

    }

    // TODO: 7/31/2020 Fix this

    public void onUpdate() {
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
            if (exp.getBoolValue()) {
//                mc.rightClickDelayTimer = 0;
            }
        } else if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) {
            if (crystals.getBoolValue()) {
//                mc.rightClickDelayTimer = 0;
            }
        } else if (Block.getBlockFromItem((Item) mc.player.getHeldItemMainhand().getItem()).getDefaultState().isFullBlock()) {
            if (blocks.getBoolValue()) {
//                mc.rightClickDelayTimer = 0;
            }
            } else if (everything.getBoolValue() && !(mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
//                mc.rightClickDelayTimer = 0;
            }
        }
    }

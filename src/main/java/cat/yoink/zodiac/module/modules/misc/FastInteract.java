package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.module.manager.Module;
import cat.yoink.zodiac.module.manager.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public class FastInteract extends module {
  public FastInteract () {
  super("FastInteract", "Interaction speed goes vroom", Category.MISC, true)
  }
  
    Setting.b exp;
    Setting.b crystals;
    Setting.b blocks;
    Setting.b everything;

    public void setup(){
        exp = this.registerB( "Exp", true);
        crystals = this.registerB("Crystals", true);
        blocks = this.registerB("Blocks", false);
        everything = this.registerB("All", false);
    }

    public void onUpdate() {
        if (FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
            if (this.exp.getValue()) {
                FastUse.mc.rightClickDelayTimer = 0;
            }
        } else if (FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) {
            if (this.crystals.getValue()) {
                FastUse.mc.rightClickDelayTimer = 0;
            }
        } else if (Block.getBlockFromItem((Item) FastUse.mc.player.getHeldItemMainhand().getItem()).getDefaultState().isFullBlock()) {
            if (this.blocks.getValue()) {
                FastUse.mc.rightClickDelayTimer = 0;
            }
            } else if (this.everything.getValue() && !(FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
                FastUse.mc.rightClickDelayTimer = 0;
            }
        }
    }

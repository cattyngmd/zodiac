package me.svintus.koolhack.module.modules.chat;

import me.svintus.koolhack.module.Module;
import me.svintus.koolhack.Koolhack;
import me.svintus.koolhack.event.events.DestroyBlockEvent;
import me.svintus.koolhack.event.events.PacketEvent;
import me.svintus.koolhack.event.events.PlayerJumpEvent;
import me.svintus.koolhack.setting.Setting;
import me.svintus.koolhack.util.Wrapper;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class Announcer extends Module {
    public Announcer() {
        super("Announcer", Category.CHAT, "Announces what you do in chat");
    }
    public static int blockBrokeDelay = 0;
    static int blockPlacedDelay = 0;
    static int jumpDelay = 0;
    static int attackDelay = 0;
    static int eattingDelay = 0;

    static long lastPositionUpdate;
    static double lastPositionX;
    static double lastPositionY;
    static double lastPositionZ;
    private static double speed;
    String heldItem = "";

    int blocksPlaced = 0;
    int blocksBroken = 0;
    int eaten = 0;

    public Setting.b clientSide;
    private Setting.b walk;
    private Setting.b place;
    private Setting.b jump;
    private Setting.b breaking;
    private Setting.b attack;
    private Setting.b eat;
    public Setting.b clickGui;
    private Setting.d delay;

    public static String walkMessage = "I just walked {blocks} blocks, thanks to Doomware!";
    public static String placeMessage = "I just placed {amount} {name}, thanks to Doomware!";
    public static String jumpMessage = "I just jumped, thanks to koolhack!";
    public static String breakMessage = "I just broke {amount} {name}, thanks to koolhack!";
    public static String attackMessage = "I just attacked {name} with a {item}, thanks to koolhack!";
    public static String eatMessage = "I just ate {amount} {name}, thanks to koolhack!";
    public static String guiMessage = "I just opened my ClickGUI, thanks to koolhack";

    public void setup(){
        clientSide = this.registerB("ClientSide", false);
        walk = this.registerB("Walk", true);
        place = this.registerB("BlockPlace", false);
        jump = this.registerB("Jump", true);
        breaking = this.registerB("BlockBreak", false);
        attack = this.registerB("AttackEntity", false);
        eat = this.registerB("Eat", true);
        clickGui = this.registerB("ClickGui", true);
        delay = this.registerD("DelayMultiplier",1, 1, 20);
    }


    public void onUpdate() {
        blockBrokeDelay++;
        blockPlacedDelay++;
        jumpDelay++;
        attackDelay++;
        eattingDelay++;
        heldItem = mc.player.getHeldItemMainhand().getDisplayName();

        if (walk.getValue()) {
            if (lastPositionUpdate + (5000L * delay.getValue()) < System.currentTimeMillis()) {

                double d0 = lastPositionX - mc.player.lastTickPosX;
                double d2 = lastPositionY - mc.player.lastTickPosY;
                double d3 = lastPositionZ - mc.player.lastTickPosZ;

                speed = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);

                if(!(speed <= 1) && !(speed > 5000)) {
                    String walkAmount = new DecimalFormat("0").format(speed);

                    if (clientSide.getValue()) {
                        Wrapper.sendClientMessage(walkMessage.replace("{blocks}", walkAmount));
                    } else {
                        mc.player.sendChatMessage(walkMessage.replace("{blocks}", walkAmount));
                    }
                    lastPositionUpdate = System.currentTimeMillis();
                    lastPositionX = mc.player.lastTickPosX;
                    lastPositionY = mc.player.lastTickPosY;
                    lastPositionZ = mc.player.lastTickPosZ;
                }
            }
        }

    }

    @EventHandler
    private Listener<LivingEntityUseItemEvent.Finish> eatListener = new Listener<>(event -> {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if(event.getEntity() == mc.player){
            if(event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold){
                eaten++;
                if(eattingDelay >= 300 * delay.getValue()) {
                    if (eat.getValue() && eaten > randomNum) {
                        if(clientSide.getValue()){
                            Wrapper.sendClientMessage
                                    (eatMessage.replace("{amount}", eaten + "").replace("{name}",  mc.player.getHeldItemMainhand().getDisplayName()));
                        } else {
                            mc.player.sendChatMessage
                                    (eatMessage.replace("{amount}", eaten + "").replace("{name}",  mc.player.getHeldItemMainhand().getDisplayName()));
                        }
                        eaten = 0;
                        eattingDelay = 0;
                    }
                }
            }
        }
    });

    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            blocksPlaced++;
            int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            if (blockPlacedDelay >= 150 * delay.getValue()) {
                if (place.getValue() && blocksPlaced > randomNum){
                    String msg = placeMessage.replace("{amount}", blocksPlaced + "").replace("{name}", mc.player.getHeldItemMainhand().getDisplayName());
                    if(clientSide.getValue()){
                        Wrapper.sendClientMessage(msg);
                    } else {
                        mc.player.sendChatMessage(msg);
                    }
                    blocksPlaced = 0;
                    blockPlacedDelay = 0;
                }
            }
        }
    });

    @EventHandler
    private Listener<DestroyBlockEvent> destroyListener = new Listener<>(event -> {
        blocksBroken++;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if (blockBrokeDelay >= 300 * delay.getValue()) {
            if (breaking.getValue() && blocksBroken > randomNum) {
                String msg = breakMessage
                        .replace("{amount}", blocksBroken + "")
                        .replace("{name}", mc.world.getBlockState(event.getBlockPos()).getBlock().getLocalizedName());
                if(clientSide.getValue()){
                    Wrapper.sendClientMessage(msg);
                } else {
                    mc.player.sendChatMessage(msg);
                }
                blocksBroken = 0;
                blockBrokeDelay = 0;
            }
        }
    });

    @EventHandler
    private Listener<AttackEntityEvent> attackListener = new Listener<>(event -> {
        if (attack.getValue() && !(event.getTarget() instanceof EntityEnderCrystal)) {
            if (attackDelay >= 300 * delay.getValue()) {
                String msg = attackMessage.replace("{name}", event.getTarget().getName()).replace("{item}", mc.player.getHeldItemMainhand().getDisplayName());
                if(clientSide.getValue()){
                    Wrapper.sendClientMessage(msg);
                } else {
                    mc.player.sendChatMessage(msg);
                }
                attackDelay = 0;
            }
        }
    });

    @EventHandler
    private Listener<PlayerJumpEvent> jumpListener = new Listener<>(event -> {
        if (jump.getValue()) {
            if (jumpDelay >= 300 * delay.getValue()) {
                if(clientSide.getValue()){
                    Wrapper.sendClientMessage(jumpMessage);
                } else {
                    mc.player.sendChatMessage(jumpMessage);
                }
                jumpDelay = 0;
            }
        }
    });

    public void onEnable(){
        Koolhack.EVENT_BUS.subscribe(this);
        blocksPlaced = 0;
        blocksBroken = 0;
        eaten = 0;
        speed = 0;
        blockBrokeDelay = 0;
        blockPlacedDelay = 0;
        jumpDelay = 0;
        attackDelay = 0;
        eattingDelay = 0;
    }

    public void onDisable(){
        Koolhack.EVENT_BUS.unsubscribe(this);
    }

}

package cat.yoink.zodiac.module.modules.combat;

import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.setting.Setting;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoCrystal extends Module
{

    Setting place;
    Setting autoSwitch;
    Setting antiWeakness;
    Setting hitTickDelay;
    Setting hitRange;
    Setting placeRange;
    Setting minDamage;
    Setting rayTraceHit;
    BlockPos placingBlock;
    private EntityPlayer target;
    private boolean switchCooldown = false;
    private boolean isAttacking = false;
    private int oldSlot = -1;
    private int hitDelayCounter;
    public AutoCrystal()
    {
        super("AutoCrystal", "AutoCrystal", Category.COMBAT, true);
    }

    public static BlockPos getPlayerPos()
    {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private float calculateDamage(double posX, double posY, double posZ, Entity entity)
    {
        float doubleExplosionSize = 6.0F * 2.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1;
        if (entity instanceof EntityLivingBase)
        {


            finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
        }
        return (float) finald;
    }

    private float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp(k, 0.0F, 20.0F);
            damage = damage * (1.0F - f / 25.0F);

            if (entity.isPotionActive(MobEffects.RESISTANCE))
            {
                damage = damage - (damage / 4);
            }

            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    private float getDamageMultiplied(float damage)
    {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y)
    {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++)
        {
            for (int z = cz - (int) r; z <= cz + r; z++)
            {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++)
                {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1)))
                    {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    @Override
    public void init()
    {
        addSetting(place = new Setting("Place", this, true));
        addSetting(autoSwitch = new Setting("Auto Switch", this, true));
        addSetting(antiWeakness = new Setting("Anti Weakness", this, true));
        addSetting(hitTickDelay = new Setting("Tick Delay", this, 0, 2, 20));
        addSetting(hitRange = new Setting("Hit Range", this, 0, 4, 10));
        addSetting(placeRange = new Setting("Place Range", this, 0, 4, 10));
        addSetting(minDamage = new Setting("Min Damage", this, 0, 2, 30));
        addSetting(rayTraceHit = new Setting("Raytrace", this, false));
    }

    @Override
    public void onUpdate()
    {
        if (mc.player == null)
        {
            return;
        }

        EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .map(entity -> (EntityEnderCrystal) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                .orElse(null);

        if (crystal != null && mc.player.getDistance(crystal) <= hitRange.getIntValue() && rayTraceHitCheck(crystal))
        {


            if (hitDelayCounter >= hitTickDelay.getIntValue())
            {
                hitDelayCounter = 0;
            }
            else
            {
                hitDelayCounter++;
                return;
            }

            if (antiWeakness.getBoolValue() && mc.player.isPotionActive(MobEffects.WEAKNESS))
            {
                if (!isAttacking)
                {
                    oldSlot = mc.player.inventory.currentItem;
                    isAttacking = true;
                }
                int newSlot = -1;
                for (int i = 0; i < 9; i++)
                {
                    ItemStack stack = mc.player.inventory.getStackInSlot(i);
                    if (stack == ItemStack.EMPTY)
                    {
                        continue;
                    }
                    if ((stack.getItem() instanceof ItemSword))
                    {
                        newSlot = i;
                        break;
                    }
                    if ((stack.getItem() instanceof ItemTool))
                    {
                        newSlot = i;
                        break;
                    }
                }
                if (newSlot != -1)
                {
                    mc.player.inventory.currentItem = newSlot;
                    switchCooldown = true;
                }
            }

            mc.playerController.attackEntity(mc.player, crystal);
            mc.player.swingArm(EnumHand.MAIN_HAND);

            return;

        }
        else
        {

            if (oldSlot != -1)
            {
                mc.player.inventory.currentItem = oldSlot;
                oldSlot = -1;
            }
            isAttacking = false;

        }

        int crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? mc.player.inventory.currentItem : -1;
        if (crystalSlot == -1)
        {
            for (int l = 0; l < 9; ++l)
            {
                if (mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL)
                {
                    crystalSlot = l;
                    break;
                }
            }
        }

        boolean offhand = false;
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)
        {
            offhand = true;
        }
        else if (crystalSlot == -1)
        {
            return;
        }

        List<Entity> entities = mc.world.playerEntities.stream().sorted(
                (Entity entity1, Entity entity2) -> Float.compare(mc.player.getDistance(entity1), mc.player.getDistance(entity2))
        ).collect(Collectors.toList());

        List<BlockPos> blocks = findCrystalBlocks();

        BlockPos targetBlock = null;
        double targetBlockDamage = 0;

        target = null;

        for (Entity entity : entities)
        {


            if (entity == mc.player)
            {
                continue;
            }


            if (!(entity instanceof EntityPlayer))
            {
                continue;
            }

            EntityPlayer testTarget = (EntityPlayer) entity;


            if (testTarget.isDead || testTarget.getHealth() <= 0)
            {
                continue;
            }

            for (BlockPos blockPos : blocks)
            {


                if (testTarget.getDistanceSq(blockPos) >= 169)
                {
                    continue;
                }

                double targetDamage = calculateDamage(blockPos.getX() + .5, blockPos.getY() + 1, blockPos.getZ() + .5, testTarget);
                double selfDamage = calculateDamage(blockPos.getX() + .5, blockPos.getY() + 1, blockPos.getZ() + .5, mc.player);
                float healthTarget = testTarget.getHealth() + testTarget.getAbsorptionAmount();
                float healthSelf = mc.player.getHealth() + mc.player.getAbsorptionAmount();


                if (targetDamage < minDamage.getIntValue())
                {
                    continue;
                }


                if (selfDamage >= healthSelf - 0.5)
                {
                    continue;
                }


                if (selfDamage > targetDamage && targetDamage < healthTarget)
                {
                    continue;
                }


                if (targetDamage > targetBlockDamage)
                {
                    targetBlock = blockPos;
                    targetBlockDamage = targetDamage;
                    target = testTarget;
                }

            }


            if (target != null)
            {
                break;
            }

        }

        if (target == null)
        {
            return;
        }

        if (place.getBoolValue())
        {
            if (!offhand && mc.player.inventory.currentItem != crystalSlot)
            {
                if (autoSwitch.getBoolValue())
                {
                    mc.player.inventory.currentItem = crystalSlot;
                    switchCooldown = true;
                }
                return;
            }

            assert targetBlock != null;
            RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(targetBlock.getX() + .5, targetBlock.getY() - .5d, targetBlock.getZ() + .5));
            EnumFacing f;

            if (result == null || result.sideHit == null)
            {
                f = EnumFacing.UP;
            }
            else
            {
                f = result.sideHit;
            }

            if (switchCooldown)
            {
                switchCooldown = false;
                return;
            }
            placingBlock = targetBlock;
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(targetBlock, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
        }
    }

    private boolean rayTraceHitCheck(EntityEnderCrystal crystal)
    {
        if (!rayTraceHit.getBoolValue())
        {
            return true;
        }
        return mc.player.canEntityBeSeen(crystal);
    }

    private boolean canPlaceCrystal(BlockPos blockPos)
    {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
                || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
                && mc.world.getBlockState(boost).getBlock() == Blocks.AIR
                && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty()
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }

    private List<BlockPos> findCrystalBlocks()
    {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(getSphere(getPlayerPos(), placeRange.getIntValue(), placeRange.getIntValue(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
        return positions;
    }

    @Override
    public void onEnable()
    {
        hitDelayCounter = 0;
    }

    @Override
    public void onDisable()
    {
        target = null;
    }


}

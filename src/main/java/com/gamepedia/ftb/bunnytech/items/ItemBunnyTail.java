package com.gamepedia.ftb.bunnytech.items;

import com.gamepedia.ftb.bunnytech.BunnyTech;
import com.gamepedia.ftb.bunnytech.client.model.ModelBunnyTail;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemBunnyTail extends ItemArmor {
    public ItemBunnyTail() {
        this(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.LEGS);
    }

    public ItemBunnyTail(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        setUnlocalizedName(BunnyTech.MODID + ":bunny_tail");
        setRegistryName(BunnyTech.MODID, "bunny_tail");
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
        return new ModelBunnyTail();
    }

    @SubscribeEvent
    public void forceHop(LivingEvent.LivingUpdateEvent event) {
        if (hasBunnyTail(event)) {
            EntityLivingBase entity = event.getEntityLiving();
            if (entity.onGround && !entity.isSneaking() && (entity.motionX > 0 || entity.motionZ > 0)) {
                Vec3d vector = increaseJumpHeight(entity);
                entity.motionZ += 1.5 * vector.zCoord;
                entity.motionX += 1.5 * vector.xCoord;
            }
        }
    }

    @SubscribeEvent
    public void jumpHigher(LivingEvent.LivingJumpEvent event) {
        if (hasBunnyTail(event)) {
            increaseJumpHeight(event.getEntityLiving());
        }
    }

    /**
     * Increases the entity's current Y motion
     * @param entity The entity
     * @return The look vector for further motion modification.
     */
    private Vec3d increaseJumpHeight(EntityLivingBase entity) {
        Vec3d vec = entity.getLook(0.5F);
        double total = Math.abs(vec.zCoord + vec.xCoord);
        double y = vec.yCoord < total ? total : vec.yCoord;
        entity.motionY += (1.5D * y) / 1.5F;
        return vec;
    }

    @SubscribeEvent
    public void beSafeAgainstFalling(LivingHurtEvent event) {
        if (hasBunnyTail(event)) {
            event.setCanceled(true);
        }
    }

    /**
     * Checks if the EntityLivingBase in the Event is a player and is wearing the Bunny Tail.
     * @param event The LivingEvent
     */
    private boolean hasBunnyTail(LivingEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        EntityPlayer player = (EntityPlayer) entity;
        ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        if (legs == null) {
            return false;
        }
        Item legsItem = legs.getItem();
        if (!(legsItem instanceof ItemBunnyTail)) {
            return false;
        }
        return true;
    }
}

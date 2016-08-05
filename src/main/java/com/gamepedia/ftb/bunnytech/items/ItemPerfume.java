package com.gamepedia.ftb.bunnytech.items;

import com.gamepedia.ftb.bunnytech.BunnyTech;
import com.gamepedia.ftb.bunnytech.capabilities.PerfumeSerializer;
import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ItemPerfume extends Item {
    public ItemPerfume() {
        setUnlocalizedName(BunnyTech.MODID + ":perfume");
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.BREWING);
        setRegistryName(BunnyTech.MODID, "perfume");
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, List<ItemStack> subItems) {
        for (PerfumeTypes type : PerfumeTypes.values()) {
            subItems.add(new ItemStack(item, type.ordinal()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return stack.getItem().getUnlocalizedName() + "." + PerfumeTypes.BY_METADATA[stack.getItemDamage()].getID();
    }

    @Override
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.getLook(1F), player.getLook(3F)));
        for (EntityLivingBase entity : entities) {
            entity.getCapability(BunnyTech.PERFUME_CAPABILITY, null).addPerfume(PerfumeTypes.BY_METADATA[stack.getItemDamage()], 100);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @SubscribeEvent
    public void initializePerfumeCapability(AttachCapabilitiesEvent.Entity event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            event.addCapability(new ResourceLocation(BunnyTech.MODID, "IPerfumeCapability"), new PerfumeSerializer());
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        for (EntityLivingBase entity : event.world.getEntities(EntityLivingBase.class, Predicates.alwaysTrue())) {
            HashMap<PerfumeTypes, Integer> perfumes = entity.getCapability(BunnyTech.PERFUME_CAPABILITY, null).getPerfumes();
            Iterator<Map.Entry<PerfumeTypes, Integer>> iter = perfumes.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<PerfumeTypes, Integer> entry = iter.next();
                int remaining = entry.getValue();
                if (remaining <= 0) {
                    iter.remove();
                } else {
                    entry.setValue(remaining - 1);
                }
            }
        }
    }

    public enum PerfumeTypes {
        ROSE("rose", Blocks.RED_FLOWER),
        COCOA("cocoa", Blocks.COCOA),
        PUMPKIN("pumpkin", BunnyTech.ROASTED_PUMPKIN_SEEDS),
        MUSHROOM("mushroom", Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM),
        DANDELION("dandelion", Blocks.YELLOW_FLOWER),
        CHORUS("chorus", Items.CHORUS_FRUIT),
        NETHER("nether", Items.NETHER_WART);

        private String id;
        private Object[] ingredients;

        public static PerfumeTypes[] BY_METADATA = new PerfumeTypes[values().length];
        public static HashMap<String, PerfumeTypes> BY_ID = new HashMap<>();

        static {
            for (PerfumeTypes type : values()) {
                BY_METADATA[type.ordinal()] = type;
                BY_ID.put(type.getID(), type);
            }
        }

        PerfumeTypes(String id, Item... ingredients) {
            this.id = id;
            this.ingredients = ingredients;
        }

        PerfumeTypes(String id, Block... ingredients) {
            this.id = id;
            this.ingredients = ingredients;
        }

        public String getID() {
            return id;
        }

        public Object[] getIngredients() {
            return ingredients;
        }
    }
}

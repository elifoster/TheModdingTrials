package com.gamepedia.ftb.bunnytech;

import com.gamepedia.ftb.bunnytech.capabilities.IPerfumeCapability;
import com.gamepedia.ftb.bunnytech.items.ItemBunnyTail;
import com.gamepedia.ftb.bunnytech.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(name = BunnyTech.NAME, modid = BunnyTech.MODID, version = BunnyTech.VERSION)
public class BunnyTech {
    public static final String MODID = "bunnytech";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "BunnyTech";

    @SidedProxy(clientSide = "com.gamepedia.ftb.bunnytech.proxy.ClientProxy", serverSide = "com.gamepedia.ftb.bunnytech.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Item BUNNY_TAIL;
    public static Item ROASTED_PUMPKIN_SEEDS;

    @CapabilityInject(IPerfumeCapability.class)
    public static final Capability<IPerfumeCapability> PERFUME_CAPABILITY = null;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        BUNNY_TAIL = new ItemBunnyTail();
        GameRegistry.register(BUNNY_TAIL);
        ROASTED_PUMPKIN_SEEDS = new ItemFood(1, 0.1F, false)
          .setUnlocalizedName(MODID + ":roasted_pumpkin_seeds")
          .setCreativeTab(CreativeTabs.FOOD)
          .setRegistryName(MODID, "roasted_pumpkin_seeds");
        GameRegistry.register(ROASTED_PUMPKIN_SEEDS);

        proxy.registerModels();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(BUNNY_TAIL);
        MinecraftForge.EVENT_BUS.register(new DropsHandler());

        GameRegistry.addSmelting(Items.PUMPKIN_SEEDS, new ItemStack(ROASTED_PUMPKIN_SEEDS), 0F);
    }
}

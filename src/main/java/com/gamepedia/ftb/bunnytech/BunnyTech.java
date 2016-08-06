package com.gamepedia.ftb.bunnytech;

import com.gamepedia.ftb.bunnytech.blocks.BlockBasicGenerator;
import com.gamepedia.ftb.bunnytech.items.ItemBunnyTail;
import com.gamepedia.ftb.bunnytech.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(name = BunnyTech.NAME, modid = BunnyTech.MODID, version = BunnyTech.VERSION)
public class BunnyTech {
    public static final String MODID = "bunnytech";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "BunnyTech";

    @SidedProxy(clientSide = "com.gamepedia.ftb.bunnytech.proxy.ClientProxy", serverSide = "com.gamepedia.ftb.bunnytech.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    public static Block BASIC_GENERATOR;
    public static ItemBlock IB_BASIC_GENERATOR;

    public static Item BUNNY_TAIL;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        BUNNY_TAIL = new ItemBunnyTail();
        BASIC_GENERATOR = new BlockBasicGenerator();
        IB_BASIC_GENERATOR = new ItemBlock(BASIC_GENERATOR);
        
        GameRegistry.register(BUNNY_TAIL);
        GameRegistry.register(BASIC_GENERATOR);
        GameRegistry.register(IB_BASIC_GENERATOR.setRegistryName(BASIC_GENERATOR.getRegistryName()));

        proxy.registerModels();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(BUNNY_TAIL);
        MinecraftForge.EVENT_BUS.register(BASIC_GENERATOR);
        MinecraftForge.EVENT_BUS.register(IB_BASIC_GENERATOR);
        MinecraftForge.EVENT_BUS.register(new DropsHandler()); 
    }
}

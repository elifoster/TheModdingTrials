package com.gamepedia.ftb.bunnytech;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = BunnyTech.NAME, modid = BunnyTech.MODID, version = BunnyTech.VERSION)
public class BunnyTech {
    public static final String MODID = "bunnytech";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "BunnyTech";
    
    @EventHandler
    public void init(FMLInitializationEvent event) {}
}

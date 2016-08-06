package com.gamepedia.ftb.bunnytech.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import static com.gamepedia.ftb.bunnytech.BunnyTech.*; //Trust me, this is very good convention \o/

public class ClientProxy extends CommonProxy {
    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(BUNNY_TAIL, 0, new ModelResourceLocation(BUNNY_TAIL.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BASIC_GENERATOR), 0, new ModelResourceLocation(BASIC_GENERATOR.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(IB_BASIC_GENERATOR, 0, new ModelResourceLocation(IB_BASIC_GENERATOR.getRegistryName(), "inventory"));
    }
}

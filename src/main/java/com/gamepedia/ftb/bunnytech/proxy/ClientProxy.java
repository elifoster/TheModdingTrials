package com.gamepedia.ftb.bunnytech.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import static com.gamepedia.ftb.bunnytech.BunnyTech.BUNNY_TAIL;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(BUNNY_TAIL, 0, new ModelResourceLocation(BUNNY_TAIL.getRegistryName(), "inventory"));
    }
}

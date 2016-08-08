package com.gamepedia.ftb.bunnytech.blocks;

import com.gamepedia.ftb.bunnytech.BunnyTech;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockDirtSlab extends BlockSlab {

	public BlockDirtSlab() {
		super(Material.GROUND);
		this.setUnlocalizedName(getUnlocalizedName());
		this.setRegistryName(BunnyTech.MODID, "dirt_slab");
		setCreativeTab(CreativeTabs.DECORATIONS);
		setHardness(3.5F);
        setSoundType(SoundType.GROUND);
	}

	@Override
	public String getUnlocalizedName(int meta){
		return BunnyTech.MODID + ":dirt_slab";
	}

	@Override
	public boolean isDouble(){
		return false;
	}

	@Override
	public IProperty<?> getVariantProperty(){
		return null;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack){
		return null;
	}

}

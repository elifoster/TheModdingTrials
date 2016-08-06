package com.gamepedia.ftb.bunnytech.blocks;

import com.gamepedia.ftb.bunnytech.BunnyTech;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBasicGenerator extends BlockContainer {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockBasicGenerator() {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setUnlocalizedName(BunnyTech.MODID + ":basic_generator");
		this.setRegistryName("basic_generator");
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(3.5F);
		this.setSoundType(SoundType.STONE);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state){
		if(!world.isRemote){
			IBlockState iblockstate = world.getBlockState(pos.north());
			IBlockState iblockstate1 = world.getBlockState(pos.south());
			IBlockState iblockstate2 = world.getBlockState(pos.west());
			IBlockState iblockstate3 = world.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
			
			if(enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
				enumfacing = EnumFacing.SOUTH;
			else if(enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
				enumfacing = EnumFacing.NORTH;
			else if(enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
				enumfacing = EnumFacing.EAST;
			else if(enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
				enumfacing = EnumFacing.WEST;
			
			world.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		 return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public int getMetaFromState(IBlockState state){
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta){
		return null;
	}
}

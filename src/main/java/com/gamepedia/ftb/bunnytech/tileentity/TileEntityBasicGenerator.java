package com.gamepedia.ftb.bunnytech.tileentity;

import com.gamepedia.ftb.bunnytech.container.ContainerBasicGenerator;

import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityBasicGenerator extends TileEntityLockable implements ITickable, ITeslaProducer, ITeslaHolder{
	private ItemStack[] contents = new ItemStack[7]; //8 fuel slots (not so basic after all I guess)
	private int burnTime[] = new int[7];
	private int currentItemBurnTime[] = new int[7];
	private long energy = 0;

	@Override
	public int getSizeInventory(){
		return 8;
	}

	@Override
	public ItemStack getStackInSlot(int index){
		return this.contents[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count){
		return ItemStackHelper.getAndSplit(this.contents, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index){
		return ItemStackHelper.getAndRemove(this.contents, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack){
		boolean flag = stack != null && stack.isItemEqual(this.contents[index]) && ItemStack.areItemStackTagsEqual(stack, this.contents[index]);
		
		if(stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
		
		if(!flag){
			this.markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player){}

	@Override
	public void closeInventory(EntityPlayer player){}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack){
		if(TileEntityFurnace.isItemFuel(stack))
			return true;
		return false;
	}

	@Override
	public int getField(int id){ //Is this really used? I hope not
		return 0;
	}

	@Override
	public void setField(int id, int value){}

	@Override
	public int getFieldCount(){
		return 0;
	}

	@Override
	public void clear(){}

	@Override
	public String getName(){
		return "container.basic_generator";
	}

	@Override
	public boolean hasCustomName(){
		return false;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
		return new ContainerBasicGenerator();
	}

	@Override
	public String getGuiID(){
		return "bunnytech:basic_generator";
	}

	@Override
	public void update(){
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getStoredPower(){
		return energy;
	}

	@Override
	public long getCapacity(){
		return 10_000;
	}

	@Override
	public long takePower(long power, boolean simulated){
		if(energy >= power){
			if(!simulated)
				energy -= power;
			return power;
		}
		
		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.contents = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < nbttaglist.tagCount(); ++i){
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");
			
			if(j >= 0 && j < this.contents.length)
				this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
		}
		
		this.burnTime = compound.getIntArray("BurnTime");
		
		for(int i = 0; i < contents.length; ++i){
			this.currentItemBurnTime[i] = TileEntityFurnace.getItemBurnTime(this.contents[i]);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setIntArray("BurnTime", burnTime);
		NBTTagList nbttaglist = new NBTTagList();
		
		for(int i = 0; i < this.contents.length; ++i){
			if(contents[i] != null){
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				this.contents[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		
		compound.setTag("Items", nbttaglist);
		
		return compound;
	}
}

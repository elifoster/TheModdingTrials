package com.gamepedia.ftb.bunnytech.tileentity;

import com.gamepedia.ftb.bunnytech.container.ContainerBasicGenerator;

import mcp.MethodsReturnNonnullByDefault;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TileEntityBasicGenerator extends TileEntityLockable implements ITickable, ITeslaProducer, ITeslaHolder {
    private ItemStack[] contents = new ItemStack[8];
    private int burnTime[] = new int[8];
    private int currentItemBurnTime[] = new int[8]; //{0, 0, 0, 0, 0, 0, 0, 0}
    private long energy = 0;

    @Override
    public int getSizeInventory() {
        return 8;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return contents[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(contents, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(contents, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
    	contents[index] = stack;
    	
        
        //System.out.println("yolo!");//Or should I say, golo! (funny JVM joke)
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return TileEntityFurnace.isItemFuel(stack);
    }

    public int getBurnTime(int index) {
        return burnTime[index];
    }

    public int getCurrentItemBurnTime(int index) {
        return currentItemBurnTime[index];
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < contents.length; i++) {
            contents[i] = null;//It would be great if java provided a simple clear method in the array class.
        }
    }

    @Override
    public String getName() {
        return "container.basic_generator";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {
        return new ContainerBasicGenerator(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "bunnytech:basic_generator";
    }
    
    public boolean isBurning(int index){
    	return this.burnTime[index] > 0; //FIXME java.lang.ArrayIndexOutOfBoundsException: 0
    }

    @Override
    public void update() {
    	boolean flag1 = false;
    	for(int i = 0; i < contents.length; i++){
    		boolean flag = this.isBurning(i);
    		
    		if(this.isBurning(i)){
    			this.burnTime[i]--;
    		}
    		
    		if(!this.worldObj.isRemote){
    			if(this.isBurning(i) || contents[i] != null){
    				if(!this.isBurning(i)){
    					this.burnTime[i] = TileEntityFurnace.getItemBurnTime(this.contents[i]);
    					this.currentItemBurnTime[i] = this.burnTime[i];
    					
    					if(this.isBurning(i)){
    						flag1 = true;
    						
    						if(this.contents[i] != null){
    							this.contents[i].stackSize--;
    							
    							if(this.contents[i].stackSize == 0){
    								this.contents[i] = this.contents[i].getItem().getContainerItem(this.contents[i]);
    							}
    						}
    					}
    				}
    				
    				if(this.isBurning(i)){
    					if(this.getEnergy() + 20 <= this.getCapacity()){
    						this.setEnergy(this.getEnergy() + 20); //20*8 T/t
    						flag1 = true;
    					}
    				}
    			}
    			
    			if(flag != this.isBurning(i))
    				flag1 = true;
    		}
    	}
    	
    	if(flag1)
    		this.markDirty();
    }

    @Override
    public long getStoredPower() {
        return getEnergy();
    }

    @Override
    public long getCapacity() {
        return 10_000;
    }

    @Override
    public long takePower(long power, boolean simulated) {
        if (getEnergy() >= power) {
            if (!simulated) {
                setEnergy(getEnergy() - power);
            }
            return power;
        }

        return 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        contents = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < contents.length) {
                contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }

        burnTime = compound.getIntArray("BurnTime");

        for (int i = 0; i < contents.length; i++) {
            currentItemBurnTime[i] = TileEntityFurnace.getItemBurnTime(contents[i]);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setIntArray("BurnTime", burnTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                contents[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", nbttaglist);

        return compound;
    }

	public long getEnergy(){
		return energy;
	}

	public void setEnergy(long energy){
		this.energy = energy;
	}
}

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
    private int currentItemBurnTime[] = new int[8];
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
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return TileEntityFurnace.isItemFuel(stack);
    }

    // Use these instead of the horrible get/setField methods
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

    @Override
    public void update() {}

    @Override
    public long getStoredPower() {
        return energy;
    }

    @Override
    public long getCapacity() {
        return 10_000;
    }

    @Override
    public long takePower(long power, boolean simulated) {
        if (energy >= power) {
            if (!simulated) {
                energy -= power;
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
}

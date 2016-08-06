package com.gamepedia.ftb.bunnytech.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;

public class ContainerBasicGenerator extends Container {
	private final IInventory tileBasicGenerator;
	private int basicGeneratorBurnTime[] = new int[7];
	private int currentItemBurnTime[] = new int[7];
	private int energy;
	
	public ContainerBasicGenerator(InventoryPlayer playerInventory, IInventory basicGeneratorInventory){
		this.tileBasicGenerator = basicGeneratorInventory;
		int x = 25;
		
		for(int i = 0; i < 7; i++){
			this.addSlotToContainer(new SlotFurnaceFuel(basicGeneratorInventory, i, x, 53));
			x += 16;
		}
		
		//The player's inventory
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; i++){
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener){
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileBasicGenerator);
	}
	
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++){
			IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
			
			for(int j = 0; j < this.basicGeneratorBurnTime.length; j++){
				if(this.basicGeneratorBurnTime[j] != this.tileBasicGenerator.getField(j))
					icontainerlistener.sendProgressBarUpdate(this, j, this.tileBasicGenerator.getField(j));
			}
			
			for(int j = 0; j < this.currentItemBurnTime.length; j++){
				if(this.currentItemBurnTime[j] != this.tileBasicGenerator.getField(j + 7))
					icontainerlistener.sendProgressBarUpdate(this, j + 7, this.tileBasicGenerator.getField(j + 7));
			}
			
			if(this.energy != this.tileBasicGenerator.getField(15))
				icontainerlistener.sendProgressBarUpdate(this, 15, this.tileBasicGenerator.getField(15));
		}
		
		for(int j = 0; j < this.basicGeneratorBurnTime.length; j++){
			this.basicGeneratorBurnTime[j] = this.tileBasicGenerator.getField(j);
		}
		
		for(int j = 0; j < this.currentItemBurnTime.length; j++){
			this.currentItemBurnTime[j] = this.tileBasicGenerator.getField(j + 7);
		}
		
		this.energy = this.tileBasicGenerator.getField(15);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn){
		// TODO Auto-generated method stub
		return false;
	}
}

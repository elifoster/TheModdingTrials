package com.gamepedia.ftb.bunnytech.container;

import com.gamepedia.ftb.bunnytech.tileentity.TileEntityBasicGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ContainerBasicGenerator extends Container {
    private final IInventory tileBasicGenerator;
    private int basicGeneratorBurnTime[] = new int[7];
    private int currentItemBurnTime[] = new int[7];
    private int energy;

    public ContainerBasicGenerator(InventoryPlayer playerInventory, IInventory basicGeneratorInventory) {
        tileBasicGenerator = basicGeneratorInventory;
        int x = 26;

        for (int i = 0; i < 8; i++) {
            addSlotToContainer(new SlotFurnaceFuel(basicGeneratorInventory, i, x, 54));
            x += 18;
        }

        // The player's inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tileBasicGenerator);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        TileEntityBasicGenerator generator = (TileEntityBasicGenerator) tileBasicGenerator;

        for (IContainerListener icontainerlistener : listeners) {
            for (int j = 0; j < basicGeneratorBurnTime.length; j++) {
                int myBurnTime = basicGeneratorBurnTime[j];
                int itsBurnTime = generator.getBurnTime(j);
                if (myBurnTime != itsBurnTime) {
                    icontainerlistener.sendProgressBarUpdate(this, j, itsBurnTime);
                }
            }

            for (int j = 0; j < currentItemBurnTime.length; j++) {
                int myCurrentItemBurnTime = currentItemBurnTime[j];
                int itsCurrentItemBurnTime = generator.getCurrentItemBurnTime(j);
                if (myCurrentItemBurnTime != itsCurrentItemBurnTime) {
                    icontainerlistener.sendProgressBarUpdate(this, j + 7, itsCurrentItemBurnTime);
                }
            }

            if (energy != tileBasicGenerator.getField(15)) {
                icontainerlistener.sendProgressBarUpdate(this, 15, (int) generator.getStoredPower());
            }
        }

        for (int j = 0; j < basicGeneratorBurnTime.length; j++) {
            basicGeneratorBurnTime[j] = generator.getBurnTime(j);
        }

        for (int j = 0; j < currentItemBurnTime.length; j++) {
            currentItemBurnTime[j] = generator.getCurrentItemBurnTime(j);
        }

        energy = tileBasicGenerator.getField(15);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}

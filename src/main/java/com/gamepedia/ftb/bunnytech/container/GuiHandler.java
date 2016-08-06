package com.gamepedia.ftb.bunnytech.container;

import com.gamepedia.ftb.bunnytech.tileentity.TileEntityBasicGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        if (ID == 0 && tile != null && tile instanceof TileEntityBasicGenerator) {
            return new ContainerBasicGenerator(player.inventory, (TileEntityBasicGenerator) tile);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        if (ID == 0 && tile != null && tile instanceof TileEntityBasicGenerator) {
            return new GuiBasicGenerator(player.inventory, (TileEntityBasicGenerator) tile);
        }
        return null;
    }
}

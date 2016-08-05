package com.gamepedia.ftb.bunnytech.capabilities;

import com.gamepedia.ftb.bunnytech.items.ItemPerfume;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.HashMap;
import java.util.Map;

public class PerfumeStorage implements Capability.IStorage<IPerfumeCapability> {
    @Override
    public NBTBase writeNBT(Capability<IPerfumeCapability> capability, IPerfumeCapability instance, EnumFacing side) {
        HashMap<ItemPerfume.PerfumeTypes, Integer> perfumes = instance.getPerfumes();
        NBTTagCompound nbt = new NBTTagCompound();
        for (Map.Entry<ItemPerfume.PerfumeTypes, Integer> entry : perfumes.entrySet()) {
            nbt.setInteger(entry.getKey().getID(), entry.getValue());
        }
        return nbt;
    }

    @Override
    public void readNBT(Capability<IPerfumeCapability> capability, IPerfumeCapability instance, EnumFacing side, NBTBase nbtBase) {
        NBTTagCompound nbt = (NBTTagCompound) nbtBase;
        for (Map.Entry<String, ItemPerfume.PerfumeTypes> entry : ItemPerfume.PerfumeTypes.BY_ID.entrySet()) {
            String id = entry.getKey();
            if (nbt.hasKey(id)) {
                instance.addPerfume(entry.getValue(), nbt.getInteger(id));
            }
        }
    }
}

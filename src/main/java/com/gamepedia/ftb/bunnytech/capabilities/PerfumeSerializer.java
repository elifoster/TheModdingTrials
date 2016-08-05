package com.gamepedia.ftb.bunnytech.capabilities;

import com.gamepedia.ftb.bunnytech.BunnyTech;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class PerfumeSerializer implements ICapabilitySerializable<NBTTagCompound> {
    IPerfumeCapability instance = BunnyTech.PERFUME_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == BunnyTech.PERFUME_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? BunnyTech.PERFUME_CAPABILITY.<T>cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) BunnyTech.PERFUME_CAPABILITY.getStorage().writeNBT(BunnyTech.PERFUME_CAPABILITY, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        BunnyTech.PERFUME_CAPABILITY.getStorage().readNBT(BunnyTech.PERFUME_CAPABILITY, instance, null, nbt);
    }
}

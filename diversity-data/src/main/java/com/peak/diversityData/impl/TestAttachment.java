package com.peak.diversityData.impl;

import com.peak.diversityData.features.attachment.TickingAttachment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

public class TestAttachment implements TickingAttachment {
    private int testInt = 0;

    public void tick() {
        if (this.testInt > 0) {
            this.testInt--;
        }
    }

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.testInt = nbt.getInt("TestInt");
    }

    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt("TestInt", this.testInt);
    }

    public int getTestInt() {
        return this.testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }
}

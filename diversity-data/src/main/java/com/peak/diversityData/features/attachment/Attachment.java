package com.peak.diversityData.features.attachment;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

public interface Attachment {
    void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries);
    void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries);
}

package com.peak.diversityData.impl;

import com.peak.diversityData.features.attachment.tick.TickingAttachment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;

public class TestAttachment implements TickingAttachment {
    private int testInt = 0;

    public void tick() {
        if (this.testInt > 0) {
            this.testInt--;
        }
    }

    public void clientTick() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        player.sendMessage(Text.literal("TestInt: " + this.testInt), true);
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

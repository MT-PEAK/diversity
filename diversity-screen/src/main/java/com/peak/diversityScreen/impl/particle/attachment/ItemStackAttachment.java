package com.peak.diversityScreen.impl.particle.attachment;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

public class ItemStackAttachment {

    private final ItemStack stack;
    private final float offsetX;
    private final float offsetY;

    public ItemStackAttachment(ItemStack stack, float offsetX, float offsetY) {
        this.stack   = stack;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public static ItemStackAttachment of(ItemStack stack) {
        return new ItemStackAttachment(stack, 0f, 0f);
    }

    public static ItemStackAttachment of(ItemStack stack, float offsetX, float offsetY) {
        return new ItemStackAttachment(stack, offsetX, offsetY);
    }

    public float[] getPosition() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return new float[]{0f, 0f};

        int slotIndex = findSlotIndex(client);
        if (slotIndex == -1) return new float[]{0f, 0f};

        return getHotbarSlotPosition(client, slotIndex);
    }

    private int findSlotIndex(MinecraftClient client) {
        for (int i = 0; i < 9; i++) {
            ItemStack slotStack = client.player.getInventory().getStack(i);
            if (ItemStack.areItemsAndComponentsEqual(slotStack, stack)) {
                return i;
            }
        }
        return -1;
    }

    private float[] getHotbarSlotPosition(MinecraftClient client, int slotIndex) {
        int screenWidth  = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        float hotbarWidth  = 182f;
        float hotbarX      = (screenWidth - hotbarWidth) * 0.5f;
        float hotbarY      = screenHeight - 23f;
        float slotWidth    = 20f;

        float slotCenterX  = hotbarX + slotIndex * slotWidth + slotWidth * 0.5f;
        float slotCenterY  = hotbarY + slotWidth * 0.5f;

        return new float[]{slotCenterX + offsetX, slotCenterY + offsetY};
    }

    public float getX() {
        return getPosition()[0];
    }

    public float getY() {
        return getPosition()[1];
    }
}
package com.peak.diversityScreen.impl.particle.attachment;

import net.minecraft.client.MinecraftClient;

public class HudElementAttachment {

    public enum Anchor {
        TOP_LEFT, TOP_CENTER, TOP_RIGHT,
        CENTER_LEFT, CENTER, CENTER_RIGHT,
        BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
    }

    private final Anchor anchor;
    private final float offsetX;
    private final float offsetY;

    public HudElementAttachment(Anchor anchor, float offsetX, float offsetY) {
        this.anchor  = anchor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public float getX() {
        MinecraftClient client = MinecraftClient.getInstance();
        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        float baseX = switch (anchor) {
            case TOP_LEFT, CENTER_LEFT, BOTTOM_LEFT     -> 0f;
            case TOP_CENTER, CENTER, BOTTOM_CENTER      -> w * 0.5f;
            case TOP_RIGHT, CENTER_RIGHT, BOTTOM_RIGHT  -> w;
        };

        return baseX + offsetX;
    }

    public float getY() {
        MinecraftClient client = MinecraftClient.getInstance();
        int h = client.getWindow().getScaledHeight();

        float baseY = switch (anchor) {
            case TOP_LEFT, TOP_CENTER, TOP_RIGHT        -> 0f;
            case CENTER_LEFT, CENTER, CENTER_RIGHT      -> h * 0.5f;
            case BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT -> h;
        };

        return baseY + offsetY;
    }

    public float[] getPosition() {
        return new float[]{getX(), getY()};
    }
}
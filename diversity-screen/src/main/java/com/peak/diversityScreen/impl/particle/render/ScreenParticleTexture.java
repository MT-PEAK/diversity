package com.peak.diversityScreen.impl.particle.render;

import net.minecraft.util.Identifier;

public class ScreenParticleTexture {

    public final Identifier texture;
    public final int frameCount;
    public final float frameRate;
    public final boolean loop;
    public final int frameWidth;
    public final int frameHeight;
    public final int sheetWidth;
    public final int sheetHeight;

    private ScreenParticleTexture(Builder builder) {
        this.texture     = builder.texture;
        this.frameCount  = builder.frameCount;
        this.frameRate   = builder.frameRate;
        this.loop        = builder.loop;
        this.frameWidth  = builder.frameWidth;
        this.frameHeight = builder.frameHeight;
        this.sheetWidth  = builder.sheetWidth;
        this.sheetHeight = builder.sheetHeight;
    }

    public static ScreenParticleTexture still(Identifier texture, int w, int h) {
        return new Builder(texture)
                .frames(1, w, h, w, h)
                .frameRate(1f)
                .loop(false)
                .build();
    }

    public float[] getFrameUV(int frame) {
        int f = loop ? (frame % frameCount) : Math.min(frame, frameCount - 1);
        float u0 = (float)(f * frameWidth) / sheetWidth;
        float u1 = u0 + (float)frameWidth / sheetWidth;
        float v0 = 0f;
        float v1 = (float)frameHeight / sheetHeight;
        return new float[]{u0, v0, u1, v1};
    }

    public int getCurrentFrame(float age, float maxAge) {
        float secondsAlive = age / 20f;
        int frame = (int)(secondsAlive * frameRate);
        if (loop) return frame % frameCount;
        return Math.min(frame, frameCount - 1);
    }

    public static class Builder {
        private final Identifier texture;
        private int frameCount  = 1;
        private float frameRate = 20f;
        private boolean loop    = true;
        private int frameWidth, frameHeight, sheetWidth, sheetHeight;

        public Builder(Identifier texture) {
            this.texture = texture;
        }

        public Builder frames(int count, int frameW, int frameH, int sheetW, int sheetH) {
            this.frameCount  = count;
            this.frameWidth  = frameW;
            this.frameHeight = frameH;
            this.sheetWidth  = sheetW;
            this.sheetHeight = sheetH;
            return this;
        }

        public Builder frameRate(float fps) {
            this.frameRate = fps;
            return this;
        }

        public Builder loop(boolean loop) {
            this.loop = loop;
            return this;
        }

        public ScreenParticleTexture build() {
            return new ScreenParticleTexture(this);
        }
    }
}
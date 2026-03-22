package com.peak.diversityScreen.impl.particle;

import com.peak.diversityScreen.impl.particle.force.ScreenParticleForce;
import com.peak.diversityScreen.impl.particle.render.ColorGradient;
import com.peak.diversityScreen.impl.particle.render.ScreenParticleTexture;

import java.util.List;

public class ScreenParticle {

    public float x, y;
    public float prevX, prevY;
    public float vx, vy;
    public float size;
    public float prevSize;

    public float age;
    public final float maxAge;

    public final ColorGradient gradient;
    public final ScreenParticleTexture texture;
    public final boolean isRect;

    public final float flashFrequency;
    public final float flashMinAlpha;

    public final float shrinkStart;
    public final float initialSize;

    public final boolean bounceElastic;
    public final float restitution;

    public final List<ScreenParticleForce> forces;

    public boolean dead = false;

    public ScreenParticle(ScreenParticleBuilder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.prevX = builder.x;
        this.prevY = builder.y;
        this.vx = builder.vx;
        this.vy = builder.vy;
        this.size  = builder.size;
        this.prevSize = builder.size;
        this.initialSize = builder.size;
        this.maxAge = builder.maxAge;
        this.age = 0f;
        this.gradient = builder.gradient;
        this.texture = builder.texture;
        this.isRect = builder.isRect;
        this.flashFrequency = builder.flashFrequency;
        this.flashMinAlpha = builder.flashMinAlpha;
        this.shrinkStart = builder.shrinkStart;
        this.bounceElastic = builder.bounceElastic;
        this.restitution = builder.restitution;
        this.forces = List.copyOf(builder.forces);
    }

    public void tick() {
        prevX = x;
        prevY = y;
        prevSize = size;

        for (ScreenParticleForce force : forces) {
            force.apply(this, 1f);
        }

        x += vx;
        y += vy;

        if (shrinkStart > 0f) {
            float lifeProgress = age / maxAge;
            if (lifeProgress >= shrinkStart) {
                float shrinkProgress = (lifeProgress - shrinkStart) / (1f - shrinkStart);
                size = initialSize * (1f - shrinkProgress);
                size = Math.max(size, 0f);
            }
        }

        age++;

        if (age >= maxAge || size <= 0f) {
            dead = true;
        }
    }

    public float[] getCurrentColor() {
        if (gradient == null) return new float[]{1f, 1f, 1f, 1f};
        float lifeProgress = age / maxAge;
        float[] color = gradient.evaluate(lifeProgress);

        if (flashFrequency > 0f) {
            float flash = (float)(Math.sin(age * flashFrequency * Math.PI * 2f / 20f) * 0.5f + 0.5f);
            float alpha = flashMinAlpha + (color[3] - flashMinAlpha) * flash;
            return new float[]{color[0], color[1], color[2], alpha};
        }

        return color;
    }

    public float getInterpolatedX(float tickDelta) {
        return prevX + (x - prevX) * tickDelta;
    }

    public float getInterpolatedY(float tickDelta) {
        return prevY + (y - prevY) * tickDelta;
    }

    public float getInterpolatedSize(float tickDelta) {
        return prevSize + (size - prevSize) * tickDelta;
    }
}
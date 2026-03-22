package com.peak.diversityScreen.impl.particle;

import com.peak.diversityScreen.impl.particle.force.ScreenParticleForce;
import com.peak.diversityScreen.impl.particle.manager.ScreenParticleManager;
import com.peak.diversityScreen.impl.particle.render.ColorGradient;
import com.peak.diversityScreen.impl.particle.render.ScreenParticleTexture;

import java.util.ArrayList;
import java.util.List;

public class ScreenParticleBuilder {
    float x, y;
    float vx, vy;
    float size = 4f;
    float maxAge = 20f;

    ColorGradient gradient;
    ScreenParticleTexture texture;
    boolean isRect = true;

    float flashFrequency = 0f;
    float flashMinAlpha  = 0f;

    float shrinkStart = -1f;

    boolean bounceElastic = false;
    float restitution     = 1f;

    final List<ScreenParticleForce> forces = new ArrayList<>();

    public static ScreenParticleBuilder create() {
        return new ScreenParticleBuilder();
    }

    public ScreenParticleBuilder pos(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ScreenParticleBuilder velocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
        return this;
    }

    public ScreenParticleBuilder size(float size) {
        this.size = size;
        return this;
    }

    public ScreenParticleBuilder lifetime(float ticks) {
        this.maxAge = ticks;
        return this;
    }

    public ScreenParticleBuilder gradient(ColorGradient gradient) {
        this.gradient = gradient;
        this.texture  = null;
        this.isRect   = false;
        return this;
    }

    public ScreenParticleBuilder rect(ColorGradient gradient) {
        this.gradient = gradient;
        this.texture  = null;
        this.isRect   = true;
        return this;
    }

    public ScreenParticleBuilder texture(ScreenParticleTexture texture) {
        this.texture  = texture;
        this.gradient = null;
        this.isRect   = false;
        return this;
    }

    public ScreenParticleBuilder flash(float frequency, float minAlpha) {
        this.flashFrequency = frequency;
        this.flashMinAlpha  = minAlpha;
        return this;
    }

    public ScreenParticleBuilder shrink(float startAtLifeProgress) {
        this.shrinkStart = startAtLifeProgress;
        return this;
    }

    public ScreenParticleBuilder bounce(float restitution) {
        this.bounceElastic = true;
        this.restitution   = restitution;
        return this;
    }

    public ScreenParticleBuilder force(ScreenParticleForce force) {
        this.forces.add(force);
        return this;
    }

    public ScreenParticle build() {
        return new ScreenParticle(this);
    }

    public ScreenParticle buildAndSpawn() {
        ScreenParticle particle = build();
        ScreenParticleManager.spawn(particle);
        return particle;
    }
}
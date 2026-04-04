package com.peak.diversityScreen.features.particle.force;

import com.peak.diversityScreen.features.particle.ScreenParticle;

public class DragForce implements ScreenParticleForce {

    private final float coefficient;

    public DragForce(float coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public void apply(ScreenParticle particle, float tickDelta) {
        particle.vx *= 1f - (coefficient * tickDelta);
        particle.vy *= 1f - (coefficient * tickDelta);
    }
}
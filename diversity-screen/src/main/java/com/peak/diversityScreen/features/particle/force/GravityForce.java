package com.peak.diversityScreen.features.particle.force;

import com.peak.diversityScreen.features.particle.ScreenParticle;

public class GravityForce implements ScreenParticleForce {

    private final float strength;

    public GravityForce(float strength) {
        this.strength = strength;
    }

    @Override
    public void apply(ScreenParticle particle, float tickDelta) {
        particle.vy += strength * tickDelta;
    }
}
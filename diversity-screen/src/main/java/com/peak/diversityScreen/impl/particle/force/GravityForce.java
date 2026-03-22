package com.peak.diversityScreen.impl.particle.force;

import com.peak.diversityScreen.impl.particle.ScreenParticle;

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
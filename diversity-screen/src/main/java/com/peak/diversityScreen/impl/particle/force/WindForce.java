package com.peak.diversityScreen.impl.particle.force;

import com.peak.diversityScreen.impl.particle.ScreenParticle;

public class WindForce implements ScreenParticleForce {

    private final float forceX;
    private final float forceY;

    public WindForce(float forceX, float forceY) {
        this.forceX = forceX;
        this.forceY = forceY;
    }

    @Override
    public void apply(ScreenParticle particle, float tickDelta) {
        particle.vx += forceX * tickDelta;
        particle.vy += forceY * tickDelta;
    }
}
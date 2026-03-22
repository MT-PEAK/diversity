package com.peak.diversityScreen.impl.particle.force;

import com.peak.diversityScreen.impl.particle.ScreenParticle;

@FunctionalInterface
public interface ScreenParticleForce {
    void apply(ScreenParticle particle, float tickDelta);
}
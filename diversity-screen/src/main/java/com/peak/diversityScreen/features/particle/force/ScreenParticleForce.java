package com.peak.diversityScreen.features.particle.force;

import com.peak.diversityScreen.features.particle.ScreenParticle;

@FunctionalInterface
public interface ScreenParticleForce {
    void apply(ScreenParticle particle, float tickDelta);
}
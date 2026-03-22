package com.peak.diversityScreen.impl.particle.emitter;

import com.peak.diversityScreen.impl.particle.ScreenParticleBuilder;

import java.util.function.Consumer;

public abstract class ScreenParticleEmitter {

    protected Consumer<ScreenParticleBuilder> builderConsumer;
    protected boolean stopped = false;

    public ScreenParticleEmitter(Consumer<ScreenParticleBuilder> builderConsumer) {
        this.builderConsumer = builderConsumer;
    }

    public abstract void tick();

    public boolean isStopped() {
        return stopped;
    }

    public void stop() {
        stopped = true;
    }

    protected ScreenParticleBuilder prepareBuilder(float x, float y) {
        ScreenParticleBuilder builder = ScreenParticleBuilder.create().pos(x, y);
        builderConsumer.accept(builder);
        return builder;
    }
}
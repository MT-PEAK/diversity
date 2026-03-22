package com.peak.diversityScreen.impl.particle.emitter;

import com.peak.diversityScreen.impl.particle.ScreenParticleBuilder;
import com.peak.diversityScreen.impl.particle.manager.ScreenParticleManager;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BurstEmitter extends ScreenParticleEmitter {

    private final Supplier<float[]> positionSupplier;
    private final int count;
    private boolean fired = false;

    public BurstEmitter(Supplier<float[]> positionSupplier, int count, Consumer<ScreenParticleBuilder> builderConsumer) {
        super(builderConsumer);
        this.positionSupplier = positionSupplier;
        this.count            = count;
    }

    @Override
    public void tick() {
        if (fired || stopped) return;

        float[] pos = positionSupplier.get();
        for (int i = 0; i < count; i++) {
            ScreenParticleBuilder builder = prepareBuilder(pos[0], pos[1]);
            ScreenParticleManager.spawn(builder.build());
        }

        fired   = true;
        stopped = true;
    }
}
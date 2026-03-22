package com.peak.diversityScreen.impl.particle.emitter;

import com.peak.diversityScreen.impl.particle.ScreenParticleBuilder;
import com.peak.diversityScreen.impl.particle.manager.ScreenParticleManager;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ContinuousEmitter extends ScreenParticleEmitter {

    private final Supplier<float[]> positionSupplier;
    private final int particlesPerTick;
    private final int maxParticles;
    private final int durationTicks;

    private int totalSpawned = 0;
    private int ticksAlive   = 0;

    public ContinuousEmitter(Supplier<float[]> positionSupplier, int particlesPerTick, int maxParticles, int durationTicks, Consumer<ScreenParticleBuilder> builderConsumer) {
        super(builderConsumer);
        this.positionSupplier  = positionSupplier;
        this.particlesPerTick  = particlesPerTick;
        this.maxParticles      = maxParticles;
        this.durationTicks     = durationTicks;
    }

    public static ContinuousEmitter forever(Supplier<float[]> positionSupplier, int particlesPerTick, Consumer<ScreenParticleBuilder> builderConsumer) {
        return new ContinuousEmitter(positionSupplier, particlesPerTick, -1, -1, builderConsumer);
    }

    public static ContinuousEmitter withDuration(Supplier<float[]> positionSupplier, int particlesPerTick, int durationTicks, Consumer<ScreenParticleBuilder> builderConsumer) {
        return new ContinuousEmitter(positionSupplier, particlesPerTick, -1, durationTicks, builderConsumer);
    }

    public static ContinuousEmitter withMaxParticles(Supplier<float[]> positionSupplier, int particlesPerTick, int maxParticles, Consumer<ScreenParticleBuilder> builderConsumer) {
        return new ContinuousEmitter(positionSupplier, particlesPerTick, maxParticles, -1, builderConsumer);
    }

    @Override
    public void tick() {
        if (stopped) return;

        float[] pos = positionSupplier.get();

        for (int i = 0; i < particlesPerTick; i++) {
            if (maxParticles != -1 && totalSpawned >= maxParticles) {
                stopped = true;
                return;
            }

            ScreenParticleBuilder builder = prepareBuilder(pos[0], pos[1]);
            ScreenParticleManager.spawn(builder.build());
            totalSpawned++;
        }

        ticksAlive++;

        if (durationTicks != -1 && ticksAlive >= durationTicks) {
            stopped = true;
        }
    }
}
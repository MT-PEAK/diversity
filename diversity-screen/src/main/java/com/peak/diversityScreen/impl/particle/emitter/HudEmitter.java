package com.peak.diversityScreen.impl.particle.emitter;

import com.peak.diversityScreen.impl.particle.ScreenParticleBuilder;
import com.peak.diversityScreen.impl.particle.attachment.HudElementAttachment;
import com.peak.diversityScreen.impl.particle.manager.ScreenParticleManager;

import java.util.function.Consumer;

public class HudEmitter extends ScreenParticleEmitter {

    private final HudElementAttachment attachment;
    private final int particlesPerTick;
    private final int maxParticles;
    private final int durationTicks;

    private int totalSpawned = 0;
    private int ticksAlive   = 0;

    public HudEmitter(HudElementAttachment attachment, int particlesPerTick, int maxParticles, int durationTicks, Consumer<ScreenParticleBuilder> builderConsumer) {
        super(builderConsumer);
        this.attachment       = attachment;
        this.particlesPerTick = particlesPerTick;
        this.maxParticles     = maxParticles;
        this.durationTicks    = durationTicks;
    }

    public static HudEmitter forever(HudElementAttachment attachment, int particlesPerTick, Consumer<ScreenParticleBuilder> builderConsumer) {
        return new HudEmitter(attachment, particlesPerTick, -1, -1, builderConsumer);
    }

    public static HudEmitter withDuration(HudElementAttachment attachment, int particlesPerTick, int durationTicks, Consumer<ScreenParticleBuilder> builderConsumer) {
        return new HudEmitter(attachment, particlesPerTick, -1, durationTicks, builderConsumer);
    }

    public static HudEmitter withMaxParticles(HudElementAttachment attachment, int particlesPerTick, int maxParticles, Consumer<ScreenParticleBuilder> builderConsumer) {
        return new HudEmitter(attachment, particlesPerTick, maxParticles, -1, builderConsumer);
    }

    @Override
    public void tick() {
        if (stopped) return;

        float x = attachment.getX();
        float y = attachment.getY();

        for (int i = 0; i < particlesPerTick; i++) {
            if (maxParticles != -1 && totalSpawned >= maxParticles) {
                stopped = true;
                return;
            }

            ScreenParticleBuilder builder = prepareBuilder(x, y);
            ScreenParticleManager.spawn(builder.build());
            totalSpawned++;
        }

        ticksAlive++;

        if (durationTicks != -1 && ticksAlive >= durationTicks) {
            stopped = true;
        }
    }
}
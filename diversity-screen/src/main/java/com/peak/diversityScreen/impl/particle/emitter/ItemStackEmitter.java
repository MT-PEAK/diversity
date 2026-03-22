package com.peak.diversityScreen.impl.particle.emitter;

import com.peak.diversityScreen.impl.particle.ScreenParticleBuilder;
import com.peak.diversityScreen.impl.particle.attachment.ItemStackAttachment;
import com.peak.diversityScreen.impl.particle.manager.ScreenParticleManager;

import java.util.function.Consumer;

public class ItemStackEmitter extends ScreenParticleEmitter {

    private final ItemStackAttachment attachment;
    private final int particlesPerTick;
    private final int maxParticles;
    private final int durationTicks;

    private int totalSpawned = 0;
    private int ticksAlive   = 0;

    public ItemStackEmitter(ItemStackAttachment attachment, int particlesPerTick, int maxParticles, int durationTicks, Consumer<ScreenParticleBuilder> builderConsumer) {
        super(builderConsumer);
        this.attachment       = attachment;
        this.particlesPerTick = particlesPerTick;
        this.maxParticles     = maxParticles;
        this.durationTicks    = durationTicks;
    }

    public static ItemStackEmitter forever(ItemStackAttachment attachment, int particlesPerTick, Consumer<ScreenParticleBuilder> builderConsumer) {
        return new ItemStackEmitter(attachment, particlesPerTick, -1, -1, builderConsumer);
    }

    public static ItemStackEmitter withDuration(ItemStackAttachment attachment, int particlesPerTick, int durationTicks, Consumer<ScreenParticleBuilder> builderConsumer) {
        return new ItemStackEmitter(attachment, particlesPerTick, -1, durationTicks, builderConsumer);
    }

    public static ItemStackEmitter withMaxParticles(ItemStackAttachment attachment, int particlesPerTick, int maxParticles, Consumer<ScreenParticleBuilder> builderConsumer) {
        return new ItemStackEmitter(attachment, particlesPerTick, maxParticles, -1, builderConsumer);
    }

    @Override
    public void tick() {
        if (stopped) return;

        float[] pos = attachment.getPosition();

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
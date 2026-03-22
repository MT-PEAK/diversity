package com.peak.diversityScreen.impl.particle.manager;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peak.diversityScreen.impl.particle.ScreenParticle;
import com.peak.diversityScreen.impl.particle.emitter.ScreenParticleEmitter;
import com.peak.diversityScreen.impl.particle.render.ScreenParticleRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ScreenParticleManager {

    private static final int DEFAULT_MAX_PARTICLES = 4096;

    private static final List<ScreenParticle> particles       = new ArrayList<>();
    private static final List<ScreenParticleEmitter> emitters = new ArrayList<>();
    private static final Deque<ScreenParticle> spawnQueue     = new ArrayDeque<>();

    private static int maxParticles = DEFAULT_MAX_PARTICLES;

    public static void spawn(ScreenParticle particle) {
        spawnQueue.add(particle);
    }

    public static void addEmitter(ScreenParticleEmitter emitter) {
        emitters.add(emitter);
    }

    public static void setMaxParticles(int max) {
        maxParticles = max;
    }

    public static void tick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.isPaused()) return;

        for (ScreenParticleEmitter emitter : emitters) {
            emitter.tick();
        }
        emitters.removeIf(ScreenParticleEmitter::isStopped);

        while (!spawnQueue.isEmpty()) {
            if (particles.size() >= maxParticles) {
                particles.remove(0);
            }
            particles.add(spawnQueue.poll());
        }

        for (ScreenParticle particle : particles) {
            particle.tick();
        }
        particles.removeIf(p -> p.dead);
    }

    public static void render(DrawContext context, float tickDelta) {
        if (particles.isEmpty()) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();

        context.getMatrices().push();

        for (ScreenParticle particle : particles) {
            ScreenParticleRenderer.render(particle, context, tickDelta);
        }

        context.getMatrices().push();

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void clear() {
        particles.clear();
        emitters.clear();
        spawnQueue.clear();
    }

    public static int getParticleCount() {
        return particles.size();
    }

    public static int getEmitterCount() {
        return emitters.size();
    }
}
package com.peak.diversityScreen.impl;

import com.peak.diversityScreen.impl.particle.emitter.ContinuousEmitter;
import com.peak.diversityScreen.impl.particle.force.BounceForce;
import com.peak.diversityScreen.impl.particle.force.DragForce;
import com.peak.diversityScreen.impl.particle.force.GravityForce;
import com.peak.diversityScreen.impl.particle.force.WindForce;
import com.peak.diversityScreen.impl.particle.manager.ScreenParticleManager;
import com.peak.diversityScreen.impl.particle.render.ColorGradient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class DiversityScreenClient implements ClientModInitializer {
    public void onInitializeClient() {
        System.out.println("this runs");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ScreenParticleManager.tick();
        });

        HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
            float tickDelta = tickDeltaManager.getTickDelta(true);
            ScreenParticleManager.render(context, tickDelta);
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ScreenParticleManager.clear();
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ColorGradient snowGradient = new ColorGradient()
                    .addStop(0.0f, 200f, 0.1f, 1.0f, 0.8f)
                    .addStop(1.0f, 210f, 0.2f, 0.9f, 0.0f);

            ScreenParticleManager.addEmitter(ContinuousEmitter.forever(
                    () -> new float[]{
                            (float)(Math.random() * client.getWindow().getScaledWidth()),
                            0f
                    },
                    2,
                    builder -> builder
                            .size(2f + (float)(Math.random() * 4f))
                            .lifetime(120f + (float)(Math.random() * 60f))
                            .velocity(
                                    -0.3f + (float)(Math.random() * 0.6f),
                                    0.5f + (float)(Math.random() * 0.5f)
                            )
                            .gradient(snowGradient)
                            .force(new GravityForce(1))
                            .force(new BounceForce(0.6f))
                            .bounce(0.3f)
            ));
        });
    }
}
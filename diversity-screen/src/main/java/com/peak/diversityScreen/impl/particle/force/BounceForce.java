package com.peak.diversityScreen.impl.particle.force;

import com.peak.diversityScreen.impl.particle.ScreenParticle;
import net.minecraft.client.MinecraftClient;

public class BounceForce implements ScreenParticleForce {

    private final float restitution;

    public BounceForce(float restitution) {
        this.restitution = restitution;
    }

    @Override
    public void apply(ScreenParticle particle, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int screenWidth  = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        float halfSize = particle.size * 0.5f;

        if (particle.x - halfSize <= 0) {
            particle.x  = halfSize;
            particle.vx = Math.abs(particle.vx) * restitution;
        } else if (particle.x + halfSize >= screenWidth) {
            particle.x  = screenWidth - halfSize;
            particle.vx = -Math.abs(particle.vx) * restitution;
        }

        if (particle.y - halfSize <= 0) {
            particle.y  = halfSize;
            particle.vy = Math.abs(particle.vy) * restitution;
        } else if (particle.y + halfSize >= screenHeight) {
            particle.y  = screenHeight - halfSize;
            particle.vy = -Math.abs(particle.vy) * restitution;
        }
    }
}
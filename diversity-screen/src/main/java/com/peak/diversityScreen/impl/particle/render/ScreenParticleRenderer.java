package com.peak.diversityScreen.impl.particle.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peak.diversityScreen.impl.particle.ScreenParticle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;

public class ScreenParticleRenderer {

    public static void render(ScreenParticle particle, DrawContext context, float tickDelta) {
        float x = particle.getInterpolatedX(tickDelta);
        float y = particle.getInterpolatedY(tickDelta);
        float size = particle.getInterpolatedSize(tickDelta);

        float[] color = particle.getCurrentColor();
        float r = color[0];
        float g = color[1];
        float b = color[2];
        float a = color[3];

        if (a <= 0f || size <= 0f) return;

        if (particle.isRect) {
            renderRect(context, x, y, size, r, g, b, a);
        } else if (particle.texture != null) {
            renderTexture(context, particle, x, y, size, a, tickDelta);
        } else {
            renderQuad(x, y, size, r, g, b, a);
        }
    }

    private static void renderRect(DrawContext context, float x, float y, float size, float r, float g, float b, float a) {
        int half  = (int)(size * 0.5f);
        int color = toARGB(r, g, b, a);
        context.fill((int)x - half, (int)y - half, (int)x + half, (int)y + half, color);
    }

    private static void renderTexture(DrawContext context, ScreenParticle particle, float x, float y, float size, float a, float tickDelta) {
        ScreenParticleTexture tex   = particle.texture;
        int frame = tex.getCurrentFrame(particle.age, particle.maxAge);
        float[] uv = tex.getFrameUV(frame);
        int half = (int)(size * 0.5f);
        int alpha = (int)(a * 255);

        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        RenderSystem.setShaderTexture(0, tex.texture);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

        int x0 = (int)x - half;
        int y0 = (int)y - half;
        int x1 = (int)x + half;
        int y1 = (int)y + half;

        buffer.vertex(x0, y1, 0).texture(uv[0], uv[3]).color(255, 255, 255, alpha);
        buffer.vertex(x1, y1, 0).texture(uv[2], uv[3]).color(255, 255, 255, alpha);
        buffer.vertex(x1, y0, 0).texture(uv[2], uv[1]).color(255, 255, 255, alpha);
        buffer.vertex(x0, y0, 0).texture(uv[0], uv[1]).color(255, 255, 255, alpha);

        BufferRenderer.drawWithGlobalProgram(buffer.end());

        RenderSystem.disableBlend();
    }

    private static void renderQuad(float x, float y, float size, float r, float g, float b, float a) {
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        float half = size * 0.5f;
        int ir = (int)(r * 255);
        int ig = (int)(g * 255);
        int ib = (int)(b * 255);
        int ia = (int)(a * 255);

        buffer.vertex(x - half, y + half, 0).color(ir, ig, ib, ia);
        buffer.vertex(x + half, y + half, 0).color(ir, ig, ib, ia);
        buffer.vertex(x + half, y - half, 0).color(ir, ig, ib, ia);
        buffer.vertex(x - half, y - half, 0).color(ir, ig, ib, ia);

        BufferRenderer.drawWithGlobalProgram(buffer.end());

        RenderSystem.disableBlend();
    }

    private static int toARGB(float r, float g, float b, float a) {
        return ((int)(a * 255) << 24)
             | ((int)(r * 255) << 16)
             | ((int)(g * 255) << 8)
             |  (int)(b * 255);
    }
}
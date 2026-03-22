//package com.peak.diversityScreen.client.event;
//
//import com.peak.diversityScreen.util.Easing;
//import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.DrawContext;
//import net.minecraft.client.network.ClientPlayerEntity;
//import net.minecraft.client.render.RenderTickCounter;
//import net.minecraft.util.math.MathHelper;
//
//public class FlashRendererEvent implements HudRenderCallback {
//
//    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
//        ClientPlayerEntity target = MinecraftClient.getInstance().player;
//
//        if (target != null) {
//            Flasher flash = Flasher.KEY.get(target);
//
//            int durationRemaining = flash.durationRemaining;
//            int color = flash.color;
//            int totalDuration = flash.totalDuration;
//            Easing easing = flash.easing;
//
//            if (durationRemaining > 0 && totalDuration > 0) {
//                double t = (double) durationRemaining / totalDuration;
//                double eased = easing.getFunction().apply(t).doubleValue();
//
//                int alphaI = (int) (MathHelper.clamp(eased, 0.0, 1.0) * 255);
//
//                int newColor = (alphaI << 24) | (color & 0x00FFFFFF);
//                drawContext.fill(0, 0, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight(), newColor);
//            }
//        }
//    }
//}
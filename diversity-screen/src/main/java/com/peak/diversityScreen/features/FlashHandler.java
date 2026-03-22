//package com.peak.diversityScreen.features;
//
//import dev.peak.diversity.v1.impl.cca.entity.Flasher;
//import dev.peak.diversity.v1.impl.util.Easing;
//import net.minecraft.entity.player.PlayerEntity;
//
//public class FlashHandler {
//
//    public static void sendFlash(PlayerEntity target, int color) {
//        Flasher flash = Flasher.KEY.get(target);
//        flash.color = color;
//        flash.durationRemaining = 20;
//        flash.totalDuration = flash.durationRemaining;
//        flash.holdTicks = 0;
//
//        flash.sync();
//    }
//
//    public static void sendFlash(PlayerEntity target, int color, Easing easing) {
//        Flasher flash = Flasher.KEY.get(target);
//        flash.color = color;
//        flash.durationRemaining = 20;
//        flash.totalDuration = flash.durationRemaining;
//        flash.easing = easing;
//        flash.holdTicks = 0;
//
//        flash.sync();
//    }
//
//    public static void sendFlash(PlayerEntity target, int color, int holdTicks) {
//        Flasher flash = Flasher.KEY.get(target);
//        flash.color = color;
//        flash.durationRemaining = 20;
//        flash.totalDuration = flash.durationRemaining;
//        flash.holdTicks = holdTicks;
//
//        flash.sync();
//    }
//
//    public static void sendFlash(PlayerEntity target, int color, Easing easing, int holdTicks) {
//        Flasher flash = Flasher.KEY.get(target);
//        flash.color = color;
//        flash.durationRemaining = 20;
//        flash.totalDuration = flash.durationRemaining;
//        flash.easing = easing;
//        flash.holdTicks = holdTicks;
//
//        flash.sync();
//    }
//}

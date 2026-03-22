package com.peak.diversityCore.features;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class WorldEffectsManager {
    public static void playGlobalSoundEvent(World world, SoundEvent soundEvent, SoundCategory category, float volume, float pitch) {
        world.getPlayers().forEach(player ->
                player.playSoundToPlayer(soundEvent, category, volume, pitch)
        );
    }
}

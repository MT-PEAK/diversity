package com.peak.diversityItem.features.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface TotemItem {
    /// Requires setting entity health to properly not kill the victim
    void onDeathEffects(LivingEntity living, LivingEntity source, ItemStack stack, World world);
}

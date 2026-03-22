package com.peak.diversityItem.features;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface TotemItem {
    void onDeathEffects(LivingEntity living, LivingEntity source, ItemStack stack, World world);
}

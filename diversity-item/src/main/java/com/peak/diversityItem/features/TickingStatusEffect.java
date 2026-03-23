package com.peak.diversityItem.features;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class TickingStatusEffect extends StatusEffect {
    private final int interval;

    protected TickingStatusEffect(StatusEffectCategory category, int color, int interval) {
        super(category, color);
        this.interval = interval;
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = this.interval;
        return duration % i == 0;
    }
}

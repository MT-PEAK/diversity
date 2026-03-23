package com.peak.diversityItem.features.interfaces;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface DescriptionItem {
    int getMaxLines(ItemStack stack);
}

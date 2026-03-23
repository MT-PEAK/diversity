package com.peak.diversityItem.impl.event;

import com.peak.diversityItem.features.interfaces.DescriptionItem;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Experimental
public class DescriptionTooltipEvent implements ItemTooltipCallback {
    public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> lines) {
        if (stack.getItem() instanceof DescriptionItem item) {
            for (int i = 0; i < item.getMaxLines(stack); i++) {
                lines.add(Text.translatable("item." + stack.getItem().toString() + ".desc_" + i));
            }
        }
    }
}

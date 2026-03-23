package com.peak.diversityItem.impl;

import com.mojang.logging.LogUtils;
import com.peak.diversityCore.impl.DiversityCore;
import com.peak.diversityItem.impl.event.DescriptionTooltipEvent;
import com.peak.diversityItem.test.item.TestItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.slf4j.Logger;

public class DiversityItem implements ModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Item TEST_ITEM = new TestItem(new Item.Settings());

    public void onInitialize() {
        Registry.register(Registries.ITEM, DiversityCore.id("test_item"), TEST_ITEM);

        ItemTooltipCallback.EVENT.register(new DescriptionTooltipEvent());
    }
}

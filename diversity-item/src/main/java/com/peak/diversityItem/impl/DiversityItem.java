package com.peak.diversityItem.impl;

import com.peak.diversityItem.test.index.TestModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiversityItem implements ModInitializer {
    public static final String MOD_ID = "diversity-item-test";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        TestModItems.init();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}

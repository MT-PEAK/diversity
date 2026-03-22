package com.peak.diversityItemTest.impl;

import com.peak.diversityItemTest.impl.index.TestModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMod implements ModInitializer {
    public static final String MOD_ID = "diversity-item-test";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        TestModItems.init();

        LOGGER.info("Test Mod has been started [ITEM]");
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}

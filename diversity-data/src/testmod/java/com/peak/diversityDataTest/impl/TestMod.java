package com.peak.diversityDataTest.impl;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMod implements ModInitializer {
    public static final String MOD_ID = "diversity-data-test";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        LOGGER.info("Test Mod has been started [DATA]");
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}

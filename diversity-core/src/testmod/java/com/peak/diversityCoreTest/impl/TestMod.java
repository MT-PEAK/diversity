package com.peak.diversityCoreTest.impl;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMod implements ModInitializer {
    public static final String MOD_ID = "diversity-core-test";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        LOGGER.info("Test Mod has been started [CORE]");
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}

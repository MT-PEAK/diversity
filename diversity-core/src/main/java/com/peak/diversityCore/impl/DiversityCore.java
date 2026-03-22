package com.peak.diversityCore.impl;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class DiversityCore implements ModInitializer {
    public static final String MOD_ID = "diversity";
    public static final Logger LOGGER = LogUtils.getLogger();

    public void onInitialize() {
        //
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}

package com.peak.diversityData.impl;

import com.peak.diversityData.features.Dispatcher;
import com.peak.diversityData.features.attachment.AttachmentData;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class DiversityData implements ModInitializer {
    public static final Map<Identifier, AttachmentData> dataMap = new HashMap<>();

    public void onInitialize() {
        Dispatcher.getOrRegister(Identifier.of("diversity", "test"), new AttachmentData(PlayerEntity.class, new TestAttachment()));
    }
}

package com.peak.diversityData.impl;

import com.mojang.logging.LogUtils;
import com.peak.diversityCore.impl.DiversityCore;
import com.peak.diversityData.features.Dispatcher;
import com.peak.diversityData.features.attachment.AttachmentData;
import com.peak.diversityData.impl.command.AttachmentsCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DiversityData implements ModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Map<Identifier, AttachmentData> dataMap = new HashMap<>();

    public void onInitialize() {
        Dispatcher.getOrRegister(DiversityCore.id("test"), new AttachmentData(PlayerEntity.class, new TestAttachment()));

        CommandRegistrationCallback.EVENT.register(AttachmentsCommand::register);
    }
}

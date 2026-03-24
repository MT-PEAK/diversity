package com.peak.diversityData.impl;

import com.mojang.logging.LogUtils;
import com.peak.diversityCore.impl.DiversityCore;
import com.peak.diversityData.features.Dispatcher;
import com.peak.diversityData.features.Fetcher;
import com.peak.diversityData.features.attachment.Attachable;
import com.peak.diversityData.features.attachment.AttachmentData;
import com.peak.diversityData.features.attachment.AttachmentHolder;
import com.peak.diversityData.impl.command.AttachmentsCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DiversityData implements ModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Identifier TEST_ID = DiversityCore.id("test_attachment");
    public static final AttachmentData TEST_DATA = new AttachmentData(Entity.class, new TestAttachment());

    public static final Map<Identifier, AttachmentData> dataMap = new HashMap<>();
    public static final Map<Class<? extends Attachable>, AttachmentHolder> attachableHolders = new HashMap<>();

    public void onInitialize() {
        Dispatcher.getOrRegister(TEST_ID, TEST_DATA);

        CommandRegistrationCallback.EVENT.register(AttachmentsCommand::register);
    }

    public static <T extends Attachable> AttachmentHolder createHolder(T object) {
        Class<? extends Attachable> clazz = object.getClass();

        return Objects.requireNonNullElseGet(
                attachableHolders.get(clazz),
                () -> getAttachment(clazz)
        );
    }

    private static synchronized AttachmentHolder getAttachment(Class<? extends Attachable> clazz) {
        AttachmentHolder original = attachableHolders.get(clazz);
        if (original != null) return original;

        AttachmentHolder holder = new AttachmentHolder();
        holder.attachments.putAll(Fetcher.getAttachmentMapFor(clazz));

        attachableHolders.put(clazz, holder);
        return holder;
    }
}

/**
 * Component reads and writes data. Able to be extended and used as a base (Attachment)
 * ComponentAccess lets you get component keys and lets you sync them. It is also implemented by ComponentProvider (Attachable)
 * ComponentContainer is unmodifiable. Holds component keys registered by ComponentInitializers like the entity and world ones for the current ComponentAccess (AttachmentHolder)
 * ComponentContainer.Factory A factory for ComponentContainer supplying an object that implements ComponentAccess
 * ComponentFactory Instantiates a Component for the given provider
 * ComponentKey A key for retrieving a Component from component providers
 */

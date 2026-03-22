package com.peak.diversityData.features.attachment;

import net.minecraft.util.Identifier;

import java.util.Optional;

public interface Attachable {
    default Optional<Attachment> getAttachment(Identifier identifier) {
        throw new AssertionError();
    }

    default AttachmentHolder getAttachmentHolder() {
        throw new AssertionError();
    }
}

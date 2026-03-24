package com.peak.diversityData.features.attachment;

import net.minecraft.util.Identifier;

import java.util.Optional;

public interface Attachable {
    default Optional<Attachment> diversity$getAttachment(Identifier identifier) {
        return this.diversity$getAttachmentHolder().getAttachment(identifier);
    }

    default AttachmentHolder diversity$getAttachmentHolder() {
        return null;
    }
}

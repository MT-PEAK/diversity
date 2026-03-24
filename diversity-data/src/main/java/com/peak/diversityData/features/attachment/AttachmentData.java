package com.peak.diversityData.features.attachment;

public record AttachmentData(Class<? extends Attachable> clazz, Attachment attachment) {
    public boolean shouldApply(Object object) {
        return object.getClass().isAssignableFrom(this.clazz);
    }
}

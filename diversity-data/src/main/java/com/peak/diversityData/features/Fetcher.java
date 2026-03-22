package com.peak.diversityData.features;

import com.peak.diversityData.features.attachment.Attachment;
import com.peak.diversityData.features.attachment.AttachmentData;
import com.peak.diversityData.impl.DiversityData;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fetcher {
    public static List<Attachment> getAttachments(Object object) {
        return getAttachments(object, Attachment.class);
    }

    public static <T extends Attachment> List<T> getAttachments(Object object, Class<T> check) {
        List<T> attachments = new ArrayList<>();
        getAll().stream()
                .filter(data -> data.shouldApply(object) && data.clazz().isInstance(check))
                .forEach(data -> attachments.add((T) data.attachment()));

        return attachments;
    }

    public static AttachmentData get(Identifier attachmentId) {
        return DiversityData.dataMap.get(attachmentId);
    }

    public static List<AttachmentData> getAll() {
        List<AttachmentData> dataList = new ArrayList<>();
        DiversityData.dataMap.keySet().forEach(identifier -> dataList.add(get(identifier)));
        return dataList;
    }

    public static List<Attachment> getAttachmentsFor(Object object) {
        List<Attachment> attachments = new ArrayList<>();
        DiversityData.dataMap.forEach((id, attachment) -> {
            if (attachment.shouldApply(object)) {
                attachments.add(attachment.attachment());
            }
        });

        return attachments;
    }

    public static Map<Identifier, Attachment> getAttachmentMapFor(Object object) {
        Map<Identifier, Attachment> attachmentMap = new HashMap<>();
        DiversityData.dataMap.forEach((id, attachment) -> {
            if (attachment.shouldApply(object)) {
                attachmentMap.put(id, attachment.attachment());
            }
        });

        return attachmentMap;
    }
}

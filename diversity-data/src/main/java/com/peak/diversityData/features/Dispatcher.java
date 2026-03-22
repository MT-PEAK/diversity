package com.peak.diversityData.features;

import com.peak.diversityData.features.attachment.AttachmentData;
import com.peak.diversityData.impl.DiversityData;
import net.minecraft.util.Identifier;

public class Dispatcher {
    public static AttachmentData getOrRegister(Identifier identifier, AttachmentData data) {
        if (DiversityData.dataMap.containsKey(identifier)) return DiversityData.dataMap.get(identifier);
        return DiversityData.dataMap.put(identifier, data);
    }
}

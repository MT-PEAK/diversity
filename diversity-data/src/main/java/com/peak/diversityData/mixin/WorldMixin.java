package com.peak.diversityData.mixin;

import com.peak.diversityData.features.attachment.Attachable;
import com.peak.diversityData.features.attachment.AttachmentHolder;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(World.class)
public abstract class WorldMixin implements Attachable {
    @Unique private final AttachmentHolder holder = new AttachmentHolder();

    @Override
    public AttachmentHolder diversity$getAttachmentHolder() {
        return this.holder;
    }
}

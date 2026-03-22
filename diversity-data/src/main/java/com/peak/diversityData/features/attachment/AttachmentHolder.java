package com.peak.diversityData.features.attachment;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.*;

public class AttachmentHolder {
    public final Map<Identifier, Attachment> attachments = new HashMap<>();

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.getAttachments().forEach(attachment -> attachment.writeNbt(nbt, registries));
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.getAttachments().forEach(attachment -> attachment.readNbt(nbt, registries));
    }

    public Optional<Attachment> getAttachment(Identifier identifier) {
        return this.attachments.containsKey(identifier) ? Optional.of(this.attachments.get(identifier)) : Optional.empty();
    }

    public List<Attachment> getAttachments() {
        List<Attachment> attachments = new ArrayList<>();
        this.attachments.forEach((id, attachment) -> {
            attachments.add(attachment);
        });

        return attachments;
    }

    public List<Identifier> getAttachmentIds() {
        List<Identifier> identifiers = new ArrayList<>();
        this.attachments.forEach((id, attachment) -> {
            identifiers.add(id);
        });

        return identifiers;
    }
}

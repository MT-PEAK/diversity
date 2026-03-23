package com.peak.diversityData.features.attachment;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.*;

public class AttachmentHolder {
    public final Map<Identifier, Attachment> attachments = new HashMap<>();

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.attachments.forEach((id, attachment) -> {
            NbtCompound compound = new NbtCompound();
            attachment.writeNbt(compound, registries);
            nbt.put(id.toString(), compound);
        });
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.attachments.forEach((id, attachment) -> {
            NbtCompound compound = nbt.getCompound(id.toString());
            attachment.readNbt(compound, registries);
        });
    }

    public Optional<Attachment> getAttachment(Identifier identifier) {
        return getAttachment(identifier, Attachment.class);
    }

    public <T extends Attachment> Optional<T> getAttachment(Identifier identifier, Class<T> clazz) {
        return this.attachments.containsKey(identifier)
                && this.attachments.get(identifier).getClass().isInstance(clazz)
                ? Optional.of((T)this.attachments.get(identifier))
                : Optional.empty();
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

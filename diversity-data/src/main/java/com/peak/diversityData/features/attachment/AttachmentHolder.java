package com.peak.diversityData.features.attachment;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.*;

@SuppressWarnings("unchecked")
public class AttachmentHolder {
    public final Map<Identifier, Attachment> attachments = new HashMap<>();

    /* Nbt */
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
            this.update(id, attachment);
        });
    }

    public void update(Identifier id, Attachment attachment) {
        this.attachments.replace(id, this.attachments.get(id), attachment);
    }

    /* Getting */
    public Optional<Attachment> getAttachment(Identifier identifier) {
        return this.getAttachment(identifier, Attachment.class);
    }

    public <A extends Attachment> Optional<A> getAttachment(Identifier identifier, Class<A> clazz) {
        Attachment attachment = this.attachments.getOrDefault(identifier, null);
        if (attachment.getClass().isAssignableFrom(clazz)) {
            return Optional.of((A)attachment);
        } else {
            return Optional.empty();
        }
    }

    public List<Attachment> getAttachments() {
        return getAttachments(Attachment.class);
    }

    public <A extends Attachment> List<A> getAttachments(Class<A> clazz) {
        List<A> attachments = new ArrayList<>();
        this.attachments.forEach((id, attachment) -> {
            if (attachment.getClass().isAssignableFrom(clazz)) {
                attachments.add((A)attachment);
            }
        });

        return attachments;
    }

    /* Util */
    public List<Identifier> getAttachmentIds() {
        List<Identifier> identifiers = new ArrayList<>();
        this.attachments.forEach((id, attachment) -> {
            identifiers.add(id);
        });

        return identifiers;
    }
}

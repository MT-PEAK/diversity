package com.peak.diversityData.mixin;

import com.peak.diversityData.features.attachment.Attachable;
import com.peak.diversityData.features.attachment.AttachmentHolder;
import com.peak.diversityData.impl.DiversityData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Attachable {
    @Unique private AttachmentHolder holder;
    @Shadow public abstract DynamicRegistryManager getRegistryManager();

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void buildAttachments(EntityType<?> type, World world, CallbackInfo ci) {
        this.holder = DiversityData.createHolder((Entity)(Object)this);
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeBeforeReturn(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound compound = new NbtCompound();
        this.holder.writeToNbt(compound, this.getRegistryManager());
        nbt.put("Attachments", compound);
    }

    @Inject(
            method = "readNbt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void readAfterCustomData(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound compound = nbt.getCompound("Attachments");
        this.holder.readFromNbt(compound, this.getRegistryManager());
    }

    @Override
    public AttachmentHolder diversity$getAttachmentHolder() {
        return this.holder;
    }
}

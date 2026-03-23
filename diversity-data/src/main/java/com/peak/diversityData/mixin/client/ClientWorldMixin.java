package com.peak.diversityData.mixin.client;

import com.peak.diversityData.features.Fetcher;
import com.peak.diversityData.features.attachment.TickingAttachment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Inject(
            method = "tickEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;tick()V",
                    shift = At.Shift.AFTER
            )
    )
    private void tickEntity(Entity entity, CallbackInfo ci) {
        Fetcher.getAttachments(entity, TickingAttachment.class).forEach(TickingAttachment::clientTick);
    }

    @Inject(
            method = "tickPassenger",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;tickRiding()V",
                    shift = At.Shift.AFTER
            )
    )
    private void tickPassenger(Entity entity, Entity passenger, CallbackInfo ci) {
        Fetcher.getAttachments(passenger, TickingAttachment.class).forEach(TickingAttachment::clientTick);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        Fetcher.getAttachments(this, TickingAttachment.class).forEach(TickingAttachment::clientTick);
    }
}

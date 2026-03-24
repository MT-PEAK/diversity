package com.peak.diversityData.mixin;

import com.peak.diversityData.features.Fetcher;
import com.peak.diversityData.features.attachment.tick.TickingAttachment;
import net.minecraft.entity.Entity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {
    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }

    @Inject(
            method = "tickEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;tick()V",
                    shift = At.Shift.AFTER
            )
    )
    private void tickEntity(Entity entity, CallbackInfo ci) {
        Fetcher.getAttachments(entity, TickingAttachment.class).forEach(TickingAttachment::serverTick);
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
        Fetcher.getAttachments(passenger, TickingAttachment.class).forEach(TickingAttachment::serverTick);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        Fetcher.getAttachments(this, TickingAttachment.class).forEach(TickingAttachment::serverTick);
    }
}

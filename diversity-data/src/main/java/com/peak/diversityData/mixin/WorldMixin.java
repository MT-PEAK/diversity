package com.peak.diversityData.mixin;

import com.peak.diversityData.features.attachment.Attachable;
import com.peak.diversityData.features.attachment.AttachmentHolder;
import com.peak.diversityData.impl.DiversityData;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(World.class)
public abstract class WorldMixin implements Attachable {
    @Unique private AttachmentHolder holder;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void buildAttachments(
            MutableWorldProperties properties,
            RegistryKey<World> registryRef,
            DynamicRegistryManager registryManager,
            RegistryEntry<DimensionType> dimensionEntry,
            Supplier<Profiler> profiler,
            boolean isClient,
            boolean debugWorld,
            long biomeAccess,
            int maxChainedNeighborUpdates,
            CallbackInfo ci
    ) {
        this.holder = DiversityData.createHolder((World)(Object)this);
    }

    @Override
    public AttachmentHolder diversity$getAttachmentHolder() {
        return this.holder;
    }
}

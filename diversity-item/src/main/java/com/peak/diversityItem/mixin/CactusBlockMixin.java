package com.peak.diversityItem.mixin;

import com.peak.diversityItem.features.interfaces.ItemWithEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin extends Block {
    public CactusBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onEntityCollision", at = @At(value = "HEAD"), cancellable = true)
    private void diversity$indestructibleItem(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity instanceof ItemEntity itemEntity) {
            if (itemEntity.getStack().getItem() instanceof ItemWithEffects effects) {
                if (effects.isIndestructible(itemEntity.getStack())) {
                    ci.cancel();
                }
            }
        }
    }
}

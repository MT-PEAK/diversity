package com.peak.diversityItem.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.peak.diversityItem.features.interfaces.ItemWithEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements Ownable {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyReturnValue(
            method = "isFireImmune",
            at = @At(
                    value = "RETURN"
            )
    )
    private boolean diversity$indestructibleItem(boolean original) {
        ItemEntity entity = (ItemEntity) (Object) this;

        if (entity.getStack().getItem() instanceof ItemWithEffects effects) {
            if (effects.isIndestructible(entity.getStack())) {
                return true;
            }
        }
        return original;
    }
}

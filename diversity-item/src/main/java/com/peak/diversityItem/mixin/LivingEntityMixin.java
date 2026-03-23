package com.peak.diversityItem.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.peak.diversityItem.features.interfaces.ItemWithEffects;
import com.peak.diversityItem.features.interfaces.TotemItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @WrapOperation(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"
            )
    )
    private void diversity$customKillSourceItem(LivingEntity instance, DamageSource source, float amount, Operation<Void> original) {
        if (source.getAttacker() instanceof PlayerEntity player && player.getMainHandStack().getItem() instanceof ItemWithEffects effects) {
            original.call(instance, effects.getKillDamageSource(instance, player), amount);
            return;
        }

        original.call(instance, source, amount);
    }

    @Inject(
            method = "tryUseTotem",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void diversity$totemItem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity)(Object)this;

        if (source.getAttacker() instanceof PlayerEntity player) {
            if (player.getMainHandStack().getItem() instanceof ItemWithEffects effects) {
                effects.getKillEffect(player, living, player.getMainHandStack(), player.getWorld());
            }
        }

        if (source.getAttacker() instanceof LivingEntity attacker) {
            if (living.getOffHandStack().getItem() instanceof TotemItem totemItem) { // yes there is probably a much better way of doing this BUT i don't know how to do this rn
                totemItem.onDeathEffects(living, attacker, living.getOffHandStack(), living.getWorld());
                cir.setReturnValue(true);
            }

            if (living.getMainHandStack().getItem() instanceof TotemItem totemItem) {
                totemItem.onDeathEffects(living, attacker, living.getOffHandStack(), living.getWorld());
                cir.setReturnValue(true);
            }
        }
    }
}

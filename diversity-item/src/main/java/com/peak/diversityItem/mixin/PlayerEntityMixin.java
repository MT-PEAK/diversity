package com.peak.diversityItem.mixin;

import com.peak.diversityItem.features.ItemWithEffects;
import com.peak.diversityItem.features.WeaponItem;
import com.peak.diversityItem.impl.util.ItemUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract ItemCooldownManager getItemCooldownManager();
    @Shadow public abstract void spawnSweepAttackParticles();
    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "attack",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;addCritParticles(Lnet/minecraft/entity/Entity;)V"
            )
    )
    private void diversity$customCritEffectItem(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        ItemStack stack = player.getMainHandStack();

        if (target instanceof LivingEntity living) {
            if (stack.getItem() instanceof ItemWithEffects effects) {
                effects.getCritEffect(player, living, player.getWorld(), stack);
            }
        }
    }

    @Inject(
            method = "takeShieldHit",
            at = @At(
                    value = "HEAD"
            )
    )
    private void diversity$shieldBreaker(LivingEntity attacker, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        ItemStack stack = attacker.getMainHandStack();

        if (stack.getItem() instanceof ItemWithEffects effects) {
            this.getItemCooldownManager().set(Items.SHIELD, ItemUtil.secondsToTicks(effects.getShieldBrokenSeconds(stack, player, attacker)));
            this.clearActiveItem();
            this.getWorld().sendEntityStatus(this, EntityStatuses.BREAK_SHIELD);
        }
    }

    @Inject(
            method = "attack",
            at = @At(
                    value = "TAIL"
            )
    )
    private void diversity$weaponItemSpawnSweepParticles(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if (player.getMainHandStack().getItem() instanceof WeaponItem item) {
            if (item.isSword) {
                if (this.getAttackCooldownProgress(0.5f) > 0.9f) {
                    this.spawnSweepAttackParticles();
                }
            }
        }
    }
}

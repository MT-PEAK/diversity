package com.peak.diversityItem.features;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ItemWithEffects {
    /* Void */
    default void getCritEffect(PlayerEntity player, LivingEntity target, World world, ItemStack stack) {}
    default void getKillEffect(PlayerEntity player, LivingEntity target, ItemStack stack, World world) {}

    /* Base */
    default int getShieldBrokenSeconds(ItemStack stack, PlayerEntity player, LivingEntity target) {return 0;}

    /* Other */
    default BipedEntityModel.ArmPose getArmPose(ItemStack stack, PlayerEntity player) {return BipedEntityModel.ArmPose.ITEM;}
    default DamageSource getKillDamageSource(LivingEntity living, PlayerEntity player) {return living.getDamageSources().genericKill();}
}

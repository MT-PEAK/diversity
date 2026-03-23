package com.peak.diversityItem.features.interfaces;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public interface ItemWithEffects {
    /* Void */
    default void getCritEffect(PlayerEntity player, LivingEntity target, World world, ItemStack stack) {}
    default void getKillEffect(PlayerEntity player, LivingEntity target, ItemStack stack, World world) {}

    /* Base */
    default int getShieldBrokenSeconds(ItemStack stack, PlayerEntity player, LivingEntity target) {return 0;}

    default int getItemBarState(float value, int max) {
        return Math.round(value / max * 13);
    }

    default boolean isIndestructible(ItemStack stack) {return false;}

    /* Other */
    default BipedEntityModel.ArmPose getArmPose(ItemStack stack, PlayerEntity player) {return BipedEntityModel.ArmPose.ITEM;}
    default DamageSource getKillDamageSource(LivingEntity living, PlayerEntity player) {return living.getDamageSources().genericKill();}
    default Identifier getRiptideTexture(ItemStack stack, PlayerEntity player) {return Identifier.ofVanilla("textures/entity/trident_riptide.png");}
}

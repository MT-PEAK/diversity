package com.peak.diversityItem.test.item;

import com.peak.diversityItem.features.ItemWithEffects;
import com.peak.diversityItem.features.WeaponItem;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TestItem extends WeaponItem implements ItemWithEffects {
    public TestItem(Settings settings) {
        super(settings, 70.0f, -2.1f, 10.0f, true);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public BipedEntityModel.ArmPose getArmPose(ItemStack stack, PlayerEntity player) {
        return BipedEntityModel.ArmPose.THROW_SPEAR;
    }

    public void getCritEffect(PlayerEntity player, LivingEntity target, World world, ItemStack stack) {
        player.setVelocity(0, 0.5f, 0);
        player.velocityModified = true;
    }
}

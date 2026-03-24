package com.peak.diversityItem.test.item;

import com.peak.diversityItem.features.WeaponItem;
import com.peak.diversityItem.features.interfaces.DescriptionItem;
import com.peak.diversityItem.features.interfaces.ItemWithEffects;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/// TODO: remove when mod is released
/// yeah no shit, test mod doesn't run :sob:
public class TestItem extends WeaponItem implements ItemWithEffects, DescriptionItem {
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

    public int getItemBarStep(ItemStack stack) {
        return getItemBarState(2, 4);
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    public int getMaxLines(ItemStack stack) {
        return 4;
    }

    public boolean isIndestructible(ItemStack stack) {
        return true;
    }

    public boolean isUndroppable(ItemStack stack) {
        return true;
    }
}

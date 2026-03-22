package com.peak.diversityItemTest.impl.item;

import com.peak.diversityItem.features.ItemWithEffects;
import com.peak.diversityItem.features.WeaponItem;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TestItem extends WeaponItem implements ItemWithEffects {
    public TestItem(Item.Settings settings) {
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

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world instanceof ServerWorld serverWorld) {
            Vec3d pos = user.getPos();

//            serverWorld.spawnParticles(
//                    new ShockwaveParticleEffect(
//                            0xFFffffff,
//                            9,
//                            Direction.Axis.Y
//                    ),
//                    pos.x, pos.y + 0.5f, pos.z,
//                    1,
//                    0.0, 0.0, 0.0,
//                    0.1
//            );
        }
        return super.use(world, user, hand);
    }
}

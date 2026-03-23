package com.peak.diversityItem.impl.util;

import com.mojang.serialization.Codec;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Predicate;

import static net.minecraft.util.dynamic.Codecs.VECTOR_3F;
import static net.minecraft.util.math.ColorHelper.Abgr.*;
import static net.minecraft.util.math.ColorHelper.Argb.fromFloats;

public class ItemUtil {
    public static final Codec<Integer> RGB = Codec.withAlternative(Codec.INT, VECTOR_3F,
            vec3f -> fromFloats(1.0F, vec3f.x(), vec3f.y(), vec3f.z()));

    public static Vector3f toVector(int rgb) {
        float f = getRed(rgb) / 255.0F;
        float g = getGreen(rgb) / 255.0F;
        float h = getBlue(rgb) / 255.0F;
        return new Vector3f(f, g, h);
    }

    public static int ticksToSeconds(int ticks) {
        return ticks / 20;
    }

    public static int secondsToTicks(int seconds) {
        return seconds * 20;
    }

    public static boolean hasStackInHands(LivingEntity entity, Predicate<ItemStack> stackPredicate) {
        return stackPredicate.test(entity.getMainHandStack()) || stackPredicate.test(entity.getOffHandStack());
    }

    public static int tickOrSync(int num, Runnable sync) {
        int i = num;
        if (i > 0) {
            i--;
            if (i == 0) {
                sync.run();
            }
        }

        return i;
    }

    public static List<LivingEntity> getEntitiesInArea(BlockPos pos, World world, int expansion) {
        Box area = new Box(pos).expand(expansion);
        return world.getEntitiesByClass(LivingEntity.class, area, entity -> true);
    }

    public static void applyRiptide(PlayerEntity player, float strength, int riptideTicks, float riptideDamage) {
        // ew vanilla code

        float f = player.getYaw();
        float g = player.getPitch();
        float h = -MathHelper.sin(f * ((float)Math.PI / 180F)) * MathHelper.cos(g * ((float)Math.PI / 180F));
        float k = -MathHelper.sin(g * ((float)Math.PI / 180F));
        float l = MathHelper.cos(f * ((float)Math.PI / 180F)) * MathHelper.cos(g * ((float)Math.PI / 180F));
        float m = MathHelper.sqrt(h * h + k * k + l * l);
        float n = 3.0F * ((1.0F + strength) / 4.0F);
        h *= n / m;
        k *= n / m;
        l *= n / m;
        player.addVelocity(h, k, l);
        player.useRiptide(riptideTicks, riptideDamage, player.getMainHandStack());
        if (player.isOnGround()) {
            player.move(MovementType.SELF, new Vec3d(0.0F, 1.1999999F, 0.0F));
        }
    }
}

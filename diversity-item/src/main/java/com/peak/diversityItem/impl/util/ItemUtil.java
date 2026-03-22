package com.peak.diversityItem.impl.util;

import com.mojang.serialization.Codec;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.joml.Vector3f;

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
}

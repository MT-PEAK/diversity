package com.peak.diversityItem.mixin.client;

import com.peak.diversityItem.features.interfaces.ItemWithEffects;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TridentRiptideFeatureRenderer.class)
public abstract class TridentRiptideFeatureRendererMixin {

    @ModifyVariable(
            method = "render*",
            at = @At(
                    value = "STORE"
            )
    )
    private VertexConsumer diversity$customRiptideTexture(VertexConsumer orig, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity player) {
            if ((player.getMainHandStack().getItem() instanceof ItemWithEffects effects)) {
                return vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucentEmissive(effects.getRiptideTexture(player.getMainHandStack(), player)));
            }

            if ((player.getOffHandStack().getItem() instanceof ItemWithEffects effects)) {
                return vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucentEmissive(effects.getRiptideTexture(player.getOffHandStack(), player)));
            }
        }
        return orig;
    }
}
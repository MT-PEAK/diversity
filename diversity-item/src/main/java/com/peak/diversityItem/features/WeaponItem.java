package com.peak.diversityItem.features;

import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class WeaponItem extends Item {
    private final float attackDamage;
    private final float attackSpeed;
    private final float entityReach;
    public final boolean isSword;

    public WeaponItem(Settings settings, float attackDamage, float attackSpeed, float entityReach, boolean isSword) {
        super(settings.maxCount(1));

        /* Statistics */
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.entityReach = entityReach;

        /* Other */
        this.isSword = isSword;
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.createAttributeModifiers();
    }

    public int getMaxCount() {
        return 1;
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !isSword || !miner.isCreative();
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(getNameColor(stack));
    }

    public AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, this.attackDamage, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, this.attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(Identifier.ofVanilla("base_entity_interaction_range"), this.entityReach, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public int getNameColor(ItemStack stack) {
        return 0xFFffffff;
    }
}

// java docs?
/**
 * desc
 * @param paramter!!!
 */
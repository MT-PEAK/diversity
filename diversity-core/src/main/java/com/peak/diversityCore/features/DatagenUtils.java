package com.peak.diversityCore.features;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;

public class DatagenUtils {
    public class Lang {
        public static void registerDamageType(FabricLanguageProvider.TranslationBuilder builder, RegistryKey<DamageType> registryKey, String normal, String item, String player) {
            String key = "death.attack." + registryKey.getValue().getPath();
            builder.add(key, normal);
            builder.add(key + ".item", item);
            builder.add(key + ".player", player);
        }

        public static void registerPotion(FabricLanguageProvider.TranslationBuilder builder, Potion potion, String formattedName) {
            String key = Registries.POTION.getId(potion).getPath();
            builder.add("item.minecraft.tipped_arrow.effect." + key, "Arrow of " + formattedName);
            builder.add("item.minecraft.potion.effect." + key, "Potion of " + formattedName);
            builder.add("item.minecraft.splash_potion.effect." + key, "Splash Potion of " + formattedName);
            builder.add("item.minecraft.lingering_potion.effect." + key, "Lingering Potion of " + formattedName);
        }

        public static void registerEnchantment(String modId, FabricLanguageProvider.TranslationBuilder builder, RegistryKey<Enchantment> registryKey, String name, String desc) {
            String key = registryKey.getValue().getPath().transform(string -> "enchantment." + modId.toLowerCase() + "." + string);
            builder.add(key, name);
            builder.add(key + ".desc", desc);
        }

        public static void registerAdvancement(String modId, FabricLanguageProvider.TranslationBuilder builder, String advancement, String title, String description) {
            String key = "advancements." + modId.toLowerCase() + "." + advancement.toLowerCase();
            builder.add(key + ".title", title);
            builder.add(key + ".description", description);
        }
    }
}

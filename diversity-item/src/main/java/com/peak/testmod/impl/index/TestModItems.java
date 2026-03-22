package com.peak.testmod.impl.index;

import com.peak.testmod.impl.TestMod;
import com.peak.testmod.impl.item.TestItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface TestModItems {
    List<Item> ITEMS = new ArrayList<>();

    Item TEST_ITEM = create("test_item", TestItem::new, new Item.Settings());

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        ITEMS.add(item);
        return Registry.register(Registries.ITEM, TestMod.id(name), item);
    }

    static void init() {}
}

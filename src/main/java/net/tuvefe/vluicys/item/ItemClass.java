package net.tuvefe.vluicys.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tuvefe.vluicys.Vluicys;

public class ItemClass
{
    public static final Item VLUICYS_MUSHROOM = registerItem("vluicys_mushroom",
            new VluicysMushroomItem(new Item.Settings().food(FoodClass.VLUICYS_MUSHROOM)));

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(Vluicys.MOD_ID, name), item);
    }

    public static void itemRegister()
    {
        Vluicys.LOGGER.info("ya esta puto xdxdxd" + Vluicys.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(VLUICYS_MUSHROOM);
        });
    }
}
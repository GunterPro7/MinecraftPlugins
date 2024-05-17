package utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static utils.CustomItem.*;

public class CraftingInventorys {
    public static final List<CustomInventory> ITEM_CRAFT_MAP = new ArrayList<>();
    public static void createInstanzes() {
        // Eternity's Edge
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{TREASURE_FRAGMENT, NAUTILUS_FRAGMENT, TREASURE_FRAGMENT,
                CHARCOLE_FRAGMENT, CHARCOLE_FRAGMENT, CHARCOLE_FRAGMENT, null, CHARCOLE_FRAGMENT}, ETERNITYS_EDGE));

        // Eternity's Pickaxe
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{TREASURE_FRAGMENT, NAUTILUS_FRAGMENT, NAUTILUS_FRAGMENT,
                null, CHARCOLE_FRAGMENT, NAUTILUS_FRAGMENT, CHARCOLE_FRAGMENT, null, TREASURE_FRAGMENT}, ETERNITYS_PICKAXE));

        // Eternity's Helmet
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{TREASURE_FRAGMENT, NAUTILUS_FRAGMENT, TREASURE_FRAGMENT,
                CHARCOLE_FRAGMENT, null, CHARCOLE_FRAGMENT}, ETERNITYS_HELMET));

        // Eternity's Chestplate
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{NAUTILUS_FRAGMENT, null, NAUTILUS_FRAGMENT,
                CHARCOLE_FRAGMENT, TREASURE_FRAGMENT, CHARCOLE_FRAGMENT, CHARCOLE_FRAGMENT, CHARCOLE_FRAGMENT, CHARCOLE_FRAGMENT}, ETERNITYS_CHESTPLATE));


        // Eternity's Leggings
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{CHARCOLE_FRAGMENT, CHARCOLE_FRAGMENT, CHARCOLE_FRAGMENT,
                TREASURE_FRAGMENT, null, TREASURE_FRAGMENT, NAUTILUS_FRAGMENT, null, NAUTILUS_FRAGMENT}, ETERNITYS_LEGGINGS));


        // Eternity's Boots
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{null, null, null,
                CHARCOLE_FRAGMENT, null, CHARCOLE_FRAGMENT, TREASURE_FRAGMENT, null, TREASURE_FRAGMENT}, ETERNITYS_BOOTS));

        // Ruby Helmet
        ITEM_CRAFT_MAP.add(new CustomInventory(EMERALD_FRAGMENT, RUBY_HELMET, 0, 1, 2, 3, 5));

        // Ruby Chestplate
        ITEM_CRAFT_MAP.add(new CustomInventory(EMERALD_FRAGMENT, RUBY_CHESTPLATE, 0, 2, 3, 4, 5, 6, 7, 8));

        // Ruby Leggings
        ITEM_CRAFT_MAP.add(new CustomInventory(EMERALD_FRAGMENT, RUBY_LEGGINGS, 0, 1, 2, 3, 5, 6, 8));

        // Ruby Boots
        ITEM_CRAFT_MAP.add(new CustomInventory(EMERALD_FRAGMENT, RUBY_BOOTS, 3, 5, 6, 8));// TODO RUBY ARMOR DAS MIT GÜNSTIGER WHATEVR VIULLAGER GEHT NOCHBN NED; STEHT NUR HIER DAS IS NED VERGESS!!!

        // Ultimate Totem
        ITEM_CRAFT_MAP.add(new CustomInventory(Material.TOTEM_OF_UNDYING, ULTIMATE_TOTEM, 1, 3, 4, 5, 7));

        // Ruby Pickaxe
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{RUBY_FRAGMENT, RUBY_FRAGMENT, RUBY_FRAGMENT,
                null, CHARCOLE_FRAGMENT, null, null, CHARCOLE_FRAGMENT}, RUBY_PICKAXE));

        // Illusioner Wand
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{null, ILLUSIONER_FRAGMENT, null,
                null, ILLUSIONER_FRAGMENT, null, null, CHARCOLE_FRAGMENT}, ILLUSIONER_WAND));

        // Auto Crafter
        ITEM_CRAFT_MAP.add(new CustomInventory(CRAFTING_FRAGMENT, AUTO_CRAFTER, 0, 1, 2, 3, 5, 6, 7, 8));

        // Silky Hoe
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{FARMING_FRAGMENT_POTATO, FARMING_FRAGMENT_CARROT,
                FARMING_FRAGMENT_BEETROOT, null, CHARCOLE_FRAGMENT, FARMING_FRAGMENT_SEEDS, CHARCOLE_FRAGMENT, null, FARMING_FRAGMENT_COCOA}, SILKY_HOE));

        // Charcole Block
        ITEM_CRAFT_MAP.add(new CustomInventory(CHARCOLE_FRAGMENT, CHARCOLE_BLOCK_FRAGMENT, 0, 1, 2, 3, 4, 5, 6, 7, 8));

        // Charcole Bundle
        ITEM_CRAFT_MAP.add(new CustomInventory(CHARCOLE_BLOCK_FRAGMENT, CHARCOLE_BUNDLE_FRAGMENT, 0, 1, 2, 3, 4, 5, 6, 7, 8));

        // Lumberjack's Axe
        ITEM_CRAFT_MAP.add(new CustomInventory(new CustomItem[]{null, CHARCOLE_BUNDLE_FRAGMENT, CHARCOLE_BUNDLE_FRAGMENT, null, CHARCOLE_FRAGMENT, CHARCOLE_BUNDLE_FRAGMENT, CHARCOLE_FRAGMENT}, LUMBERJACK_AXE));
    }

    public static void openAsWorkbenchPreview(Player player, Inventory inventory) {
        player.openWorkbench(null, true).getTopInventory().setContents(inventory.getContents());
    }

    public static boolean isSameCraftingRecipe(ItemStack[] itemStacks, ItemStack[] craftingRecipe) {
        for (int i = 0; i < itemStacks.length; i++) {
            ItemStack itemStack = itemStacks[i];
            ItemStack craftingItem = craftingRecipe[i];

            int itemStackModelData = Utils.getSecureModelData(itemStack);
            if (itemStackModelData != -1) {
                if (itemStackModelData != Utils.getSecureModelData(craftingItem)) {
                    return false;
                }
            } else {
                if (Utils.getSecureType(itemStack) != Utils.getSecureType(craftingItem)) {
                    return false;
                }
            }
        }
        return true;
    }
}

package costumItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class InfinitySword extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.GOLDEN_SWORD);

    public InfinitySword() {
        super(Material.GOLDEN_SWORD);

    }

    public static void enableCraftingRecipe() {

    }

    public static void createItem() {

    }

    public static boolean isInfinitySword(ItemStack item) {
        return true;
    }
}


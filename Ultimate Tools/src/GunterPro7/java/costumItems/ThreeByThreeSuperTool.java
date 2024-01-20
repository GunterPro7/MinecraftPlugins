package costumItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ThreeByThreeSuperTool extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);

    public ThreeByThreeSuperTool() {
        super(Material.DIAMOND_PICKAXE);

    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(" BB", " AB", "A  ");
        recipe.setIngredient('A', Material.STICK);
        recipe.setIngredient('B', Material.NETHERITE_INGOT);
        ThreeByThreeSuperTool.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_PICKAXE);

        List<String> lore = new ArrayList<>();
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404039);

        itemMeta.setDisplayName(ChatColor.GREEN + "3x3 Super Tool");

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isThreeByThreePickaxe(ItemStack item) {
        if (item != null && item.getType() == Material.DIAMOND_PICKAXE && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404039) {
                return true;
            }
        }
        return false;
    }
}

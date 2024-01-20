package costumItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class InfinityPickaxe extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE);

    public InfinityPickaxe() {
        super(Material.GOLDEN_PICKAXE);

    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(" BB", " AB", "A  ");
        recipe.setIngredient('A', Material.STICK);
        recipe.setIngredient('B', Material.NETHERITE_BLOCK);
        InfinityPickaxe.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.GOLDEN_PICKAXE);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add("§7Enjoy the infinit range of the Pickaxe!!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404053);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Infinity Pickaxe");
        //itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isInfinityPickaxe(ItemStack item) {
        if (item != null && item.getType() == Material.GOLDEN_PICKAXE && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404053) {
                return true;
            }
        }
        return false;
    }
}


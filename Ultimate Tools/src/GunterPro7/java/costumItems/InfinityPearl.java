package costumItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class InfinityPearl extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.ENDER_PEARL);

    public InfinityPearl() {
        super(Material.ENDER_PEARL);

    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("ABA", "BCB", "ABA");
        recipe.setIngredient('A', Material.END_CRYSTAL);
        recipe.setIngredient('B', Material.ENDER_PEARL);
        recipe.setIngredient('C', Material.NETHER_STAR);
        InfinityPearl.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_SWORD);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add("§7Teleport infinitly with the Infinity Pearl!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404045);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Infinity Pearl");

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isCooldownOver(Player player) {
        if (CustomItemData.lastPearlHook.get(player) == null) {
            return true;
        }
        return System.currentTimeMillis() - CustomItemData.lastPearlHook.get(player) > CustomItemData.pearlCD * 1000;
    }

    public static boolean isInfinityPearl(ItemStack item) {
        if (item != null && item.getType() == Material.ENDER_PEARL && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404045) {
                return true;
            }
        }
        return false;
    }
}

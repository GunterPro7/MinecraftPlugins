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

public class InfinityWaterBucket extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.WATER_BUCKET);

    public InfinityWaterBucket() {
        super(Material.WATER_BUCKET);

    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("ABA", "BCB", "ABA");
        recipe.setIngredient('A', Material.PRISMARINE);
        recipe.setIngredient('B', Material.SPONGE);
        recipe.setIngredient('C', Material.WATER_BUCKET);
        InfinityWaterBucket.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_SWORD);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add("§7Leap through the air with the Pearl Leap!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404046);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Infinity Water Bucket");
        //itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isInfinityWaterBucket(ItemStack item) {
        if (item != null && item.getType() == Material.WATER_BUCKET && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404046) {
                return true;
            }
        }
        return false;
    }
}

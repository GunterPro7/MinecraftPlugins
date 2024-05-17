package costumItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class InfinityLavaBucket extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.LAVA_BUCKET);

    public InfinityLavaBucket() {
        super(Material.WATER_BUCKET);
    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("ABA", "BCB", "ABA");
        recipe.setIngredient('A', Material.MAGMA_BLOCK);
        recipe.setIngredient('B', Material.FIRE_CHARGE);
        recipe.setIngredient('C', Material.LAVA_BUCKET);
        InfinityLavaBucket.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_SWORD);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add("§7Harness the power of lava with the Infinity Lava Bucket!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404044);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Infinity Lava Bucket");
        //itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isInfinityLavaBucket(ItemStack item) {
        if (item != null && item.getType() == Material.LAVA_BUCKET && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404044) {
                return true;
            }
        }
        return false;
    }

    
}

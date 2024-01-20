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

public class LightningBolt extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.STICK);

    public LightningBolt() {
        super(Material.STICK);

    }
    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("ABA", "BCB", "ABA");
        recipe.setIngredient('A', Material.GOLD_BLOCK);
        recipe.setIngredient('B', Material.FIRE_CHARGE);
        recipe.setIngredient('C', Material.NETHER_STAR);
        LightningBolt.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_SWORD);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add("§7Kill other player with the Lightning Bolt!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404047);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Lightning Bolt");

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isCooldownOver(Player player) {
        if (CustomItemData.lastBolt.get(player) == null) {
            return true;
        }
        return System.currentTimeMillis() - CustomItemData.lastBolt.get(player) > CustomItemData.boltCD * 1000;
    }

    public static boolean isLightningBolt(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() == Material.STICK && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404047) {
                return true;
            }
        }
        return false;
    }
}

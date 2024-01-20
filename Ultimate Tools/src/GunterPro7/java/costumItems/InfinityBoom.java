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

public class InfinityBoom extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.TNT);

    public InfinityBoom() {
        super(Material.TNT);
    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("ABA", "BCB", "ABA");
        recipe.setIngredient('A', Material.FIREWORK_ROCKET);
        recipe.setIngredient('B', Material.TNT);
        recipe.setIngredient('C', Material.NETHER_STAR);
        InfinityBoom.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_SWORD);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Infinity Boom");
        List<String> lore = new ArrayList<>();
        lore.add("§7Create a mighty explosion with this powerful boom!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404043);

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isCooldownOver(Player player) {
        if (CustomItemData.lastBoom.get(player) == null) {
            return true;
        }
        return System.currentTimeMillis() - CustomItemData.lastBoom.get(player) > CustomItemData.boomCD * 1000;
    }

    public static boolean isInfinityBoom(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() == Material.TNT && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404043) {
                return true;
            }
        }
        return false;
    }
}

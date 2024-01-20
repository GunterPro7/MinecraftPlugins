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

public class GrapplingHook extends CustomItemData {
    public static final ItemStack item = new ItemStack(Material.FISHING_ROD);
    public static ShapedRecipe recipe;

    public GrapplingHook() {
        super(Material.FISHING_ROD);
    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("  A", " BC", "B C");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.STICK);
        recipe.setIngredient('C', Material.IRON_BLOCK);
        GrapplingHook.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.IRON_SWORD);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add("§7Unleash the power of the grappling hook!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404040);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Grappling hook");

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static void knockPlayer(Player player, double distance) {
        player.setVelocity(player.getLocation().getDirection().multiply(0.45 * distance * (CustomItemData.grapplingHookRange / 10.0)).setY(0.15 * distance * (CustomItemData.grapplingHookRange / 10.0)));
    }

    public static boolean isCooldownOver(Player player) {
        if (CustomItemData.lastGrapplingHook.get(player) == null) {
            return true;
        }
        return System.currentTimeMillis() - CustomItemData.lastGrapplingHook.get(player) > CustomItemData.grapplingHookCD * 1000;
    }

    public static boolean isGrapplingHook(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() == Material.FISHING_ROD && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404040) {
                return true;
            }
        }
        return false;
    }
}

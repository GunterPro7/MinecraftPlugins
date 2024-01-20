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

public class IceWand extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.STICK);

    public IceWand() {
        super(Material.STICK);
    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("  A", " BC", "B C");
        recipe.setIngredient('A', Material.BLUE_ICE);
        recipe.setIngredient('B', Material.SNOWBALL);
        recipe.setIngredient('C', Material.NETHER_STAR);
        IceWand.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.IRON_SWORD);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add("§7Freeze your entites with the Ice Wand!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404042);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Ice Wand");

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isIceWand(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() == Material.STICK && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404042) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFreezing(Player player) {
        if (CustomItemData.lastIceWandFreeze.get(player) == null) {
            return false;
        }
        return !(System.currentTimeMillis() - CustomItemData.lastIceWandFreeze.get(player) > CustomItemData.iceWandDuration * 1000);
    }

    public static boolean isCooldownOver(Player player) {
        if (CustomItemData.lastIceWand.get(player) == null) {
            return true;
        }
        return System.currentTimeMillis() - CustomItemData.lastIceWand.get(player) > CustomItemData.iceWandCD * 1000;
    }
}

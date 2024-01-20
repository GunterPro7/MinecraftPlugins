package costumItems;


import org.bukkit.*;
import org.bukkit.block.Block;
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

public class Hyperion extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.IRON_SWORD);

    public Hyperion() {
        super(Material.IRON_SWORD);
    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(" W ", " W ", "0S0");
        recipe.setIngredient('W', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('S', Material.STICK);
        Hyperion.recipe = recipe;
        Bukkit.addRecipe(recipe);// Erstelle das ShapelessRecipe-Objekt
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.IRON_SWORD);

        // Optional: Setze den Namen des Items
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GREEN + "Hyperion");
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setCustomModelData(404041);

        // Setting the custom lore
        List<String> lore = new ArrayList<>();
        lore.add("§7BOOM BOOOOM WOOHOOOOO BOOOM!");
        itemMeta.setLore(lore);

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isHyperion(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() == Material.IRON_SWORD && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            assert meta != null;
            return meta.getCustomModelData() == 404041;
        }
        return false;
    }

    public boolean isBlockInWay(Player player) {
        // Get the player's location
        Location playerLocation = player.getLocation();

        // Get the block at the player's location
        Block blockAtPlayerLocation = playerLocation.getBlock();

        // Check if the block is a type that blocks movement
        Material blockType = blockAtPlayerLocation.getType();
        return blockType.isSolid();
    }
}

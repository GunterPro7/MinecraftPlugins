package costumItems;

import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class SuperLeap extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.ENDER_EYE);

    public SuperLeap() {
        super(Material.ENDER_EYE);

    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("ABA", "BCB", "ABA");
        recipe.setIngredient('A', Material.ENDER_PEARL);
        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('C', Material.ENDER_EYE);
        SuperLeap.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_SWORD);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add("§7Leap to other Players with the Pearl Leap!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404048);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Pearl Leap");

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isCooldownOver(Player player) {
        if (CustomItemData.lastLeap.get(player) == null) {
            return true;
        }
        return System.currentTimeMillis() - CustomItemData.lastLeap.get(player) > CustomItemData.leapCD * 1000;
    }

    public static boolean isSuperLeap(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() == Material.ENDER_EYE && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404048) {
                return true;
            }
        }
        return false;
    }

    public static Inventory createLeapInv(Player player) {
        Inventory playerInv = Bukkit.createInventory(null, 27, "§lLeap to Player");
        for (Player curPlayer : Bukkit.getOnlinePlayers()) {
            if (curPlayer == player) {
                continue;
            }
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwningPlayer(curPlayer);
            meta.setDisplayName(ChatColor.GREEN + curPlayer.getName());
            skull.setItemMeta(meta);
            playerInv.addItem(skull);
            // Do something with the skull item, such as giving it to the player or dropping it on the ground
        }
        return playerInv;

    }
}

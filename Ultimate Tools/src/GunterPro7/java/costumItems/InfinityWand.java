package costumItems;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InfinityWand extends CustomItemData {
    public static final ItemStack item = new ItemStack(Material.BLAZE_ROD);
    public static final Inventory inventory = Bukkit.createInventory(null, 27, "§lAdd an Item!");

    public InfinityWand() {
        super(Material.BLAZE_ROD);
    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("FAF", "ADA", "FAF");
        recipe.setIngredient('F', Material.STONE);
        recipe.setIngredient('D', Material.BLAZE_ROD);
        recipe.setIngredient('A', Material.NETHERITE_INGOT);
        FlameThrower.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_SWORD);

        // Optional: Setze den Namen des Items
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GREEN + "Infinity Wand");
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setCustomModelData(404052);

        // Setting the custom lore
        List<String> lore = new ArrayList<>();
        lore.add("§7Enjoy the infinity Range of the Wand!");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        ItemStack itemToAdd = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMetaToAdd = itemToAdd.getItemMeta();

        itemMetaToAdd.setDisplayName("§7Put an Item in there!");
        itemToAdd.setItemMeta(itemMetaToAdd);

        // fill the inventory slots
        for (int i = 0; i < 27; i++) {
            if (i != 13) {
                inventory.setItem(i, itemToAdd);
            }
        }
    }

    public static boolean isInfinityWand(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() == Material.BLAZE_ROD && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            return meta != null && meta.getCustomModelData() == 404052;
        }
        return false;
    }

    public static boolean isCooldownOver(Player player) {
        if (CustomItemData.lastInfinityWand.get(player) == null) {
            return true;
        }
        return System.currentTimeMillis() - CustomItemData.lastInfinityWand.get(player) > CustomItemData.infinityWandCD * 1000;
    }
}

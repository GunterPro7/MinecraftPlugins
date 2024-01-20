package costumItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class LockedSlot extends CustomItemData {
    public static final ItemStack item = new ItemStack(Material.BARRIER);

    public LockedSlot() {
        super(Material.BARRIER);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_SWORD);
        itemMeta.setDisplayName(ChatColor.RED + "LOCKED");
        itemMeta.setCustomModelData(404054);
        item.setItemMeta(itemMeta);
    }

    public static boolean isLockedSlot(ItemStack item) {
        if (item != null && item.getType() == Material.BARRIER && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getCustomModelData() == 404054) {
                return true;
            }
        }
        return false;
    }
}

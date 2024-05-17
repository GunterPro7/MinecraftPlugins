package items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import utils.CustomItems;

import java.util.ArrayList;
import java.util.List;

public class EternitysEdge extends ItemStack {
    public static final int UNIQUE_NUMBER = 404200;
    public EternitysEdge() {
        super(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.AQUA + " Eternity's Edge " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Thunder");
        lore.add(ChatColor.YELLOW + "Right Clicking will summon a Thunder-Strike");
        lore.add(ChatColor.RED + "30 second cooldown!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    public static ItemStack upgrade(ItemStack item) {
        ItemStack newItem = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Teleport");
        lore.add(ChatColor.YELLOW + "Right Clicking while Sneaking teleports you 16 blocks into that direction");
        lore.add(ChatColor.RED + "10 second cooldown!");
        lore.add("");
        lore.add(ChatColor.GREEN + "UPGRADED");
        itemMeta.setLore(lore);

        newItem.setItemMeta(itemMeta);
        return newItem;
    }

    public static boolean isItem(ItemStack item) {
        return item != null && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }

    public static boolean isUpgradedItem(ItemStack item) {
        return isItem(item) && item.getType() == Material.NETHERITE_SWORD;
    }
}

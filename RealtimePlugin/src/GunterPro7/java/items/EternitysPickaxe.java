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

public class EternitysPickaxe extends ItemStack {
    public static final int UNIQUE_NUMBER = 404201;

    public EternitysPickaxe() {
        super(Material.DIAMOND_PICKAXE);
        ItemMeta itemMeta = getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.AQUA + " Eternity's Pickaxe " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: 3x3 Pickaxe");
        lore.add(ChatColor.YELLOW + "Mining with this Pickaxe will destroy blocks in");
        lore.add(ChatColor.YELLOW + "the radius of 3x3 Blocks!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    public static ItemStack upgrade(ItemStack item) {
        ItemStack newItem = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Explosion");
        lore.add(ChatColor.YELLOW + "Right Clicking will launch an explosion which");
        lore.add(ChatColor.YELLOW + "won't heart yourself!");
        lore.add(ChatColor.RED + "60 second cooldown!");
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
        return isItem(item) && item.getType() == Material.NETHERITE_PICKAXE;
    }
}

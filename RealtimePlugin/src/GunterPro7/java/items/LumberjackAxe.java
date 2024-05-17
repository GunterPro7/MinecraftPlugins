package items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import utils.CustomItems;

import java.util.ArrayList;
import java.util.List;

public class LumberjackAxe extends ItemStack {
    public static final int UNIQUE_NUMBER = 404208;

    public LumberjackAxe() {
        super(Material.DIAMOND_AXE);
        ItemMeta itemMeta = getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.AQUA + " Lumberjack's Axe " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Big Power");
        lore.add(ChatColor.YELLOW + "Left clicking while sneaking will destroy");
        lore.add(ChatColor.YELLOW + "a maximum of 20 Wood Blocks at once!");
        lore.add(ChatColor.RED + "5 second cooldown!");
        lore.add("");
        lore.add(ChatColor.GOLD.toString() + ChatColor.BOLD + "LEGENDARY ITEM");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    public static boolean isItem(ItemStack item) {
        return item != null && item.getType() == Material.DIAMOND_AXE && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }
}
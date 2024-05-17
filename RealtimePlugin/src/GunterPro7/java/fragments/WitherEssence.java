package fragments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import utils.CustomItems;

import java.util.ArrayList;
import java.util.List;

public class WitherEssence extends ItemStack {
    public static final int UNIQUE_NUMBER = 404101;
    public WitherEssence() {
        super(Material.WITHER_SKELETON_SKULL);
        ItemMeta itemMeta = getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.GREEN + "Wither Essence");
        List<String> lore = new ArrayList<>();
        lore.add("§7This Essence can be used to Upgrade the " + ChatColor.GOLD + "Eternity's Edge" + "§7!");
        lore.add(ChatColor.DARK_GREEN + "Right Click to view Recipe");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    public static boolean isItem(ItemStack item) {
        return item != null && item.getType() == Material.WITHER_SKELETON_SKULL && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }
}

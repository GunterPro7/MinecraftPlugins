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

public class EternitysArmor extends ItemStack {
    public static final int UNIQUE_NUMBER = 404202;
    public EternitysArmor(Material material) {
        super(material);
        ItemMeta itemMeta = getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.AQUA + " Eternity's " + getCorrectWord(material) + " " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Heal");
        lore.add(ChatColor.YELLOW + "Sneak to heal yourself");
        lore.add(ChatColor.RED + "Full set needed!");
        lore.add(ChatColor.RED + "10 second cooldown!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    private String getCorrectWord(Material material) {
        String unformatedString = material.name().replace("DIAMOND_", "").toLowerCase();
        return unformatedString.replace(unformatedString.charAt(0), Character.toUpperCase(unformatedString.charAt(0)));
    }

    public static ItemStack upgrade(ItemStack item) {
        ItemStack newItem = new ItemStack(Material.matchMaterial("NETHERITE_" + item.getType().name().replace("DIAMOND_", "")));
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Damage Reduction");
        lore.add(ChatColor.YELLOW + "Each Peace wearn will grant 15% Damage Reduction");
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
        return isItem(item) && item.getType().name().startsWith("NETHERITE");
    }
}
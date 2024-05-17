package items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import utils.CustomItems;

import java.util.ArrayList;
import java.util.List;

public class RubyArmor extends ItemStack {
    public static final int UNIQUE_NUMBER = 404203;

    public RubyArmor(Material material) {
        super(material);
        ItemMeta itemMeta = getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.AQUA + " Ruby " + getCorrectWord(material) + " " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Lucky Trades");
        lore.add(ChatColor.YELLOW + "For every Peace wearing you get a 20% chance of getting");
        lore.add(ChatColor.YELLOW + "an item twice from the same trade!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        leatherArmorMeta.setColor(Color.fromRGB(239, 75, 83));

        setItemMeta(leatherArmorMeta);
    }

    private String getCorrectWord(Material material) {
        String unformatedString = material.name().replace("LEATHER_", "").toLowerCase();
        return unformatedString.replace(unformatedString.charAt(0), Character.toUpperCase(unformatedString.charAt(0)));
    }

    public static boolean isItem(ItemStack item) {
        return item != null && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }
}
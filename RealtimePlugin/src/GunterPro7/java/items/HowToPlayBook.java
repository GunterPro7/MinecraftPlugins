package items;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import utils.CustomItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HowToPlayBook extends ItemStack {
    public static final int UNIQUE_NUMBER = 404209;

    public HowToPlayBook() {
        super(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.AQUA + " Adventurer Plugin " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD.toString() + ChatColor.BOLD + "HOW TO PLAY - GUIDE");
        lore.add(ChatColor.YELLOW + "A new Adventure - by " + ChatColor.ITALIC + "GunterPro7");
        lore.add("");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        // Set the first page with a string
        itemMeta.addPage("Introduction page");
        // Set the new meta to the book
        setItemMeta(itemMeta);
    }

    public static boolean isItem(ItemStack item) {
        return item != null && item.getType() == Material.WRITTEN_BOOK && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }
}

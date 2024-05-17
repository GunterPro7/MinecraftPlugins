package items;

import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import utils.CustomItems;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AutoCrafter extends ItemStack {
    public static final int UNIQUE_NUMBER = 404206;
    public static final NamespacedKey AUTO_CRAFTER_KEY = new NamespacedKey(Main.getPlugin(), "AutoCrafterID");

    public static final HashMap<String, Inventory> UUID_MAP = new HashMap<>();

    public AutoCrafter() {
        super(Material.DISPENSER);
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);
        String uuid = String.valueOf(UUID.randomUUID());
        container.set(AUTO_CRAFTER_KEY, PersistentDataType.STRING, uuid);
        UUID_MAP.put(uuid, createAutoCrafterInv());

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.AQUA + " Auto Crafter " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Auto-Craft Items");
        lore.add(ChatColor.YELLOW + "Holding this item will convert the given input");
        lore.add(ChatColor.YELLOW + "to the given output (if possible)");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    public static Inventory createAutoCrafterInv() {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.DARK_GRAY + " Auto Crafter " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, Utils.getItemByAutoCrafterInventory(i));
        }
        return inventory;
    }

    public static boolean isItem(ItemStack item) {
        return item != null && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }
}

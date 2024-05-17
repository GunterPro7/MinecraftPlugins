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

public class RubyPickaxe extends ItemStack {
    public static final int UNIQUE_NUMBER = 404204;

    public RubyPickaxe() {
        super(Material.IRON_PICKAXE);
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.AQUA + " Ruby Pickaxe " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Drop");
        lore.add(ChatColor.YELLOW + "Mining Emeralds have a 25% chance to drop");
        lore.add(ChatColor.YELLOW + "an Emerald Fragment");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    public static boolean isItem(ItemStack item) {
        return item != null && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }
}

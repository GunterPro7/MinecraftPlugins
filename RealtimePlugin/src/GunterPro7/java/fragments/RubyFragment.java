package fragments;

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

public class RubyFragment extends ItemStack {
    public static final int UNIQUE_NUMBER = 404105;

    public RubyFragment() {
        super(Material.REDSTONE);
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.GREEN + "Ruby Fragment");
        List<String> lore = new ArrayList<>();
        lore.add("§7This Fragment can be used to Craft the " + ChatColor.GOLD + "Ruby Pickaxe" + "§7!");
        lore.add(ChatColor.DARK_GREEN + "Right Click to view Recipe");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    public boolean isItem(ItemStack item) {
        return item != null && item.getType() == Material.EMERALD && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }
}

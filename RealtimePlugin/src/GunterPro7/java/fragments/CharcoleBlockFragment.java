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

public class CharcoleBlockFragment extends ItemStack {
    public static final int UNIQUE_NUMBER = 404113;

    public CharcoleBlockFragment() {
        super(Material.COAL_BLOCK);
        ItemMeta itemMeta = getItemMeta();

        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.GREEN + "Charcole Block Fragment");
        List<String> lore = new ArrayList<>();
        lore.add("§7This Fragment can be used to Craft the " + ChatColor.GOLD + "Charcole Bundle Fragment" + "§7!");
        lore.add(ChatColor.DARK_GREEN + "Right Click to view Recipe");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    public boolean isItem(ItemStack item) {
        return item != null && item.getType() == Material.COAL_BLOCK && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }
}

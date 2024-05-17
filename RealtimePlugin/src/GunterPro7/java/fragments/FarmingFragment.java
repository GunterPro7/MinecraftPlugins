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
import java.util.HashMap;
import java.util.List;

public class FarmingFragment extends ItemStack {
    public static final HashMap<Material, Integer> UNIQUE_NUMBER_MAP = new HashMap<Material, Integer>() {{
       put(Material.CARROT, 404108);
       put(Material.POTATO, 404109);
       put(Material.WHEAT_SEEDS, 404110);
       put(Material.BEETROOT_SEEDS, 404111);
       put(Material.COCOA_BEANS, 404112);
    }};

    public FarmingFragment(Material material) {
        super(material);
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.GREEN + "Farming Fragment");
        List<String> lore = new ArrayList<>();
        lore.add("§7This Fragment can be used to Craft the " + ChatColor.GOLD + "Silky Hoe" + "§7!");
        lore.add(ChatColor.DARK_GREEN + "Right Click to view Recipe");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER_MAP.get(material));

        setItemMeta(itemMeta);
    }

    public boolean isItem(ItemStack item) {
        return item != null && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER_MAP.get(item.getType());
    }
}

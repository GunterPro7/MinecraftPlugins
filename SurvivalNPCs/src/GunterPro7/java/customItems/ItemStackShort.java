package customItems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemStackShort extends ItemStack {
    public ItemStackShort(Material material, String displayName, int id, String... lore) {
        super(material);
        ItemMeta itemMeta = getItemMeta();


        itemMeta.setCustomModelData(id);

        if (!(lore.length == 1 && Objects.equals(lore[0], ""))) {
            itemMeta.setLore(Arrays.asList(lore));
        }

        itemMeta.setDisplayName(displayName);

        setItemMeta(itemMeta);
    }

    public ItemStackShort(Material material, String displayName, int id) {
        this(material, displayName, id, "");
    }
}

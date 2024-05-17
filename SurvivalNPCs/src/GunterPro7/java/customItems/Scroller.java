package customItems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import utils.ScrollType;

public class Scroller extends ItemStack {
    public Scroller(ScrollType scrollType) {
        super(Material.ARROW);
        ItemMeta itemMeta = getItemMeta();

        itemMeta.setCustomModelData(scrollType == ScrollType.RIGHT ? 40111 : 40110);

        itemMeta.setDisplayName("Scroll " + (scrollType == ScrollType.RIGHT ? "right" : "left") + "!");

        setItemMeta(itemMeta);
    }
}

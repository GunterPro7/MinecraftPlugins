package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OnBlockPlace  implements Listener {

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        // Infinity Boom
        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            if (customModelData == 404043 || customModelData == 404051) {
                event.setCancelled(true);
            }
        }
    }
}

package listeners;

import main.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static org.bukkit.Bukkit.getLogger;

public class DespawnListener implements Listener {

    @EventHandler
    public void onItemSpawn2(ItemSpawnEvent event) {
        Item item = event.getEntity();
        Main.itemEntities.add(item);


        if (event.getEntity() instanceof Item) {
            Entity entity = event.getEntity();
            if (entity.isDead()) {
                ItemStack clickedItem = (ItemStack) event.getEntity();
                ItemMeta itemMeta = clickedItem.getItemMeta();
                String itemId = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(), "item_id"), PersistentDataType.STRING);
            }
        }
    }
}
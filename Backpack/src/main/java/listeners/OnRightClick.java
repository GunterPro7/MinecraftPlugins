package listeners;

import main.Backpacks;
import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

public class OnRightClick implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        try {
            ItemStack clickedItem = event.getItem();
            if (clickedItem != null && clickedItem.getType() == Material.PLAYER_HEAD && clickedItem.getItemMeta() instanceof SkullMeta) {
                clickedItem = event.getItem();
                ItemMeta itemMeta = clickedItem.getItemMeta();
                String itemId = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(), "item_id"), PersistentDataType.STRING);
                if (itemId == null) {
                    return;
                }
                Backpacks.setUUIDForPlayer(event.getPlayer(), itemId);
                //Backpacks.loadBackpackFromUUID(itemId);
                Backpacks.prepareLoadBackpackFromUUID(itemId);
                Player player = event.getPlayer();

                Bukkit.getPlayer(player.getName()).openInventory(Backpacks.getBPFromUUID(itemId).getInventory());
                event.setCancelled(true);

            }
        } catch (Exception e) {
            // Unsupported Error
        }
    }
}

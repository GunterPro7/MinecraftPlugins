package listeners;

import costumItems.AutoPickUp;
import costumItems.CustomItemData;
import costumItems.IceWand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import utils.Utils;

public class OnPlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (IceWand.isFreezing(event.getPlayer())) {
            Player player = event.getPlayer();
            double timeRemaining = Utils.round(CustomItemData.iceWandDuration - ((System.currentTimeMillis() - CustomItemData.lastIceWandFreeze.get(player)) / 1000.0), -1);
            player.sendMessage(ChatColor.RED + "You are currently freezing! Time Remaining: " + ChatColor.YELLOW + timeRemaining);
            event.setCancelled(true);
        }
        if (AutoPickUp.isItem(event.getPlayer().getInventory().getItemInOffHand())) {
            Player player = event.getPlayer();
            Location location = player.getLocation();

            Inventory playerInventory = player.getInventory();
            int radius = 4;
            for (Entity nearbyEntity : location.getWorld().getNearbyEntities(location, radius, radius, radius)) {
                if (nearbyEntity instanceof Item) {
                    Item item = (Item) nearbyEntity;
                    Location itemLocation = item.getLocation();
                    if (location.distance(itemLocation) <= radius) {
                        playerInventory.addItem(item.getItemStack());
                        item.remove();
                    }
                }
            }
        }
    }
}

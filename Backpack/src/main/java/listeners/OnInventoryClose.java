package listeners;

import main.Backpacks;
import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import utils.Backpack;

import java.io.IOException;
import java.util.HashMap;

import static org.bukkit.Bukkit.getLogger;


public class OnInventoryClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) throws IOException {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        String uuid = Backpacks.getUUIDFromPlayer(player);
        Backpack backpack = Backpacks.getBPFromUUID(uuid);
        if (backpack != null && inventory.equals(backpack.getInventory())) {
            Backpacks.saveInventoryToFile(uuid);
        }

        // Überprüfe, ob es sich um dein spezielles Inventar handelt
        if (event.getView().getTitle().equals(Main.title)) {
            // Schleife durch alle Items im Inventar
            for (ItemStack item : inventory.getContents()) {
                // Wenn das Item ein Player Head oder eine Shulker Box ist, lege es ins Inventar des Spielers
                if (item != null && (item.getType() == Material.PLAYER_HEAD || item.getType().toString().contains("SHULKER_BOX"))) {
                    // Versuche das Item ins Inventar des Spielers zu legen
                    HashMap<Integer, ItemStack> result = player.getInventory().addItem(item);

                    // Wenn das Item nicht hinzugefügt werden konnte, lege es auf den Boden
                    if (!result.isEmpty()) {
                        player.getWorld().dropItem(player.getLocation(), item);
                    }
                }
            }
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack currentItem;
        if (event.getHotbarButton() != -1) {
            currentItem = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
        } else {
            currentItem = event.getCurrentItem();
        }
        // Get the clicked inventory
        Inventory clickedInventory = event.getClickedInventory();

        // Check if the clicked inventory is a chest
        if (clickedInventory != null && event.getView().getTitle().equals(Main.title)) {
            if (currentItem != null && (currentItem.getType().toString().contains("SHULKER_BOX") || currentItem.getType().toString().contains("PLAYER_HEAD"))) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            }
        }
    }
}

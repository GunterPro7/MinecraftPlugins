package listeners;

import costumItems.CustomItemData;
import costumItems.InfinityWand;
import costumItems.LockedSlot;
import costumItems.SuperLeap;
import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getLogger;

public class OnInventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        // If inventory is infinity Wand inv
        if (event.getView().getTitle().equals("§lAdd an Item!")) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && !event.getCurrentItem().getItemMeta().getDisplayName().equals("§7Put an Item in there!") && event.getCurrentItem().getType().isBlock()) {
                InfinityWand.inventory.setItem(13, new ItemStack(event.getCurrentItem().getType()));
            }
            event.setCancelled(true);
            return;
        }

        if (event.getView().getTitle().equals("§lUltimate Tools")) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.CHEST && !LockedSlot.isLockedSlot(clickedItem)) {
                if (event.isRightClick() && event.isShiftClick()) {
                    clickedItem.setAmount(32);
                } else if (event.isShiftClick()) {
                    clickedItem.setAmount(64);
                }
                player.getInventory().addItem(clickedItem);

                Main.resetItemsOfInventory(player);
            }
            event.setCancelled(true);
        }


        // Anvil überprüfung - auf die Items sollen keine enchants drauf gehen
        if (event.getClickedInventory() instanceof AnvilInventory) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                int data = event.getCurrentItem().getItemMeta().getCustomModelData();
                if (data > 404039 && data < 404054) {
                    event.setCancelled(true);
                }
            }
        }


        // Überprüfe, ob das Inventar, auf das der Spieler geklickt hat, ein Spielerkopf-Inventar ist
        if (event.getView().getTitle().equals("§lLeap to Player")) {
            // Überprüfe, ob der geklickte Gegenstand ein Schädel ist
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() != Material.PLAYER_HEAD) {
                return;
            }

            HashMap<Integer, ItemStack> items = new HashMap<>();
            for (Map.Entry<Integer, ? extends ItemStack> entry : player.getInventory().all(SuperLeap.item.getType()).entrySet()) {
                items.put(entry.getKey(), entry.getValue().clone());
            }
            for (ItemStack curItem : items.values()) {
                curItem.setAmount(1);
                if (curItem.isSimilar(SuperLeap.item)) {
                    SkullMeta skullMeta = (SkullMeta) event.getCurrentItem().getItemMeta();
                    Player targetPlayer = skullMeta.getOwningPlayer().getPlayer();
                    if (targetPlayer != null) {
                        player.teleport(targetPlayer.getLocation());
                        if (player.getGameMode() != GameMode.CREATIVE) {
                            player.getInventory().removeItem(curItem);
                        }
                        CustomItemData.lastLeap.put(player, System.currentTimeMillis());
                    }
                    break;
                }
            }
            event.setCancelled(true);
        }
    }
}

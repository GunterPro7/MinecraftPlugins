package listeners;

import customItems.ItemStackShort;
import main.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class InteractListeners implements Listener {
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.NAME_TAG) {
            entity.setCustomName(Objects.requireNonNull(item.getItemMeta()).getDisplayName());
        } else {
            PlayerInformation pi = Main.PLAYERS.get(player);
            Inventory inventory;

            if (pi.getLastNPC() == null || pi.getLastNPC().getEntity() != entity) {
                NPCInventory npcInventory = new NPCInventory(entity, player);
                pi.setLastNPC(npcInventory);
                inventory = npcInventory.getInventory();
            } else {
                inventory = NPCInventorys.getStarterInventory();
            }
            player.openInventory(inventory);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            NPC.sendChatMessages(player, new ArrayList<>(Main.PLAYERS.get(player).getLastNPC().getInteractionMessages()), 20);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null && itemMeta.hasCustomModelData()) {
                int id = itemMeta.getCustomModelData();
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                PlayerInformation pi = Main.PLAYERS.get(player);
                NPC npc = pi.getLastNPC();
                InventoryAction action = event.getAction();

                switch (id) {
                    case 40001:
                        player.openInventory(NPCInventorys.getInteractionInventory(npc.getInteractionMessages(), npc.getInteractionPage()));
                        break;
                    case 40002:
                        player.sendMessage(ChatColor.GREEN + "Run to the location you want to put a Waypoint and type " + ChatColor.GOLD + "/NPC add location");
                    case 40100:
                        player.closeInventory();
                        player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Type in your message now!");
                        pi.setListening(System.currentTimeMillis());
                        break;
                    case 40200:
                        player.openInventory(NPCInventorys.getStarterInventory());
                        break;
                    case 40111:
                        npc.setInteractionPage(npc.getInteractionPage() + 2);
                    case 40110:
                        npc.setInteractionPage(npc.getInteractionPage() - 1);
                        player.openInventory(NPCInventorys.getInteractionInventory(npc.getInteractionMessages(), npc.getInteractionPage()));
                        break;
                    case 40300:
                        if (action == InventoryAction.PICKUP_HALF) {
                            player.openInventory(NPCInventorys.getCheckInventory(itemMeta.getDisplayName()));
                            break;
                        } else if (action == InventoryAction.PICKUP_ALL) {
                            npc.moveInteractionLine(itemMeta.getDisplayName(), true);
                        } else if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                            npc.moveInteractionLine(itemMeta.getDisplayName(), false);
                        }

                        player.openInventory(NPCInventorys.getInteractionInventory(npc.getInteractionMessages(), npc.getInteractionPage()));
                        event.setCancelled(true);
                        break;
                    case 40500:
                        npc.removeInteractionLine(event.getClickedInventory().getItem(22).getItemMeta().getDisplayName());
                    case 40501:
                        player.openInventory(NPCInventorys.getInteractionInventory(npc.getInteractionMessages(), npc.getInteractionPage()));
                        event.setCancelled(true);
                        break;
                    default:
                        event.setCancelled(false);
                }
            }
        }
    }
}


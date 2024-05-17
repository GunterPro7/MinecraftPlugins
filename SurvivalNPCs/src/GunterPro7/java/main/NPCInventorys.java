package main;

import customItems.ItemStackShort;
import customItems.Scroller;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import utils.ScrollType;

import java.util.ArrayList;
import java.util.List;

public class NPCInventorys {
    public static Inventory getStarterInventory() {
        Inventory inventory = Bukkit.createInventory(null, 54, "Config NPC");

        inventory.setItem(0, new ItemStackShort(Material.EMERALD_BLOCK, ChatColor.GREEN + "Config Interactions", 40001));
        inventory.setItem(1, new ItemStackShort(Material.REDSTONE_BLOCK, ChatColor.GREEN + "Config Patriol", 40002));
        inventory.setItem(27, new ItemStackShort(Material.GHAST_SPAWN_EGG, ChatColor.YELLOW + "Edit Type", 40004));


        return inventory;
    }

    public static Inventory getInteractionInventory(List<String> messages, int page) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Config NPC - Interactions");

        int counter = 45*page;
        int min = Math.min(messages.size() - counter, 45);

        for (int i = 0; i < min; i++) {
            inventory.setItem(i, new ItemStackShort(Material.NAME_TAG, ChatColor.AQUA + messages.get(counter++), 40300, "",
                    ChatColor.GREEN + "Right Click " + ChatColor.GRAY + "this name tag to " +ChatColor.RED + "delete " + ChatColor.GRAY + "it!", "",
                    ChatColor.GREEN + "Left Click " + ChatColor.GRAY + "this name tag to " +ChatColor.RED + "move " + ChatColor.GRAY + "it", ChatColor.GRAY + "to the left!", "",
                    ChatColor.GREEN + "Left Shift Click " + ChatColor.GRAY + "this name tag to " +ChatColor.RED + "move ", ChatColor.GRAY + "to the right!"));
        }

        if (messages.size() > counter) {
            inventory.setItem(53, new Scroller(ScrollType.RIGHT));
        }
        if (page != 0) {
            inventory.setItem(45, new Scroller(ScrollType.LEFT));
        }

        inventory.setItem(48, new ItemStackShort(Material.GREEN_CONCRETE, ChatColor.GREEN + "Text-Interaction", 40100));
        inventory.setItem(49, new ItemStackShort(Material.BARRIER, ChatColor.GREEN + ChatColor.BOLD.toString() + "BACK", 40200));
        inventory.setItem(50, new ItemStackShort(Material.YELLOW_CONCRETE, ChatColor.GREEN + "Example-Interaction", 40102));
        return inventory;
    }

    public static Inventory getInteractionInventory(List<String> messages) {
        return getInteractionInventory(messages, 0);
    }

    public static Inventory getInteractionInventory() {
        return getInteractionInventory(new ArrayList<>());
    }

    public static Inventory getCheckInventory(String name) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Are you sure??");
        inventory.setItem(22, new ItemStackShort(Material.NAME_TAG, ChatColor.RED + name, 49999, ChatColor.GRAY + "This will" + ChatColor.RED + " delete " + ChatColor.GRAY + "this", ChatColor.GRAY + "item forever!"));
        inventory.setItem(38, new ItemStackShort(Material.GREEN_CONCRETE, ChatColor.GREEN + "Proceed", 40500));
        inventory.setItem(42, new ItemStackShort(Material.RED_CONCRETE, ChatColor.RED + "Cancel", 40501));

        return inventory;
    }
}

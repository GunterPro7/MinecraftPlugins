package main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class NPCInventory extends NPC {
    private final Inventory inventory;
    public NPCInventory(Entity entity, Player player) {
        super(entity, player);
        inventory = NPCInventorys.getStarterInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }
}

package listeners;

import main.Backpacks;
import main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.io.IOException;

public class OnCraftingItem implements Listener {
    @EventHandler
    public void onCraftItem(CraftItemEvent event) throws IOException {
        String oldUUID = Main.getCurrentID();
        Backpacks.addNewBackpackToListAndFile(oldUUID);
        Backpacks.enableCraftingRecipe();
        Backpacks.saveInventoryToFile(oldUUID);
    }
}
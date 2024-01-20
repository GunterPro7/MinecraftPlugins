package listeners;

import costumItems.CodingBook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

public class OnItemCraft implements Listener {
    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        Player player = (Player) event.getView().getPlayer();
        Recipe recipe = event.getRecipe();

        if (recipe != null) {
            ItemStack result = recipe.getResult();
            if (result.getItemMeta() != null) {
                ItemMeta itemMeta = result.getItemMeta();
                if (itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == CodingBook.UNIQUE_NUMBER) {
                    if (!player.hasPermission("codingbook.craft")) {
                        event.getInventory().setResult(null);
                    }
                }
            }
        }
    }
}

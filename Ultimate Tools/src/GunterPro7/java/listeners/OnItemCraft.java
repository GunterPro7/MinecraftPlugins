package listeners;

import costumItems.GrapplingHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import utils.Utils;

import java.util.Arrays;

public class OnItemCraft implements Listener {
    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        Player player = (Player) event.getView().getPlayer();
        Recipe recipe = event.getRecipe();

        if (recipe != null) {
            ItemStack result = recipe.getResult();
            if (result.getItemMeta() != null) {
                ItemMeta itemMeta = result.getItemMeta();
                if (itemMeta.hasCustomModelData() && !Utils.hasPlayerPermission(itemMeta.getCustomModelData(), player)) {
                    event.getInventory().setResult(null);
                }
            }
        }
    }
}

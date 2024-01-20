package listeners;

import costumItems.CodingBook;
import utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OnPlayerInteract implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getAction() == Action.LEFT_CLICK_BLOCK) {
            ItemStack holdingItem = event.getPlayer().getInventory().getItemInMainHand();
            if (event.getClickedBlock().getType() == Material.JUKEBOX) {
                Player player = event.getPlayer();
                if (CodingBook.isItemToRun(holdingItem)) {
                    event.setCancelled(true);
                    if (event.getPlayer().hasPermission("codingbook.use")) {
                        CodingBook.runCodingBook(player, holdingItem);
                    } else {
                        player.sendMessage(ChatColor.RED + Config.NO_PERMISSION);
                    }
                } else if (CodingBook.isItemToConvert(holdingItem)) {
                    player.sendMessage(ChatColor.RED + Config.CONVERT_CODINGBOOK_NEEDED);
                    event.setCancelled(true);
                }
            }
        }
    }
}

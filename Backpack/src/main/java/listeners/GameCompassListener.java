package listeners;

import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Bukkit.getServer;

public class GameCompassListener implements Listener {
    private Main plugin;

    public GameCompassListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            String playerName = player.getName();
            // Überprüfen, ob das geklickte Item ein Kompass ist
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) {
                return;
            }
            ItemMeta itemMeta = clickedItem.getItemMeta();
            if (itemMeta == null || !itemMeta.hasDisplayName()) {
                return;
            }
            if (itemMeta.getDisplayName().equals("Farmwelt") && clickedItem.getType() == Material.GRASS_BLOCK) {
                mvtpPlayer(playerName, "farming_world");
            } else if (itemMeta.getDisplayName().equals("Bauwelt") && clickedItem.getType() == Material.OAK_LOG) {
                mvtpPlayer(playerName, "world");
            } else if (itemMeta.getDisplayName().equals("Hub") && clickedItem.getType() == Material.COMPASS) {
                mvtpPlayer(playerName, "hub1");
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        String title = ChatColor.translateAlternateColorCodes('&', "&6Welcome &l" + playerName + '!');
        String subTitle = ChatColor.translateAlternateColorCodes('&', "&r&7Have fun :D");
        getServer().dispatchCommand(getServer().getConsoleSender(), "title " + playerName + " title " + '"' + title + '"');
        getServer().dispatchCommand(getServer().getConsoleSender(), "title " + playerName + " subtitle " + '"' + subTitle + '"');
    }

    private void mvtpPlayer(String playerName, String destination) {
        getServer().dispatchCommand(getServer().getConsoleSender(), "mvtp " + playerName + " " + destination);
    }
}

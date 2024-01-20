package listeners;

import costumItems.GrapplingHook;
import costumItems.CustomItemData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import utils.Utils;

public class OnPlayerFishEvent implements Listener {
    @EventHandler
    public void onPlayerFishingBack(PlayerFishEvent event) {
        PlayerFishEvent.State state = event.getState();
        if (state == PlayerFishEvent.State.REEL_IN || state == PlayerFishEvent.State.IN_GROUND) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (GrapplingHook.isGrapplingHook(item)) {
                if (!player.hasPermission("ultimatetools.use.grappling_hook")) {
                    Utils.sendNoPermissionMessage(player);
                    return;
                }
                if (GrapplingHook.isCooldownOver(event.getPlayer())) {
                    CustomItemData.lastGrapplingHook.put(player, System.currentTimeMillis());
                    FishHook fishHook = event.getHook();
                    Location hookLoc = fishHook.getLocation();
                    Location playerLoc = event.getPlayer().getLocation();
                    double verticalDistance = playerLoc.getY() - hookLoc.getY();
                    double horizontalDistance = playerLoc.distance(hookLoc);
                    double totalDistance = Math.sqrt(Math.pow(verticalDistance, 2) + Math.pow(horizontalDistance, 2));
                    GrapplingHook.knockPlayer(player, totalDistance);
                    if (state == PlayerFishEvent.State.REEL_IN) {
                        short currentDurability = item.getDurability();
                        item.setDurability((short) (currentDurability + 1));
                    }
                } else {
                    double timeRemaining = Utils.round(CustomItemData.grapplingHookCD - ((System.currentTimeMillis() - CustomItemData.lastGrapplingHook.get(player)) / 1000.0), -1);
                    Utils.sendCooldownMessage(player, timeRemaining);
                }
            }
        }
    }
}

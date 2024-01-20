package listeners;

import costumItems.CustomItemData;
import costumItems.TeddyBear;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class OnPlayerEnterBed implements Listener {
    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (TeddyBear.isTeddyBear(event.getPlayer().getInventory().getItemInMainHand())) {
            Player player = event.getPlayer();
            if (player.getWorld().getTime() > 12550) {
                if (TeddyBear.isCooldownOver(player)) {
                    CustomItemData.lastBasicTeddy.put(player, System.currentTimeMillis());
                    Location bedLocation = event.getBed().getLocation();

                    player.sleep(bedLocation, true);
                    event.setCancelled(true);
                }
            }
        }
    }
}

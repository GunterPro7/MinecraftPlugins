package listeners;

import costumItems.Hyperion;
import costumItems.InfinityBoom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class OnPlayerDamage implements Listener {
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            Player player = (Player) event.getEntity();
            ItemStack curItem = player.getInventory().getItemInMainHand();

            if (InfinityBoom.isInfinityBoom(curItem) || Hyperion.isHyperion(curItem)) {
                event.setCancelled(true);
            }
        }
    }
}

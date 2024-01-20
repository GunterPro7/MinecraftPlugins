package listeners;

import costumItems.InfinityLavaBucket;
import costumItems.InfinityWaterBucket;
import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import utils.Utils;

public class PlayerEmptyBucketEvent implements Listener {
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        Player player = event.getPlayer();

        int heldSlot = event.getPlayer().getInventory().getHeldItemSlot();

        ItemStack bucket = event.getItemStack();
        if (InfinityWaterBucket.isInfinityWaterBucket(item)) {
            if (!player.hasPermission("ultimatetools.use.infinity_water_bucket")) {
                Utils.sendNoPermissionMessage(player);
                return;
            }
            //player.playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1f, 1f);

            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                player.getInventory().setItem(heldSlot, InfinityWaterBucket.item);
                player.updateInventory();
            }, 1);

            player.updateInventory();
        }
        if (InfinityLavaBucket.isInfinityLavaBucket(item)) {
            if (!player.hasPermission("ultimatetools.use.infinity_lava_bucket")) {
                Utils.sendNoPermissionMessage(player);
                return;
            }
            //player.playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1f, 1f);

            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                player.getInventory().setItem(heldSlot, InfinityLavaBucket.item);
                player.updateInventory();
            }, 1);

            player.updateInventory();
        }
    }

}

package listeners;

import costumItems.CustomItemData;
import costumItems.IceWand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import utils.Utils;

public class OnPlayerInteractEntity implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getHand() == EquipmentSlot.HAND && event.getRightClicked() instanceof Player && IceWand.isIceWand(event.getPlayer().getItemInHand())) {
            Player player = event.getPlayer();
            Player clickedPlayer = (Player) event.getRightClicked();
            if (IceWand.isCooldownOver(player)) {
                CustomItemData.lastIceWandFreeze.put((Player) event.getRightClicked(), System.currentTimeMillis());
                CustomItemData.lastIceWand.put(player, System.currentTimeMillis());
                player.sendMessage("§aYou successfully froze §r" + clickedPlayer.getName() + " §afor §r" + CustomItemData.iceWandDuration + " §aseconds!");
            } else {
                double timeRemaining = Utils.round(CustomItemData.iceWandCD - ((System.currentTimeMillis() - CustomItemData.lastIceWand.get(player)) / 1000.0), -1);
                Utils.sendCooldownMessage(player, timeRemaining);
            }
        }
    }
}

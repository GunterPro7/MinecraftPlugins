package listeners;

import costumItems.CustomItemData;
import costumItems.InfinityPickaxe;
import costumItems.ThreeByThreeSuperTool;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import utils.Utils;

public class OnBlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // 3x3 Pickaxe
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (ThreeByThreeSuperTool.isThreeByThreePickaxe(item) && Utils.canBreakBlock(block)) {
            if (!player.hasPermission("ultimatetools.use.three_by_three_super_tool")) {
                Utils.sendNoPermissionMessage(player);
                return;
            }
            World world = block.getWorld();
            Location location = block.getLocation();
            int range = 1;

            boolean shouldRemoveDurability = false;
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    for (int z = -range; z <= range; z++) {
                        Block relativeBlock = world.getBlockAt(location.clone().add(x, y, z));
                        if (Utils.canBreakBlock(relativeBlock)) {
                            relativeBlock.breakNaturally(item);
                            shouldRemoveDurability = true;
                        }
                    }
                }
            }
            if (shouldRemoveDurability && player.getGameMode() != GameMode.CREATIVE) {
                item.setDurability((short) (item.getDurability()+1));
            }
        }

        // Infinity Pickaxe
        if (InfinityPickaxe.isInfinityPickaxe(item)) {
            Location playerLoc = player.getLocation();
            if (playerLoc.distance(block.getLocation()) > CustomItemData.infinityPickaxeRange) {
                event.setCancelled(true);
            }
        }
    }
}

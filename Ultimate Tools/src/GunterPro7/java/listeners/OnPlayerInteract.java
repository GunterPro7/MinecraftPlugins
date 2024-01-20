package listeners;

import costumItems.*;
import main.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import utils.Utils;

import java.util.HashMap;
import java.util.Timer;

public class OnPlayerInteract implements Listener {
    final Timer timer = new Timer();
    private HashMap<Player, Boolean> loopRunning = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (InfinityPearl.isInfinityPearl(item)) {
                // Check if the player has the necessary permissions or conditions to throw an ender pearl
                if (!player.isSneaking() && !player.isInsideVehicle()) {
                    if (!player.hasPermission("ultimatetools.use.infinity_pearl")) {
                        Utils.sendNoPermissionMessage(player);
                        event.setCancelled(true);
                        return;
                    }
                    if (InfinityPearl.isCooldownOver(player)) {
                        CustomItemData.lastPearlHook.put(player, System.currentTimeMillis());
                        if (!(player.getGameMode() == GameMode.CREATIVE) && !(player.getGameMode() == GameMode.SPECTATOR)) {
                            player.getInventory().addItem(InfinityPearl.item);
                        }
                    } else {
                        event.setCancelled(true);
                        double timeRemaining = Utils.round(CustomItemData.pearlCD - ((System.currentTimeMillis() - CustomItemData.lastPearlHook.get(player)) / 1000.0), -1);

                        Utils.sendCooldownMessage(player, timeRemaining);
                    }

                } else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You cannot throw ender pearls in this situation!");
                }
            } else if (InfinityBoom.isInfinityBoom(item)) {
                // Check if the player has the necessary permissions or conditions to throw an ender pearl
                if (player.hasPermission("ultimatetools.use.infinity_boom")) {
                    if (InfinityBoom.isCooldownOver(player)) {
                        CustomItemData.lastBoom.put(player, System.currentTimeMillis());
                        if (!(player.getGameMode() == GameMode.CREATIVE) && !(player.getGameMode() == GameMode.SPECTATOR)) {
                            player.getInventory().addItem(InfinityBoom.item);
                        }
                        Location loc = event.getClickedBlock().getLocation();
                        World world = loc.getWorld();
                        world.createExplosion(loc, (float) CustomItemData.boomPower, true, true);
                    } else {
                        event.setCancelled(true);
                        double timeRemaining = Utils.round(CustomItemData.boomCD - ((System.currentTimeMillis() - CustomItemData.lastBoom.get(player)) / 1000.0), -1);

                        Utils.sendCooldownMessage(player, timeRemaining);
                    }

                } else {
                    event.setCancelled(true);
                    Utils.sendNoPermissionMessage(player);
                }
            } else if (SuperLeap.isSuperLeap(item)) {
                // Check if the player has the necessary permissions or conditions to throw an ender pearl
                if (player.hasPermission("ultimatetools.use.pearl_leap")) {
                    if (SuperLeap.isCooldownOver(player)) {
                        player.openInventory(SuperLeap.createLeapInv(player));
                    } else {
                        double timeRemaining = Utils.round(CustomItemData.leapCD - ((System.currentTimeMillis() - CustomItemData.lastLeap.get(player)) / 1000.0), -1);

                        Utils.sendCooldownMessage(player, timeRemaining);
                    }
                    event.setCancelled(true);

                } else {
                    event.setCancelled(true);
                    Utils.sendNoPermissionMessage(player);
                }
            } else if (Hyperion.isHyperion(item)) {
                if (!player.hasPermission("ultimatetools.use.hyperion")) {
                    Utils.sendNoPermissionMessage(player);
                    return;
                }


                Location location = player.getLocation();
                Vector direction = location.getDirection().normalize();
                Location newLocation = location.add(direction.multiply(5));

                player.teleport(newLocation);


                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    Location loc = player.getLocation();
                    World world = loc.getWorld();
                    world.createExplosion(loc, 5F, false, true);

                    player.setFallDistance(0);
                }, 1L);

                event.setCancelled(true);
            } else if (FlameThrower.isFlameThrower(item)) {
                if (!player.hasPermission("ultimatetools.use.flame_thrower")) {
                    Utils.sendNoPermissionMessage(player);
                    return;
                }
                if (!FlameThrower.isCooldownOver(player)) {
                    double timeRemaining = Utils.round(CustomItemData.flameThrowerCD - ((System.currentTimeMillis() - CustomItemData.lastFlameThrower.get(player)) / 1000.0), -1);
                    Utils.sendCooldownMessage(player, timeRemaining);
                    return;
                }

                // Spawn fire entities in the direction the player is facing
                Location location = player.getLocation();
                Vector direction = location.getDirection();
                int range = 10;
                location.add(direction);
                location.getWorld().spawn(location, Fireball.class);
                CustomItemData.lastFlameThrower.put(player, System.currentTimeMillis());
            }
        }

        if (Terminator.isTerminator(item)) {
            if (!player.hasPermission("ultimatetools.use.terminator")) {
                Utils.sendNoPermissionMessage(player);
                event.setCancelled(true);
                return;
            }
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
                Terminator.shootArrows(player);
            } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                Terminator.shootArrows(player);
                if (loopRunning.get(player) != null) {
                    loopRunning.put(player, null);
                } else {
                    loopRunning.put(player, true);
                    runRightClickEvent(player);
                }
            }
        } else if (LightningBolt.isLightningBolt(item)) {
            if (!player.hasPermission("ultimatetools.use.lightning_bolt")) {
                Utils.sendNoPermissionMessage(player);
                return;
            }
            if (LightningBolt.isCooldownOver(player)) {
                // Get the block that the player is looking at
                Block block = player.getTargetBlock(null, 256);

                // Get the location of the block
                Location blockLocation = block.getLocation();

                // Create a lightning bolt at the location of the block
                block.getWorld().strikeLightning(blockLocation);

                CustomItemData.lastBolt.put(player, System.currentTimeMillis());
            } else {
                double timeRemaining = Utils.round(CustomItemData.boltCD - ((System.currentTimeMillis() - CustomItemData.lastBolt.get(player)) / 1000.0), -1);

                Utils.sendCooldownMessage(player, timeRemaining);
            }
        } else if (InfinityPickaxe.isInfinityPickaxe(player.getInventory().getItemInMainHand())) {
            if (!player.hasPermission("ultimatetools.use.infinity_pickaxe")) {
                Utils.sendNoPermissionMessage(player);
                return;
            }
            Block block = player.getTargetBlock(null, CustomItemData.infinityPickaxeRange);
            if (Utils.canBreakBlock(block)) {
                player.sendBlockChange(block.getLocation(), Material.AIR.createBlockData());
                player.breakBlock(block);
                player.getInventory().getItemInMainHand().setDurability((short) 0);
            }
        } else if (TeddyBear.isTeddyBear(event.getPlayer().getInventory().getItemInMainHand())) {
            if (event.getClickedBlock() != null && !event.getClickedBlock().getType().toString().endsWith("BED")) {
                event.setCancelled(true);
            }
        } else if (InfinityWand.isInfinityWand(item)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (InfinityWand.isCooldownOver(player) && InfinityWand.inventory.getItem(13) != null) {
                    ItemStack heldItem = InfinityWand.inventory.getItem(13);
                    if (!player.getInventory().contains(heldItem.getType()) && player.getGameMode() != GameMode.CREATIVE) {
                        player.sendMessage(ChatColor.RED + "You ran out of Blocks!");
                        return;
                    }
                    if (!player.hasPermission("ultimatetools.use.infinity_wand")) {
                        Utils.sendNoPermissionMessage(player);
                        return;
                    }
                    Block block = player.getTargetBlock(null, (int) CustomItemData.infinityWandRange);
                    if (block.getType() == Material.AIR) {
                        return;
                    }

                    Location playerLoc = player.getLocation();
                    Location newLocation = new Location(player.getWorld(), 0, 0, 0);
                    newLocation.setX(Utils.checkCoords((int) Math.floor(playerLoc.getX()), block.getX(), false));
                    newLocation.setY(Utils.checkCoords((int) Math.floor(playerLoc.getY()), block.getY(), true));
                    newLocation.setZ(Utils.checkCoords((int) Math.floor(playerLoc.getZ()), block.getZ(), false));

                    if (newLocation.getX() == (int) Math.floor(playerLoc.getX()) && (newLocation.getY() == (int) Math.floor(playerLoc.getY()) || newLocation.getY() == (int) Math.floor(playerLoc.getY()) + 1) && newLocation.getZ() == (int) Math.floor(playerLoc.getZ())) {
                        return;
                    }
                    Block curBlock = block.getWorld().getBlockAt(newLocation);
                    if (curBlock.getType() != Material.AIR) {
                        return;
                    }

                    curBlock.setType(heldItem.getType());
                    BlockData blockData = curBlock.getBlockData();
                    Bukkit.getLogger().info(String.valueOf(blockData));
                    if (blockData instanceof Directional) {
                        Bukkit.getLogger().info("yea");
                        Directional directional = (Directional) blockData;
                        directional.setFacing(Utils.flipPlayerFacing(player.getFacing()));
                        curBlock.setBlockData(directional);
                    } else {
                        Bukkit.getLogger().info("no");
                    }

                    CustomItemData.lastInfinityWand.put(player, System.currentTimeMillis());
                    if (player.getGameMode() != GameMode.CREATIVE) {
                        player.getInventory().removeItem(heldItem);
                    }
                }
            } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                player.openInventory(InfinityWand.inventory);
            }
        } else if (ThreeByThreeSuperTool.isThreeByThreePickaxe(item) && event.getClickedBlock() != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!player.hasPermission("ultimatetools.use.three_by_three_super_tool")) {
                Utils.sendNoPermissionMessage(player);
                return;
            }
            World world = player.getWorld();
            Location location = event.getClickedBlock().getLocation();
            int range = 1;

            boolean shouldRemoveDurability = false;
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    for (int z = -range; z <= range; z++) {
                        Block relativeBlock = world.getBlockAt(location.clone().add(x, y, z));
                        Block blockAbove = world.getBlockAt(location.clone().add(x, y + 1, z));
                        if (blockAbove.getType() == Material.AIR && Utils.canBeFarmland(relativeBlock)) {
                            relativeBlock.setType(Material.FARMLAND);
                            shouldRemoveDurability = true;
                        }
                    }
                }
            }
            if (shouldRemoveDurability && player.getGameMode() != GameMode.CREATIVE) {
                item.setDurability((short) (item.getDurability() + 1));
            }
        }
    }

    private void runRightClickEvent(Player player) {
        ItemStack curItem = player.getInventory().getItemInMainHand();

        if (loopRunning.get(player) != null && curItem.getType() == Material.BOW && Terminator.isTerminator(curItem) && (player.getGameMode() == GameMode.CREATIVE || player.getInventory().contains(Material.ARROW))) {
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                Terminator.shootArrows(player);
                runRightClickEvent(player);
            }, (int) (CustomItemData.terminatorSpeed * 20));
        } else {
            loopRunning.put(player, null);
        }
    }
}

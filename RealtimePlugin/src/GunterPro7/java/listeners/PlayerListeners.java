package listeners;

import fragments.CharcoleFragment;
import fragments.EmeraldFragment;
import fragments.FarmingFragment;
import items.*;
import main.CooldownType;
import main.Main;
import main.PlayerInformations;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionType;
import utils.*;

import java.util.Collection;
import java.util.Objects;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (Config.isEnabled) {
            if (event.getEntity() instanceof Player) {
                double curDamage = event.getDamage();
                if (curDamage < 100000) {
                    curDamage *= 2;

                    for (ItemStack item : ((Player) event.getEntity()).getInventory().getArmorContents()) {
                        if (EternitysArmor.isUpgradedItem(item)) {
                            curDamage -= curDamage * 0.15;
                        }
                    }

                    if (curDamage < 0) {
                        curDamage = 0;
                    }
                    event.setDamage(curDamage);
                }
                PlayerInformations pi = Main.PLAYER_MAP.get(event.getEntity().getName());
                if (!pi.isStaminaCritical()) {
                    pi.setStamina(pi.getStamina() - 0.01d);
                }
            }
        }
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        if (Config.isEnabled) {
            Player player = event.getPlayer();
            PlayerInformations playerInformations = Main.PLAYER_MAP.get(player.getName());
            playerInformations.addExp(event.getAmount());
            Bukkit.getLogger().info("Levels per game: " + playerInformations.getExpInLevels());
            Bukkit.getLogger().info("Levels per PLAYER VAR: " + player.getLevel());
            int playerLevels = playerInformations.getExpInLevels();
            if (playerLevels != player.getLevel()) {
                player.setLevel(playerLevels);
            }
            Bukkit.getLogger().info(String.valueOf(event.getAmount()));

            event.setAmount(0);

        }
    }

    @EventHandler
    public void onItemEat(PlayerItemConsumeEvent event) {
        if (Config.isEnabled) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            if (item.getType() == Material.POTION) {
                ItemStack bottle = new ItemStack(Material.POTION);
                PotionMeta meta = (PotionMeta) bottle.getItemMeta();
                if (meta != null) {
                    if (meta.getBasePotionData().getType() == PotionType.UNCRAFTABLE) {
                        float newExp = player.getExp() + 0.25f;
                        if (newExp > 1f) {
                            newExp = 1f;
                        }
                        player.setExp(newExp);
                    }
                }
            } else if (AuspiciousRabbitStew.isItem(item)) {
                player.teleport(Worlds.WORLD_SKYBLOCK.getWorld().getSpawnLocation());
            }
            if (CustomItems.isSpecialItem(item)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (Config.isEnabled) {
            // Durst bar
            Player player = event.getPlayer();
            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                if (player.getExp() == 0f && !Main.MAP.contains(player)) {
                    Main.MAP.add(player);
                    Utils.loseHearts(player);
                    return;
                }

                Location loc = player.getLocation();

                Biome biome = player.getWorld().getBiome((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
                float newExp;

                if (biome == Biome.DESERT || biome == Biome.BADLANDS || biome == Biome.ERODED_BADLANDS || biome == Biome.WOODED_BADLANDS || biome == Biome.JUNGLE || biome == Biome.BAMBOO_JUNGLE || biome == Biome.SPARSE_JUNGLE || biome == Biome.SAVANNA || biome == Biome.SAVANNA_PLATEAU || biome == Biome.WINDSWEPT_SAVANNA) {
                    newExp = player.getExp() - 0.000125f;
                } else if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
                    newExp = player.getExp() - 0.00025f;
                } else {
                    newExp = player.getExp() - 0.00005f;
                }

                if (newExp < 0f) {
                    newExp = 0f;
                }
                player.setExp(newExp);
            }
            if (player.isSprinting() || player.isSwimming() || player.isClimbing() || Main.PLAYER_MAP.get(player.getName()).isStaminaCritical()) {
                PlayerInformations pi = Main.PLAYER_MAP.get(player.getName());
                pi.setStamina(pi.getStamina() - 0.0001d);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Bukkit.getLogger().info(event.getDeathMessage());
        if (Config.isEnabled) {
            Player player = event.getEntity();
            event.setDroppedExp(0);

            Bukkit.getLogger().info(player.getWorld().getName());
            Bukkit.getLogger().info(Worlds.WORLD_SKYBLOCK.getWorld().getName());

            if (player.getWorld() == Worlds.WORLD_SKYBLOCK.getWorld() && event.getDeathMessage().endsWith(" fell out of the world")) {
                event.setKeepInventory(true);
                event.setKeepLevel(true);
                return;
            }


            Main.PLAYER_MAP.get(player.getName()).setExp(0);

            if (player.getExp() == 0f && Objects.equals(event.getDeathMessage(), player.getName() + " died")) {
                event.setDeathMessage(player.getName() + " has died of thirst.");
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        // Den Spieler bekommen
        Player player = event.getPlayer();

        // Die neuen EXP im PlayerRespawnEvent setzen
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> player.setExp(0.33333f), 1);

        // Falls in Boss World -> in world
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            if (player.getWorld().getName().equals("world_boss_area")) {
                player.teleport(Worlds.WORLD.getWorld().getSpawnLocation());
            }
        }, 1);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Allgemeine Dinge
        Player player = event.getPlayer();
        PlayerInformations pi = Main.PLAYER_MAP.get(player.getName());
        if (pi.isSitting()) {
            event.setCancelled(true);
            return;
        }
        pi.setStamina(pi.getStamina() - 0.005d);

        Material material = event.getBlock().getType();
        String blockName = material.name();
        Location location = event.getBlock().getLocation();

        boolean allow = true;
        for (MetadataValue value : event.getBlock().getMetadata("invalid")) {
            // Überprüfe, ob die MetadataValue von deinem Plugin stammt
            if (value.getOwningPlugin().equals(Main.getPlugin())) {
                // Erhalte den Wert der MetadataValue
                if (value.asBoolean()) {
                    allow = false;
                    break;
                }
            }
        }

        if (RubyPickaxe.isItem(player.getInventory().getItemInMainHand()) && material == Material.EMERALD_ORE || material == Material.DEEPSLATE_EMERALD_ORE) {
            if (allow && Main.RANDOM.nextInt(4) == 1) {
                location.getWorld().dropItem(location, new EmeraldFragment());
            }
        } else if (blockName.contains("ORE")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            item.setDurability((short) (item.getDurability() + 16));
            event.setDropItems(Main.RANDOM.nextBoolean());
        } else if (material == Material.CHEST || material == Material.CHEST_MINECART) {
            event.getBlock().setType(Material.AIR);
            event.setCancelled(true);
        } else if (material == Material.ANCIENT_DEBRIS) {
            event.setDropItems(Main.RANDOM.nextInt(5) == 1);
        } else if (blockName.contains("LOG")) {
            if (allow && Main.RANDOM.nextInt(256) == 1) {
                location.getWorld().dropItem(location, new CharcoleFragment());
            }
        } else if (Utils.isCrop(event.getBlock().getType())) {
            BlockData blockData = event.getBlock().getBlockData();
            if (blockData instanceof Ageable) {
                Ageable ageable = (Ageable) blockData;
                if (ageable.getAge() == ageable.getMaximumAge()) {
                    if (Main.RANDOM.nextInt(1250) == 1) {
                        Bukkit.getLogger().info(event.getBlock().getType().name());
                        player.getWorld().dropItem(event.getBlock().getLocation(), new FarmingFragment(Utils.convertBlockCropToItem(event.getBlock().getType())));
                    }
                }
            }

            if (SilkyHoe.isItem(player.getInventory().getItemInMainHand())) {
                Material itemToReplant = event.getBlock().getType();
                Collection<ItemStack> drops = event.getBlock().getDrops();
                boolean canReplant = false;
                Location loc = event.getBlock().getLocation();
                World world = loc.getWorld();
                for (ItemStack curItem : drops) {
                    if (curItem.getType() == Utils.cropMap.get(itemToReplant)) {
                        curItem.setAmount(curItem.getAmount() - 1);
                        canReplant = true;
                    }
                }

                if (canReplant) {
                    Utils.setBlockNextTick(event.getBlock().getLocation(), itemToReplant);
                    event.setDropItems(false);
                    for (ItemStack drop : drops) {
                        if (drop.getAmount() != 0) {
                            world.dropItem(loc, drop);
                        }
                    }
                }
            }
        }

        // Special Items
        ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
        Block block = event.getBlock();
        if (EternitysPickaxe.isItem(mainItem) && Utils.canBreakBlock(block)) {
            World world = block.getWorld();
            int range = 1;

            boolean shouldRemoveDurability = false;
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    for (int z = -range; z <= range; z++) {
                        Block relativeBlock = world.getBlockAt(location.clone().add(x, y, z));
                        if (Utils.canBreakBlock(relativeBlock)) {
                            relativeBlock.breakNaturally(mainItem);
                            shouldRemoveDurability = true;
                        }
                    }
                }
            }
            if (shouldRemoveDurability && player.getGameMode() != GameMode.CREATIVE) {
                mainItem.setDurability((short) (mainItem.getDurability() + 1));
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlockAgainst().getType() == Material.LODESTONE && event.getBlock().getType() == Material.FIRE) {
            Player player = event.getPlayer();
            if (Main.PLAYER_MAP.get(player.getName()).isCooldownOver(CooldownType.BEACON_EFFECT) && !player.getWorld().getName().equals("world_boss_area")) {
                Location location = event.getBlockAgainst().getLocation();
                World world = location.getWorld();
                location.setY(location.getY() - 1);
                int x = (int) location.getX();
                int y = (int) location.getY();
                int z = (int) location.getZ();

                world.getBlockAt(x, y+2, z).setType(Material.FIRE);

                boolean allow = true;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if ((!(i == 0 && j == 0))) {
                            Material type = world.getBlockAt(x+i, y, z+j).getType();
                            Material typeAbove = world.getBlockAt(x+i, y+1, z+j).getType();
                            if (type != Material.DIAMOND_BLOCK && type != Material.NETHERITE_BLOCK || typeAbove != Material.AIR && typeAbove != Material.FIRE) {
                                allow = false;
                            } else {
                                world.getBlockAt(x+i, y+1, z+j).setType(Material.FIRE);
                            }
                        } else {
                            if (world.getBlockAt(location).getType() != Material.NETHERITE_BLOCK) {
                                allow = false;
                            }
                        }
                    }
                }

                Bukkit.getLogger().info(String.valueOf(allow));
                if (allow) {
                    Main.PLAYER_MAP.get(player.getName()).setCooldown(CooldownType.BEACON_EFFECT);
                    BeaconEffect effect = new BeaconEffect(player, player.getWorld(), location.getX()+0.5d, location.getY(), location.getZ()+0.5d, 256.0D, Particle.REDSTONE, new Particle.DustOptions(Color.WHITE, 1.0F), 100);
                } else {
                    event.setCancelled(true); // TODO man kann hier noch spammen, wenn man mehjr als einmal das Feuerzeug hier rechtsclicked... Irgendeine Abfrage etc muss man da noch machen
                }
            }
        }

        PlayerInformations pi = Main.PLAYER_MAP.get(event.getPlayer().getName());
        pi.setStamina(pi.getStamina() - 0.005d);

        if (CustomItems.isSpecialItem(event.getItemInHand())) {
            event.setCancelled(true);
        }
        Block block = event.getBlockPlaced();
        Material blockType = block.getType();
        if (blockType.name().contains("LOG") || blockType.name().contains("EMERALD_ORE")) {
            block.setMetadata("invalid", Main.FIXED_METADATA);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getExp() == 0f) {
            player.setExp(0.33333f);
        }
        Main.PLAYER_MAP.put(player.getName(), new PlayerInformations(player));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Main.PLAYER_MAP.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        World.Environment environment = world.getEnvironment();
        long time = world.getTime();
        if (world.getName().equals("world_boss_area") && ((time > 12541 && time < 23458) || world.hasStorm())) {
            PlayerInformations pi = Main.PLAYER_MAP.get(player.getName());
            if (pi.isCooldownOver(CooldownType.BED_RETURNING)) {
                pi.setCooldown(CooldownType.BED_RETURNING);
                player.sendMessage(ChatColor.GRAY + "You are now returning to the real World!"); // TODO "Respawnpoint set" remove
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    if (player.isSleeping()) {
                        player.teleport(Worlds.WORLD.getWorld().getSpawnLocation());
                    }
                }, 140);
            }
        } else if (environment != World.Environment.NETHER && environment != World.Environment.THE_END) {
            if (world.getName().equals("world_boss_area")) {
                player.sendMessage(ChatColor.GRAY + "You may not return yet!");
            } else {
                Main.PLAYER_MAP.get(player.getName()).doStaminaInterval(true);
                player.sendMessage(ChatColor.GRAY + "You can't sleep in this world!");
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Monster) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                // Das Entity brennt
                event.setCancelled(true); // Um den Schaden zu verhindern
                entity.setFireTicks(0); // Um das Feuer zu löschen
            }
        }
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            PlayerInformations pi = Main.PLAYER_MAP.get((event.getDamager()).getName());
            pi.setStamina(pi.getStamina() - 0.0045d);
        }
    }
}

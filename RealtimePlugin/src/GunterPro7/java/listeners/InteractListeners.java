package listeners;

import fragments.CraftingFragment;
import fragments.WitherEssence;
import items.*;
import main.CooldownType;
import main.Main;
import main.PlayerInformations;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import utils.*;


import java.util.*;

public class InteractListeners implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getView().getPlayer();
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null) {
            if (clickedInventory instanceof AnvilInventory) {
                AnvilInventory anvilInventory = (AnvilInventory) event.getClickedInventory();
                ItemStack baseItem = anvilInventory.getItem(0);
                ItemStack upgradeItem = anvilInventory.getItem(1);
                ItemStack upgradedItem = CustomItems.upgrade(baseItem);
                if (event.getSlot() == 2) {
                    if (upgradedItem != event.getCurrentItem()) {
                        if (WitherEssence.isItem(upgradeItem) && upgradeItem.getAmount() > 7) {
                            if (event.isShiftClick()) {
                                player.getInventory().addItem(upgradedItem);
                            } else {
                                player.setItemOnCursor(upgradedItem);
                            }
                            anvilInventory.setItem(0, null);
                            upgradeItem.setAmount(upgradeItem.getAmount() - 8);
                            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
                        }
                    }
                }
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    Main.PLAYER_MAP.get(player.getName()).setExp(Utils.getExp(player.getLevel()));
                    Bukkit.getLogger().info(String.valueOf(player.getLevel()));
                }, 1);
            } else if (clickedInventory.getType() == InventoryType.MERCHANT) {
                if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                    ItemStack result = event.getCurrentItem();
                    if (result != null) {
                        int chance = 0;

                        for (ItemStack item : player.getInventory().getArmorContents()) {
                            if (RubyArmor.isItem(item)) {
                                chance += 20;
                            }
                        }

                        if (Utils.chance(chance)) {
                            player.getInventory().addItem(result);
                        }
                    }
                }
            } else if (event.getView().getType() == InventoryType.WORKBENCH) {
                if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                    ItemStack result = event.getCurrentItem();
                    if ((CustomItems.isSpecialItem(result))) {
                        if (Main.RANDOM.nextInt(4) == 1) {
                            player.getInventory().addItem(new CraftingFragment());
                        }
                    }
                }
            } else if (ChatColor.stripColor(event.getView().getTitle()).equals("abc Auto Crafter cba")) {
                if (AutoCrafter.isItem(event.getCurrentItem())) {
                    event.setCancelled(true);
                    return;
                }
                if (clickedInventory == event.getView().getTopInventory()) {
                    int slot = event.getSlot();
                    if (slot == 22) {
                        ItemStack item = clickedInventory.getItem(22);
                        item.setType(item.getType() == Material.RED_CONCRETE ? Material.GREEN_CONCRETE : Material.RED_CONCRETE);
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setDisplayName(ChatColor.stripColor(itemMeta.getDisplayName()).equals("Enabled") ? ChatColor.GREEN + "Disabled" : ChatColor.GREEN + "Enabled");
                        item.setItemMeta(itemMeta);
                        clickedInventory.setItem(22, item);
                        event.setCancelled(true);
                    } else if (slot == 11 || slot == 15) {
                        ItemStack item = clickedInventory.getItem(slot);
                        if (item != null && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 404340) {
                            clickedInventory.setItem(slot, item);
                            event.setCurrentItem(null);     // TODO auf 3x3 matrix vergrößern     // Später kann man dann mit Bukkit.getServer().getRecipesFor(output) überorüfen, ob es mit den Input übereinststimmt
                        } else {
                            event.setCancelled(true);
                            clickedInventory.setItem(slot, Utils.getItemByAutoCrafterInventory(slot));
                            player.setItemOnCursor(item);
                        }
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemEnchant(EnchantItemEvent event) {
        Player player = event.getEnchanter().getPlayer();
        Bukkit.getLogger().info("Hey world!");
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            Main.PLAYER_MAP.get(player.getName()).setExp(Utils.getExp(player.getLevel()));
            Bukkit.getLogger().info(String.valueOf(player.getLevel()));
        }, 1);



    }

    /*@EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (Config.isEnabled) {
            if (CustomItems.isSpecialItem(event.getInventory().getItem(1))) {
                Inventory inventory = event.getInventory();

                inventory.setContents(new ItemStack[10]);

            }
        }
    }*/

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        if (CustomItems.isSpecialItem(event.getInventory().getItem(0))) {
            AnvilInventory anvilInventory = event.getInventory();
            ItemStack baseItem = anvilInventory.getItem(0);
            ItemStack upgradeItem = anvilInventory.getItem(1);
            if (WitherEssence.isItem(upgradeItem) && upgradeItem.getAmount() > 7) {
                event.setResult(CustomItems.upgrade(baseItem));
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (Config.isEnabled) {
            if (CustomItems.isSpecialItem(event.getItem())) {
                Action action = event.getAction();
                ItemStack item = event.getItem();
                if (EternitysPickaxe.isUpgradedItem(item) && event.getClickedBlock() != null) {
                    if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                        Location clickedLocation = event.getClickedBlock().getLocation();
                        World world = clickedLocation.getWorld();
                        if (Main.PLAYER_MAP.get(event.getPlayer().getName()).isCooldownOver(CooldownType.ETERNITYS_PICKAXE_EXPLOSION)) {
                            Main.PLAYER_MAP.get(event.getPlayer().getName()).setCooldown(CooldownType.ETERNITYS_PICKAXE_EXPLOSION);
                            world.createExplosion(clickedLocation, 15, false, true, event.getPlayer());
                        }
                    }
                } else if (EternitysEdge.isItem(item)) {
                    if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                        Player player = event.getPlayer();
                        if (event.getPlayer().isSneaking()) {
                            if (Main.PLAYER_MAP.get(player.getName()).isCooldownOver(CooldownType.ETERNITYS_EDGE_TELEPORT)) {
                                if (EternitysEdge.isUpgradedItem(item)) {
                                    Location location = player.getLocation();
                                    Vector direction = location.getDirection().normalize();
                                    Location newLocation = location.add(direction.multiply(12));

                                    Block block = newLocation.getBlock();
                                    newLocation.setY(newLocation.getY() + 1);
                                    Bukkit.getLogger().info(block.getType().name());
                                    Bukkit.getLogger().info(newLocation.getBlock().getType().name());
                                    if (block.getType() == Material.AIR && newLocation.getBlock().getType() == Material.AIR) {
                                        player.teleport(newLocation);
                                        Main.PLAYER_MAP.get(player.getName()).setCooldown(CooldownType.ETERNITYS_EDGE_TELEPORT);
                                    }
                                }
                            }
                        } else {
                            Block clickedBlock = player.getTargetBlock(Main.IGNORED_BLOCKS, 128);
                            if (clickedBlock.getType() != Material.AIR) {
                                Location clickedLocation = clickedBlock.getLocation();

                                if (Main.PLAYER_MAP.get(player.getName()).isCooldownOver(CooldownType.ETERNITYS_EDGE_THUNDER)) {
                                    player.getWorld().strikeLightning(clickedLocation);
                                    Main.PLAYER_MAP.get(player.getName()).setCooldown(CooldownType.ETERNITYS_EDGE_THUNDER);
                                }
                            }

                        }
                    }
                } else if (IllusionerWand.isItem(item) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
                    Player player = event.getPlayer();
                    if (Main.PLAYER_MAP.get(player.getName()).isCooldownOver(CooldownType.ILLUSIONER_WAND)) {
                        IllusionerWand.damageNearbyEntitys(player);
                        Main.PLAYER_MAP.get(player.getName()).setCooldown(CooldownType.ILLUSIONER_WAND);
                    }
                } else if (AutoCrafter.isItem(item)) {
                    if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                        Player player = event.getPlayer();
                        String uuid = item.getItemMeta().getPersistentDataContainer().get(AutoCrafter.AUTO_CRAFTER_KEY, PersistentDataType.STRING);
                        if (AutoCrafter.UUID_MAP.containsKey(uuid)) {
                            player.openInventory(AutoCrafter.UUID_MAP.get(uuid));
                        } else {
                            AutoCrafter.UUID_MAP.put(uuid, AutoCrafter.createAutoCrafterInv());
                            player.openInventory(AutoCrafter.UUID_MAP.get(uuid));
                        }
                    }
                }
            } else if (event.getAction() == Action.PHYSICAL) {
                Block clickedBlock = event.getClickedBlock();
                for (MetadataValue value : clickedBlock.getMetadata("explosive")) {
                    // Überprüfe, ob die MetadataValue von deinem Plugin stammt
                    if (value.getOwningPlugin().equals(Main.getPlugin())) {
                        // Erhalte den Wert der MetadataValue
                        if (value.asBoolean()) {
                            Utils.checkUntilWentOf(event.getClickedBlock());
                            break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCrafting(PrepareItemCraftEvent event) {
        CraftingInventory craftingInventory = event.getInventory();
        ItemStack[] itemStacks = craftingInventory.getMatrix();

        if (event.getRecipe() != null && craftingInventory.getResult() != null) {
            for (ItemStack itemStack : itemStacks) {
                if (CustomItems.isSpecialItem(itemStack)) {
                    craftingInventory.setResult(null);
                    return;
                }
            }
            return;
        }

        ItemStack result = null;
        List<CustomInventory> items = CraftingInventorys.ITEM_CRAFT_MAP;

        for (CustomInventory customInventory : items) {
            if (CraftingInventorys.isSameCraftingRecipe(itemStacks, customInventory.getMatrix())) {
                result = customInventory.getResult();
                break;
            }
        }

        craftingInventory.setResult(result);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (Main.PLAYER_MAP.get(player.getName()).isCooldownOver(CooldownType.ETERNITYS_ARMOR_HEAL)) {
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (!EternitysArmor.isItem(item)) {
                    return;
                }
            }

            double maxHealth = player.getMaxHealth();
            double newHealth = player.getHealth() + 8;
            if (newHealth > maxHealth) {
                newHealth = maxHealth;
            }

            player.setHealth(newHealth);
            Main.PLAYER_MAP.get(player.getName()).setCooldown(CooldownType.ETERNITYS_ARMOR_HEAL);
        }
    }

    @EventHandler
    public void wqweqwe(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        Bukkit.getLogger().info("Hey1");
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Bukkit.getLogger().info("Hey2");
            if (clickedBlock != null &&  clickedBlock.getBlockData() instanceof Stairs && ((Stairs) clickedBlock.getBlockData()).getHalf() == Bisected.Half.BOTTOM) {
                Bukkit.getLogger().info("Hey3");
                if (clickedBlock.getType().name().contains("STAIRS") && !player.isSneaking()) {
                    Bukkit.getLogger().info("Hey4");
                    Utils.removePigForChairs(player.getLocation());
                    Location sittingLocation = clickedBlock.getLocation().add(0.5, -0.4, 0.5);

                    // Spawn an invisible pig entity at the sitting location
                    Pig pig = (Pig) player.getWorld().spawnEntity(sittingLocation, EntityType.PIG);
                    pig.setVelocity(player.getLocation().getDirection());
                    pig.setCustomName("HARDCORE_PLUGIN_CHAIR_SIT");
                    pig.setCustomNameVisible(false);
                    pig.setAI(false);
                    pig.setInvulnerable(true);
                    pig.setGravity(false);
                    pig.setSilent(true);
                    pig.setCollidable(false);
                    pig.setMaxHealth(1); // Todo googlen was die nicht depricated methode ist!
                    pig.setInvisible(true);

                    BlockFace blockFace = Utils.getStairsBlockFace(clickedBlock);
                    pig.setRotation(Utils.blockFaceToYaw(blockFace), 0);

                    PlayerInformations pi = Main.PLAYER_MAP.get(player.getName());

                    pi.setSitting(true);
                    pi.doStaminaInterval(false); // TODO bei manchen stairs "sitzt man nicht richtig", noch fixen
                    pi.doSitInterval(sittingLocation, player);

                    pig.addPassenger(player);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) {
            if (event.getPlayer().getLocation().getBlock().getType().name().contains("STAIRS")) {
                Utils.removePigForChairs(event.getPlayer().getLocation());
                Main.PLAYER_MAP.get(event.getPlayer().getName()).setSitting(false); // TODO das geht noch nicht ganz weil man nicht immer auf diesen block drauf steht, der überprüft wird
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        // Get the chunk that was loaded
        Chunk chunk = event.getChunk();

        // Get the world of the chunk
        World world = chunk.getWorld();

        if (world == Worlds.WORLD_BOSS.getWorld() && chunk.getX() == 0 && chunk.getZ() == 0) {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {

                }
            }
        }

        // Check if the chunk is new or not
        boolean isNewChunk = event.isNewChunk();

        // If the chunk is new, update the lava sources
        if (isNewChunk) {
            // Update the lava blocks
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    for (int k = 0; k < world.getMaxHeight(); k++) {
                        Block block = world.getBlockAt(chunk.getX() * 16 + i, k, chunk.getZ() * 16 + j);
                        if (block.getType() == Material.LAVA) {
                            // Get the block state
                            BlockState state = world.getBlockAt(block.getX(), block.getY() - 1, block.getZ()).getState();
                            // Update the block state with force and physics
                            state.update(true, true);

                            Bukkit.getLogger().info("Updated Lava!");
                        }
                    }
                }
            }
        }
    }

}


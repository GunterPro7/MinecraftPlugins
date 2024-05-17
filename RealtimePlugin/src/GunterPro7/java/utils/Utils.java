package utils;

import fragments.*;
import items.*;
import main.Main;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class Utils {
    private static final BukkitScheduler bukkitScheduler = Bukkit.getScheduler();

    public static void loseHearts(Player player) {
        bukkitScheduler.runTaskLater(Main.getPlugin(), () -> {
            if (player.getExp() == 0f && player.getHealth() != 0) {
                player.damage(0.5);

                loseHearts(player);
            } else {
                Main.MAP.remove(player);
            }
        }, 35);
    }

    public static ItemStack[] removeLast(ItemStack[] contents) {
        ItemStack[] returnItemStack = new ItemStack[9];
        int counter = 0;
        for (ItemStack item : contents) {
            if (counter == 9) {
                break;
            }
            returnItemStack[counter++] = item;
        }
        return returnItemStack;
    }

    public static boolean canBreakBlock(Block block) {
        Material type = block.getType();
        return !type.equals(Material.BEDROCK) && !type.equals(Material.WATER) && !type.equals(Material.LAVA) && !type.equals(Material.AIR);
    }

    public static int getSecureModelData(ItemStack item) {
        if (item == null || item.getItemMeta() == null || !item.getItemMeta().hasCustomModelData()) {
            return -1;
        }
        return item.getItemMeta().getCustomModelData();
    }

    public static Material getSecureType(ItemStack item) {
        if (item == null) {
            return Material.AIR;
        }
        return item.getType();
    }

    public static boolean chance(int chance) {
        return Main.RANDOM.nextInt(100) < chance;
    }

    public static void fillInventoryWithItems(Inventory inventory) {
        inventory.addItem(new CharcoleFragment());
        inventory.addItem(new EmeraldFragment());
        inventory.addItem(new IllusionerFragment());
        inventory.addItem(new NautilusFragment());
        inventory.addItem(new RubyFragment());
        inventory.addItem(new TreasureFragment());
        inventory.addItem(new WitherEssence());
        inventory.addItem(new CraftingFragment());

        inventory.addItem(new FarmingFragment(Material.CARROT));
        inventory.addItem(new FarmingFragment(Material.WHEAT_SEEDS));
        inventory.addItem(new FarmingFragment(Material.COCOA_BEANS));
        inventory.addItem(new FarmingFragment(Material.POTATO));
        inventory.addItem(new FarmingFragment(Material.BEETROOT_SEEDS));

        inventory.addItem(new EternitysEdge());
        inventory.addItem(new EternitysPickaxe());
        inventory.addItem(new IllusionerWand());
        inventory.addItem(new RubyPickaxe());
        inventory.addItem(new UltimateTotem());
        inventory.addItem(new HowToPlayBook());
    }

    public static ItemStack getItemByAutoCrafterInventory(int i) {
        ItemStack item;
        String name;
        if (i == 22) {
            item = new ItemStack(Material.GREEN_CONCRETE);
            name = "Enabled";
        } else if (i == 11) {
            item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
            name = "Input";
        } else if (i == 15) {
            item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
            name = "Output";
        } else {
            item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            name = " ";
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + name);
        itemMeta.setCustomModelData(404340);
        item.setItemMeta(itemMeta);
        return item;
    }

    private static final List<Material> CropList = new ArrayList<>(Arrays.asList(Material.CARROTS, Material.POTATOES,
            Material.BEETROOTS, Material.WHEAT, Material.COCOA));

    public static boolean isCrop(Material material) {
        return CropList.contains(material);
    }

    public static final HashMap<Material, Material> cropMap = new HashMap<Material, Material>() {{
        put(Material.CARROTS, Material.CARROT);
        put(Material.POTATOES, Material.POTATO);
        put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
        put(Material.WHEAT, Material.WHEAT_SEEDS);
        put(Material.COCOA, Material.COCOA_BEANS);
    }};

    public static Material convertBlockCropToItem(Material type) {
        return cropMap.get(type);
    }

    public static void setBlockNextTick(Location location, Material itemToReplant) {
        bukkitScheduler.runTaskLater(Main.getPlugin(), () -> {
            location.getWorld().getBlockAt(location).setType(itemToReplant);
        }, 1);

    }

    public static int getLevel(int exp) {
        // Für Level 0–15
        if (exp < 352) {
            return (int) ((-6 + Math.sqrt(36 + 4 * exp)) / 2);
        }
        // Für Level 16–30
        else if (exp < 1395) {
            return (int) ((40.5 + Math.sqrt(1640.25 + 10 * exp)) / 5);
        }
        // Für Level 31+
        else {
            return (int) ((158 + Math.sqrt(24964 + 36 * exp)) / 9);
        }
    } // TODO formel not working correclty

    public static int getExp(int level) {
        // Für Level 0–15
        if (level < 16) {
            return level * level + 6 * level;
        }
        // Für Level 16–30
        else if (level < 31) {
            return (int) (2.5 * level * level - 40.5 * level + 360);
        }
        // Für Level 31+
        else {
            return (int) (4.5 * level * level - 162.5 * level + 2220);
        } // TODO formel not working correclty
    }

    public static void savePlayersToFile() {
        // save Player EXP to config file here
    }

    public static boolean equalLocations(Location bedLocation, Location newLocation) {
        if (bedLocation == null || newLocation == null) {
            return false;
        }

        return radiusEquals(bedLocation.getX(), newLocation.getX(), 1.5) && radiusEquals(bedLocation.getY(), newLocation.getY(), 2) && radiusEquals(bedLocation.getZ(), newLocation.getZ(), 2);
    }

    public static boolean radiusEquals(double a, double b, double size) {
        return Math.abs(a - b) <= size;
    }

    public static ItemStack getRandomSpawnEgg() {
        return new ItemStack(SPAWN_EGG_LIST.get(Main.RANDOM.nextInt(SPAWN_EGG_LIST.size())));
    }

    private static final List<Material> SPAWN_EGG_LIST = new ArrayList<>();

    public static void fillSpawnEggList() {
        for (Material material : Material.values()) {
            if (material.name().endsWith("_SPAWN_EGG")) {
                SPAWN_EGG_LIST.add(material);
            }
        }
    }

    public static void test(Player player, Location bedLocation) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            player.sleep(bedLocation, true);
            Bukkit.getLogger().info("HUII");
            test(player, bedLocation);
        }, 1);
    }

    public static void createBlankWorld() {
        // Create a new WorldCreator with the name "sky"
        //WorldCreator creator = new WorldCreator("world_boss_area");
        //Bukkit.createWorld(creator);
        /*BiomeProvider biomeProvider = new BiomeProvider() { // Todo Biome auf single biome stellen und basalt deltas einstellen, sodass es schön ausseiht
            @Override
            public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
                return Biome.BASALT_DELTAS;
            }

            @Override
            public List<Biome> getBiomes(WorldInfo worldInfo) {
                return Collections.singletonList(Biome.BASALT_DELTAS);
            }
        };

        creator.biomeProvider(biomeProvider);
        creator.environment(World.Environment.CUSTOM);

        Bukkit.createWorld(creator);*/
        //WorldCreator.get

// Set the biome provider to use the basalt deltas biome
        //creator.biomeProvider("minecraft:fixed", "minecraft:basalt_deltas");

// Set any other options you want, such as generator, environment, seed, etc.

// Create the world using the creator
        //World world = Bukkit.createWorld(creator);
    }

    public static void waitForLoaded(Chunk chunk) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            if (chunk.isLoaded()) {
                for (Coordinate coordinate : new RandomCoords(false).nextCoordinates(2)) {
                    Block block = chunk.getBlock(coordinate.getX(), coordinate.getY(), coordinate.getZ());
                    Block blockUnderneath = chunk.getBlock(coordinate.getX(), coordinate.getY() > 0 ? coordinate.getY() - 1 : 0, coordinate.getZ());
                    Material materialUnderneath = blockUnderneath.getType();
                    if (materialUnderneath != Material.AIR && materialUnderneath != Material.WATER && materialUnderneath != Material.LAVA) {
                        block.setType(Material.STONE_PRESSURE_PLATE);
                        block.setMetadata("explosive", Main.FIXED_METADATA);
                    }
                }
            } else {
                waitForLoaded(chunk);
            }
        }, 20);
    }

    public static void checkUntilWentOf(Block block) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            BlockData blockMeta = block.getBlockData(); // das BlockMeta Objekt des Blocks
            if (blockMeta instanceof Powerable) { // überprüfe, ob das BlockMeta Objekt powerable ist
                Powerable powerable = (Powerable) blockMeta; // caste das BlockMeta Objekt zu einem Powerable Objekt
                if (powerable.isPowered()) {
                    checkUntilWentOf(block);
                } else {
                    block.getWorld().createExplosion(block.getLocation(), 15, true, true);
                    Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> block.setType(Material.AIR), 1);
                }
            } else {
                block.getWorld().createExplosion(block.getLocation(), 15, true, true);
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> block.setType(Material.AIR), 1);
            }
        }, 1);
    }

    public static BlockFace getStairsBlockFace(Block stairsBlock) {
        // Determine the block face based on the stairs' block data
        // Adjust this logic based on your specific block data handling requirements
        // This example assumes the stairs' direction is determined by the data value
        byte data = stairsBlock.getData();
        switch (data & 0x3) {
            case 0:
                return BlockFace.EAST;
            case 1:
                return BlockFace.WEST;
            case 2:
                return BlockFace.SOUTH;
            case 3:
            default:
                return BlockFace.NORTH;
        }
    }

    public static float blockFaceToYaw(BlockFace blockFace) {
        // Convert BlockFace to yaw rotation angle
        // Adjust this logic based on your specific requirements
        switch (blockFace) {
            case SOUTH:
                return 180;
            case WEST:
                return 270;
            case NORTH:
                return 0;
            case EAST:
                return 90;
            default:
                return 0;
        }
    }

    public static void removePigForChairs(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location, 1, 1, 1)) {
            if (entity.getName().equals("HARDCORE_PLUGIN_CHAIR_SIT")) {
                entity.remove();
            }
        }
    }

    public static boolean sameLocation(Location loc1, Location loc2, double bound) {
            return loc1.getX() == loc2.getX() && radiusEquals(loc1.getY(), loc2.getY(), bound) && loc1.getZ() == loc2.getZ();
    }


}

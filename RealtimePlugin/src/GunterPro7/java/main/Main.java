package main;

import commands.GunterModeCommand;
import fragments.*;
import items.AuspiciousRabbitStew;
import items.EternitysPickaxe;
import listeners.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import utils.*;

import java.util.*;

public class Main extends JavaPlugin implements CommandExecutor {
    public static final Random RANDOM = new Random();
    private static Plugin plugin;
    public static Plugin getPlugin() {
        return plugin;
    }
    public static final List<Player> MAP = new ArrayList<>();
    public static final HashMap<String, PlayerInformations> PLAYER_MAP = new HashMap<>();
    public static final Inventory INVENTORY = Bukkit.createInventory(null, 54, "Items");
    public static final Set<Material> IGNORED_BLOCKS = EnumSet.of(Material.AIR, Material.WATER);
    public static MetadataValue FIXED_METADATA;

    @Override
    public void onEnable() {
        plugin = this;

        FIXED_METADATA = new FixedMetadataValue(Main.getPlugin(), true);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListeners(), plugin);
        pm.registerEvents(new EntityListener(), plugin);
        pm.registerEvents(new MiscListeners(), plugin);

        if (Worlds.WORLD_BOSS.getWorld() == null) {
            Utils.createBlankWorld();
        }
        if (Worlds.WORLD_SKYBLOCK.getWorld() == null) {
            WorldCreator creator = new WorldCreator("world_skyblock");
            creator.generator(new SkyGenerator());

            // Create and load the world
            Worlds.WORLD_SKYBLOCK.setWorld(creator.createWorld());
        }

        pm.registerEvents(new InteractListeners(), plugin);

        World world = Worlds.WORLD.getWorld(); // Get the world
        world.setGameRuleValue("playersSleepingPercentage", "101"); // Set the player sleep
        Worlds.WORLD_SKYBLOCK.getWorld().setGameRuleValue("playersSleepingPercentage", "101"); // Set the player sleep

        CraftingInventorys.createInstanzes();

        for (Player curPlayer : Bukkit.getOnlinePlayers()) {
            Main.PLAYER_MAP.put(curPlayer.getName(), new PlayerInformations(curPlayer));
        }

        Utils.fillInventoryWithItems(INVENTORY);
        Utils.fillSpawnEggList();
        getCommand("gunter_mode").setExecutor(new GunterModeCommand());

        addRecipes();
    }

    private void addRecipes() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new AuspiciousRabbitStew());

        shapedRecipe.shape(" A ", "AAA", " A ");
        shapedRecipe.setIngredient('A', Material.RABBIT_STEW);

        Bukkit.addRecipe(shapedRecipe);
    }

    @Override
    public void onDisable() {
        for (PlayerInformations pi : PLAYER_MAP.values()) {
            pi.disableBossbar();
        }
    }



}

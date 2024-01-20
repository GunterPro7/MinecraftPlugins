package main;

import commands.MainCommand;
import costumItems.*;
import listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import utils.ItemInformation;
import utils.Utils;

import java.io.*;
import java.util.*;

public class Main extends JavaPlugin implements CommandExecutor {
    private static Plugin plugin;
    private static final Inventory inventory = Bukkit.createInventory(null, 27, "§lUltimate Tools");
    private static final Map<String, ItemInformation> itemDict = new HashMap<>();
    private static String[] keys;

    public static String[] getKeyList() {
        return keys;
    }

    public static void resetItemsOfInventory(Player player) {
        for (int i = 0; i < itemDict.size(); i++) {
            ItemInformation curInfo = itemDict.get(keys[i]);
            inventory.setItem(i, Utils.getItemIfPermission(player, curInfo.getPermission(), curInfo.getItem()));
        }
        //inventory.setItem(24, Utils.getItemIfPermission(player, "ultimatetools.use.infinity_sword", Hyperion.item));
    }

    public static Inventory createUtInv(Player player) {
        resetItemsOfInventory(player);
        return inventory;
    }

    public static Map<String, ItemInformation> getItemDict() {
        return itemDict;
    }

    @Override
    public void onEnable() {
        fillItemDict();
        plugin = this;
        loadSettingsFromFile();
        loadCommands();
        loadListener();
        loadCustomItems();
        loadCraftingRecipes();
        loadPermissions();
    }

    private void loadCustomItems() {
        GrapplingHook.createItem();
        InfinityPearl.createItem();
        InfinityBoom.createItem();
        SuperLeap.createItem();
        InfinityWaterBucket.createItem();
        InfinityLavaBucket.createItem();
        Hyperion.createItem();
        Terminator.createItem();
        LightningBolt.createItem();
        WinterBlast.createItem();
        IceWand.createItem();
        InfinityPickaxe.createItem();
        ThreeByThreeSuperTool.createItem();
        FlameThrower.createItem();
        InfinityWand.createItem();
        TeddyBear.createItem();
        AutoPickUp.createItem();
        LockedSlot.createItem();
    }

    private void loadPermissions() {
        PluginManager pm = Bukkit.getPluginManager();
        for (int i = 0; i < keys.length; i++) {
            pm.addPermission(new Permission("ultimatetools.use." + keys[i]));
            pm.addPermission(new Permission("ultimatetools.craft." + keys[i]));
        }

        pm.addPermission(new Permission("ultimatetools.give"));
        pm.addPermission(new Permission("ultimatetools.set"));
        pm.addPermission(new Permission("ultimatetools.ultimatetools"));
    }

    private void loadCraftingRecipes() {
        GrapplingHook.enableCraftingRecipe();
        InfinityPearl.enableCraftingRecipe();
        InfinityBoom.enableCraftingRecipe();
        Hyperion.enableCraftingRecipe();
        Terminator.enableCraftingRecipe();
        SuperLeap.enableCraftingRecipe();
        WinterBlast.enableCraftingRecipe();
        LightningBolt.enableCraftingRecipe();
        InfinityWaterBucket.enableCraftingRecipe();
        InfinityLavaBucket.enableCraftingRecipe();
        InfinityPickaxe.enableCraftingRecipe();
        ThreeByThreeSuperTool.enableCraftingRecipe();
        FlameThrower.enableCraftingRecipe();
        InfinityWand.enableCraftingRecipe();
        InfinitySword.enableCraftingRecipe();
        TeddyBear.enableCraftingRecipe();
        AutoPickUp.enableCraftingRecipe();
    }

    private void fillItemDict() {
        itemDict.put("grappling_hook", new ItemInformation("Grappling hook", GrapplingHook.item, "grappling_hook"));
        itemDict.put("hyperion", new ItemInformation("Hyperion", Hyperion.item, "hyperion"));
        itemDict.put("infinity_pearl", new ItemInformation("Infinity Pearl", InfinityPearl.item, "infinity_pearl"));
        itemDict.put("infinity_boom", new ItemInformation("Infinity Boom", InfinityBoom.item, "infinity_boom"));
        itemDict.put("infinity_water_bucket", new ItemInformation("Infinity Water Bucket", InfinityWaterBucket.item, "infinity_water_bucket"));
        itemDict.put("infinity_lava_bucket", new ItemInformation("Infinity Lava Bucket", InfinityLavaBucket.item, "infinity_lava_bucket"));
        itemDict.put("lightning_bolt", new ItemInformation("Lightning Bolt", LightningBolt.item, "lightning_bolt"));
        itemDict.put("pearl_leap", new ItemInformation("Pearl Leap", SuperLeap.item, "pearl_leap"));
        itemDict.put("terminator", new ItemInformation("Terminator", Terminator.item, "terminator"));
        itemDict.put("winter_blast", new ItemInformation("Winter Blast", WinterBlast.item, "winter_blast"));
        itemDict.put("ice_wand", new ItemInformation("Ice Wand", IceWand.item, "ice_wand"));
        itemDict.put("infinity_pickaxe", new ItemInformation("Infinity Pickaxe", InfinityPickaxe.item, "infinity_pickaxe"));
        itemDict.put("three_by_three_super_tool", new ItemInformation("3x3 Super Tool", ThreeByThreeSuperTool.item, "three_by_three_super_tool"));
        itemDict.put("flame_thrower", new ItemInformation("Flame Thrower", FlameThrower.item, "flame_thrower"));
        itemDict.put("infinity_wand", new ItemInformation("Infinity Wand", InfinityWand.item, "infinity_wand"));
        itemDict.put("teddy_bear", new ItemInformation("Teddy Bear", TeddyBear.item, "teddy_bear"));
        itemDict.put("infinity_sword", new ItemInformation("Infinity Sword", InfinitySword.item, "infinity_sword"));
        itemDict.put("auto_pick_up", new ItemInformation("Auto Pick Up", AutoPickUp.item, "auto_pick_up"));
        keys = itemDict.keySet().toArray(new String[0]);
    }

    private void loadCommands() {
        getCommand("ultimateTools").setExecutor(new MainCommand());
        getCommand("ut").setExecutor(new MainCommand());
    }

    private void loadListener() {
        getServer().getPluginManager().registerEvents(new OnPlayerFishEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerEmptyBucketEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerMove(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerInteractEntity(), this);
        getServer().getPluginManager().registerEvents(new OnBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new OnProjectileHit(), this);
        getServer().getPluginManager().registerEvents(new OnItemCraft(), this);
        getServer().getPluginManager().registerEvents(new OnBlockPlace(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerEnterBed(), this);
    }

    private void loadSettingsFromFile() {
        File dataFolder = getPlugin().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        File settings = new File(getPlugin().getDataFolder(), "config.yml");
        if (!settings.exists()) {
            saveSettingsToFile();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(settings));
            List<String> fileContentList = Utils.readFile(bufferedReader);
            CustomItemData.grapplingHookCD = Double.parseDouble(fileContentList.get(1).split("cooldown: ")[1]);
            CustomItemData.grapplingHookRange = Double.parseDouble(fileContentList.get(2).split("range: ")[1]);

            CustomItemData.boomCD = Double.parseDouble(fileContentList.get(4).split("cooldown: ")[1]);
            CustomItemData.boomPower = Double.parseDouble(fileContentList.get(5).split("power: ")[1]);

            CustomItemData.winterBlastPower = Double.parseDouble(fileContentList.get(7).split("power: ")[1]);

            CustomItemData.hypeSpeed = Double.parseDouble(fileContentList.get(9).split("speed: ")[1]);

            CustomItemData.pearlCD = Double.parseDouble(fileContentList.get(11).split("cooldown: ")[1]);

            CustomItemData.boltCD = Double.parseDouble(fileContentList.get(13).split("cooldown: ")[1]);

            CustomItemData.terminatorSpeed = Double.parseDouble(fileContentList.get(15).split("speed: ")[1]);

            CustomItemData.leapCD = Double.parseDouble(fileContentList.get(17).split("cooldown: ")[1]);

            CustomItemData.iceWandCD = Double.parseDouble(fileContentList.get(19).split("cooldown: ")[1]);
            CustomItemData.iceWandDuration = Double.parseDouble(fileContentList.get(20).split("duration: ")[1]);

            CustomItemData.infinityPickaxeRange = Integer.parseInt(fileContentList.get(22).split("range: ")[1]);
            CustomItemData.flameThrowerCD = Integer.parseInt(fileContentList.get(24).split("cooldown: ")[1]);

            CustomItemData.infinityWandCD = Integer.parseInt(fileContentList.get(26).split("cooldown: ")[1]);
            CustomItemData.infinityWandRange = Integer.parseInt(fileContentList.get(27).split("range: ")[1]);

            CustomItemData.infinitySwordRange = Integer.parseInt(fileContentList.get(29).split("range: ")[1]);

            bufferedReader.close();
        } catch (Exception e) {
            saveSettingsToFile();
        }
    }

    public static void saveSettingsToFile() {
        File dataFolder = getPlugin().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        File settings = new File(getPlugin().getDataFolder(), "config.yml");
        if (!settings.exists()) {
            try {
                settings.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        try {
            String[] tempList = {"# Grappling Hook", "cooldown: " + CustomItemData.grapplingHookCD, "range: " + CustomItemData.grapplingHookRange,
                    "# Infinity Boom", "cooldown: " + CustomItemData.boomCD, "power: " + CustomItemData.boomPower, "# Winter Blast",
                    "power: " + CustomItemData.winterBlastPower, "# Hyperion", "speed: " + CustomItemData.hypeSpeed, "# Infinity Pearls",
                    "cooldown: " + CustomItemData.pearlCD, "# Lightning Bolt", "cooldown: " + CustomItemData.boltCD, "# Terminator",
                    "speed: " + CustomItemData.terminatorSpeed, "# Super Leaps", "cooldown: " + CustomItemData.leapCD, "# Ice Wand",
                    "cooldown: " + CustomItemData.iceWandCD, "duration: " + CustomItemData.iceWandDuration, "# Infinity Pickaxe",
                    "range: " + CustomItemData.infinityPickaxeRange, "# Flame Thrower", "cooldown: " + CustomItemData.flameThrowerCD, "# Infinity Wand", "cooldown: " + CustomItemData.infinityWandCD, "range: " + CustomItemData.infinityWandRange, "# Infinity Sword", "range: " + CustomItemData.infinitySwordRange};

            FileWriter fileWriter = new FileWriter(getPlugin().getDataFolder() + "/config.yml");
            for (String curPart : tempList) {
                fileWriter.write(curPart + "\n");
            }
            fileWriter.close();
        } catch (IOException ignored) {
        }

    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        //saveSettingsToFile();
    }
}

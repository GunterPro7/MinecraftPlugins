package main;

import commands.*;
import listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import utils.Messages;
import utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin implements CommandExecutor {

    public static String title = "Backpack";
    public static String lore = "A good place to store your items while travelling.";
    public static List<Item> itemEntities = new ArrayList<>();

    private static Plugin plugin;
    private static String uuid;

    public static int continueResizeNewsize = 54;

    public static List<String> fileNames = new ArrayList<>();

    public static Plugin getPlugin() {
        return plugin;
    }

    private static FileConfiguration config;
    private static File configFile;

    public static FileConfiguration getConfig_() {
        return config;
    }

    public static File getConfigFile() {
        return configFile;
    }

    public static void setCurrentID(String uuid) {
        Main.uuid = uuid;
    }

    public static String getCurrentID() {
        return uuid;
    }

    public static void saveChangesToFile() {
        File file = new File(getPlugin().getDataFolder() + "/config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("#Backpack config:\n");
            fileWriter.write("title: " + title + "\n");
            fileWriter.write("lore: " + lore + "\n");
            fileWriter.write("size: " + continueResizeNewsize + "\n\n");
            fileWriter.write("#Messages config:" + "\n");
            fileWriter.write("no_permission: " + Messages.noPermission + "\n");
            fileWriter.write("gave_new_backpack: " + Messages.gaveNewBackpack + "\n");
            fileWriter.write("changed_size: " + Messages.changedSize + "\n");
            fileWriter.write("changed_lore: " + Messages.changedLore + "\n");
            fileWriter.write("changed_title: " + Messages.changedTitle + "\n");
            fileWriter.write("command_not_found: " + Messages.commandNotFound + "\n");
            fileWriter.write("inventory_not_exists: " + Messages.inventoryNotExists + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadChangesFromFile() {
        File file = new File(getPlugin().getDataFolder() + "/config.yml");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            title = reader.readLine().split("title: ")[1];
            lore = reader.readLine().split("lore: ")[1];
            try {
                continueResizeNewsize = Integer.parseInt(reader.readLine().split("size: ")[1]);
            } catch (Exception e) {
                continueResizeNewsize = 54;
            }

            reader.readLine();
            reader.readLine();

            Messages.noPermission = Utils.splitAb(reader.readLine(), "no_permission: ", 1);
            Messages.gaveNewBackpack = Utils.splitAb(reader.readLine(), "gave_new_backpack: ", 1);
            Messages.changedSize = Utils.splitAb(reader.readLine(), "changed_size: ", 1);
            Messages.changedLore = Utils.splitAb(reader.readLine(), "changed_lore: ", 1);
            Messages.changedTitle = Utils.splitAb(reader.readLine(), "changed_title: ", 1);
            Messages.commandNotFound = Utils.splitAb(reader.readLine(), "command_not_found: ", 1);
            Messages.inventoryNotExists = Utils.splitAb(reader.readLine(), "inventory_not_exists: ", 1);
        } catch (Exception e) {
            saveChangesToFile();
            loadChangesFromFile();
        }
    }

    private void checkIfDirectoryIsExisting() {
        File directory = new File(getDataFolder(), "backpacks");

        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public void onEnable() {
        plugin = this;
        checkIfDirectoryIsExisting();
        loadChangesFromFile();
        loadAllFileNames();
        this.getCommand("backpack").setExecutor(new BackpackCommand());
        this.getCommand("bp").setExecutor(new BackpackCommand());
        getServer().getPluginManager().registerEvents(new OnInventoryClose(), plugin);
        Bukkit.getPluginManager().registerEvents(new OnRightClick(), plugin);
        getServer().getPluginManager().registerEvents(new OnCraftingItem(), plugin);
        getServer().getPluginManager().registerEvents(new DespawnListener(), plugin);


        Backpacks.enableCraftingRecipe();
        PluginManager pm = getServer().getPluginManager();
        pm.addPermission(new Permission("backpacks.info"));
        pm.addPermission(new Permission("backpacks.get"));
        pm.addPermission(new Permission("backpacks.setsize"));
        pm.addPermission(new Permission("backpacks.setlore"));
        pm.addPermission(new Permission("backpacks.settitle"));
        pm.addPermission(new Permission("backpacks.open"));
        pm.addPermission(new Permission("backpacks.clear"));
    }

    private void loadAllFileNames() {
        File folder = new File(getPlugin().getDataFolder() + "/backpacks/");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName().replace(".json", ""));
                }
            }
        }
        // Do something with the list of file names
    }

    @Override
    public void onDisable() {

    }

}

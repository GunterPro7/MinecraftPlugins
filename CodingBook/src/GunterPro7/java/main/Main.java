package main;

import costumItems.CodingBook;
import listeners.OnItemCraft;
import utils.Config;
import utils.Utils;
import commands.CodingBookCommand;
import listeners.OnEditBook;
import listeners.OnPlayerInteract;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends JavaPlugin implements CommandExecutor {
    private static Plugin plugin;
    @Override
    public void onEnable() {
        plugin = this;
        Config.readPropertyFile();
        getCommand("codingBook").setExecutor(new CodingBookCommand());
        PluginManager pm = Bukkit.getPluginManager();
        pm.addPermission(new Permission("codingbook.use"));
        pm.addPermission(new Permission("codingbook.convert"));
        pm.addPermission(new Permission("codingbook.give"));
        pm.addPermission(new Permission("codingbook.continue"));
        pm.addPermission(new Permission("codingbook.stop"));
        pm.addPermission(new Permission("codingbook.kill"));
        pm.addPermission(new Permission("codingbook.allow_run_unsafe"));
        pm.addPermission(new Permission("codingbook.help"));
        pm.addPermission(new Permission("codingbook.craft"));
        pm.registerEvents(new OnPlayerInteract(), plugin);
        pm.registerEvents(new OnEditBook(), plugin);
        pm.registerEvents(new OnItemCraft(), plugin);

        File folder = new File(getDataFolder().toString());
        if (!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(getDataFolder(), "config.yml");
        if (file.exists()) {
            try {
                Utils.loadMessagesFromFile(file);
            } catch (FileNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                Bukkit.getLogger().info("Error occured while reading config.yml file! -> " + e);
            }
        } else {
            try {
                Utils.createMessagesFile(file);
            } catch (IOException e) {
                Bukkit.getLogger().info("ERROR - Error while creating Messages File! -> " + e);
            }
        }

        Bukkit.addRecipe(CodingBook.getCodingBookRecipe());
    }

    @Override
    public void onDisable() {

    }

    public static Plugin getPlugin() {
        return plugin;
    }

}

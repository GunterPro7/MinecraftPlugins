package main;

import commands.*;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin implements CommandExecutor {

    public static NamespacedKey KEY;
    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        File dataFolder = new File(getDataFolder().toString());
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        File waypointFile = new File(getDataFolder(), "waypoints.txt");
        if (!waypointFile.exists()) {
            try {
                waypointFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        plugin = this;
        KEY = new NamespacedKey(plugin, "waypoint");
        this.getCommand("waypoint").setExecutor(new WaypointCommand());
        this.getCommand("wp").setExecutor(new WaypointCommand());
        Waypoints.removeAllWaypoints();
        Waypoints.loadWaypointsFromFile();
        PluginManager pm = getServer().getPluginManager();
        pm.addPermission(new Permission("waypoints.setwaypoint"));
        pm.addPermission(new Permission("waypoints.listwaypoints"));
        pm.addPermission(new Permission("waypoints.clearwaypoints"));
        pm.addPermission(new Permission("waypoints.removewaypoint"));
    }

    @Override
    public void onDisable() {
        Waypoints.removeAllWaypoints();
    }

}

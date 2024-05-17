package main;

import listeners.OnMoveEvent;
import listeners.OnPlayerInteract;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import snake.SnakeMap;

import java.util.Random;

public class Main extends JavaPlugin implements CommandExecutor {
    public static final Random RANDOM = new Random();
    private static Plugin plugin;
    @Override
    public void onEnable() {
        plugin = this;

        Player player = Bukkit.getPlayer("GunterPro7");
        player.getInventory().addItem(new SnakeMap(player).getMap());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new OnPlayerInteract(), this);
        pm.registerEvents(new OnMoveEvent(), this);
        /* TODO STOPPED HERE */
    }

    @Override
    public void onDisable() {

    }

    public static Plugin getPlugin() {
        return plugin;
    }

}
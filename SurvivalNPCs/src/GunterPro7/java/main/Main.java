package main;

import commands.NPCCommand;
import listeners.InteractListeners;
import listeners.PlayerListeners;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

public class Main extends JavaPlugin implements CommandExecutor {
    public static final Random RANDOM = new Random();
    public static final HashMap<Player, PlayerInformation> PLAYERS = new HashMap<>();
    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        File folder = new File(getDataFolder().toString());
        if (!folder.exists()) {
            folder.mkdir();
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InteractListeners(), plugin);
        pm.registerEvents(new PlayerListeners(), plugin);


        getCommand("npc").setExecutor(new NPCCommand());

        for (Player player: Bukkit.getOnlinePlayers()) {
            PLAYERS.put(player, new PlayerInformation());
        }
    }

    @Override
    public void onDisable() {

    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
package main;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin implements CommandExecutor {
    public static HashMap<String, String> worldDict = new HashMap<String, String>();


    @Override
    public void onEnable() {
        fillWorldDict();
        getServer().getPluginManager().registerEvents(new onPlayerListener(), this);
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                for (int i = 0; i < players.size(); i++) {
                    updateScoreboard.updateScoreboard(players.get(i), false);
                }
            }
        };
        task.runTaskTimer(this, 0L, 600L);
    }

    @Override
    public void onDisable() {

    }

    public void fillWorldDict() {
        worldDict.put("world", "Building world");
        worldDict.put("world_nether", "Building world nether");
        worldDict.put("world_the_end", "Building world end");
        worldDict.put("hub1", "Hub");
        worldDict.put("farming_world", "Farming world");
        worldDict.put("farming_world_nether", "Farming world nether");
        worldDict.put("farming_world_the_end", "Farming world end");
    }
}

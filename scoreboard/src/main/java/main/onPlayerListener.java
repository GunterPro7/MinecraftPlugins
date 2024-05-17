package main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class onPlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < players.size(); i++) {
            updateScoreboard.updateScoreboard(players.get(i), false);
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (int i = 0; i < players.size(); i++) {
            updateScoreboard.updateScoreboard(players.get(i), true);
        }

    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer(); // den Spieler definieren
        updateScoreboard.updateScoreboard(player, false);
    }
}

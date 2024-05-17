package main;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class updateScoreboard {
    public static void updateScoreboard(Player player, boolean remove1Player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard(); // neues Scoreboard erstellen

        Objective obj = scoreboard.registerNewObjective("Test", "dummy"); // Objekt erstellen
        obj.setDisplaySlot(DisplaySlot.SIDEBAR); // Anzeigeslot festlegen
        obj.setDisplayName("§6§lBlockcity Survival"); // Anzeigenamen festlegen

// Zeilen hinzufügen
        obj.getScore("").setScore(7);
        if (remove1Player) {
            obj.getScore("§ePlayers online: §r" + (Bukkit.getOnlinePlayers().size() - 1)).setScore(6);
        } else {
            obj.getScore("§ePlayers online: §r" + Bukkit.getOnlinePlayers().size()).setScore(6);
        }

        obj.getScore("§eServer IP: §rwww.BlockCity.at").setScore(5);
        obj.getScore("").setScore(4);
        obj.getScore("§ePlayer: §r" + player.getName()).setScore(3);
        obj.getScore("§eWorld: §r" + Main.worldDict.get(player.getWorld().getName())).setScore(2);
        long playTime = player.getStatistic(Statistic.TOTAL_WORLD_TIME) / 1200;
        obj.getScore("§ePlaytime: §r" + playTime / 60 + "h " + playTime % 60 + "m").setScore(1);

// Team hinzufügen
        //Team team = scoreboard.registerNewTeam("team1");
        //team.addEntry("§eTeam 1:");
        //score.getScoreboard().getTeam("team1").addEntry("§eTeam 1:");

// Scoreboard dem Spieler zuweisen
        player.setScoreboard(scoreboard);
    }
}

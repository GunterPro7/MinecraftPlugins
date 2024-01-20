package commands;

import main.Main;
import main.Waypoints;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClearWaypointsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("waypoints.clearwaypoints")) {
            sender.sendMessage(ChatColor.RED + "You have no permission to run that command!");
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }


        File waypointsFile = new File(Main.getPlugin().getDataFolder(), "waypoints.txt");
        if (waypointsFile.length() == 0) {
            sender.sendMessage(ChatColor.RED + "No waypoints to clear!");
            return true;
        }

        Waypoints.removeAllWaypoints();
        try {
            FileWriter fileWriter = new FileWriter(waypointsFile);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sender.sendMessage(ChatColor.YELLOW + "Cleared all Waypoints!");
        Waypoints.clearWaypoints();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}

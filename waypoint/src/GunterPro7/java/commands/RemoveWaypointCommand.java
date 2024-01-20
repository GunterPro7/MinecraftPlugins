package commands;

import main.Main;
import main.Waypoints;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import utils.Waypoint;

import java.util.ArrayList;
import java.util.List;

public class RemoveWaypointCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("waypoints.removewaypoint")) {
            sender.sendMessage(ChatColor.RED + "You have no permission to run that command!");
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("Usage: /waypoint remove <name>");
            return true;
        }

        String name = args[0];

        Waypoint waypoint = Waypoints.getWaypointByName(name);
        if (waypoint == null) {
            sender.sendMessage(ChatColor.RED + "This waypoint does not exist!");
            return true;
        }


        Waypoints.removeWaypoint(waypoint);

        int waypointIndex = Waypoints.getDuplicatedWaypointIndex(waypoint);
        Waypoints.removeWaypointFromFile(waypointIndex);
        Waypoints.removeWaypoint(waypointIndex);
        sender.sendMessage(ChatColor.GREEN + "Waypoint deleted successful!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String arg = args[0].toLowerCase();
            int counter = 0;

            for (Waypoint waypoint : Waypoints.getWaypoints()) {
                if (counter == 64) {
                    break;
                }

                String currentWorldName = waypoint.getName();
                if (currentWorldName.toLowerCase().startsWith(arg)) {
                    completions.add(currentWorldName);
                    counter++;
                }
            }
        }
        return completions;
    }
}

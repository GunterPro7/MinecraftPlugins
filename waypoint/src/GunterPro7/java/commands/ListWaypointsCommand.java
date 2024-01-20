package commands;

import main.Waypoints;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import utils.Utils;
import utils.Waypoint;


import java.util.ArrayList;
import java.util.List;

public class ListWaypointsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("waypoints.listwaypoints")) {
            sender.sendMessage(net.md_5.bungee.api.ChatColor.RED + "You have no permission to run that command!");
            return true;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;

            List<Waypoint> waypoints = Waypoints.getWaypoints();

            if (waypoints.isEmpty()) {
                player.sendMessage(ChatColor.RED + "No waypoints found!");
                return true;
            }

            int page = 1; // Standardseite ist 1
            if (args.length > 0) {
                try {
                    page = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid page number!");
                    return true;
                }
            }

            int maxPerPage = 8; // maximale Anzahl von Einträgen pro Seite
            int startIndex = (page - 1) * maxPerPage; // Index des ersten Eintrags auf der Seite
            int endIndex = Math.min(startIndex + maxPerPage, waypoints.size()); // Index des letzten Eintrags auf der Seite

            int pages = waypoints.size() / 8 + 1;
            if (page > pages) {
                sender.sendMessage(ChatColor.RED + "This Page does not exist yet!");
                return true;
            }

            player.sendMessage(ChatColor.YELLOW + "Waypoints (Page: " + page + '/' + pages + "):");

            for (int i = startIndex; i < endIndex; i++) {
                Waypoint waypoint = waypoints.get(i);
                if (player.hasPermission("waypoints.tp")) {
                    player.spigot().sendMessage(Utils.combineWithTooltip(ChatColor.GREEN + "§l" + waypoint.getName() + "§r: " + waypoint.getX() +
                                    " " + waypoint.getY() + " " + waypoint.getZ() +
                                    " in §l" + waypoint.getWorldName() + ChatColor.YELLOW + " [", ChatColor.YELLOW + "]", ChatColor.YELLOW + "tp", "Teleport to Waypoint",
                            "/execute in minecraft:" + waypoint.getWorldName() + " run teleport " + player.getName() + " " + waypoint.getX() + " " + waypoint.getY() + " " + waypoint.getZ()
                            , ClickEvent.Action.RUN_COMMAND));
                } else {
                    player.sendMessage(ChatColor.GREEN + "§l" + waypoint.getName() + "§r: " + waypoint.getX() +
                            " " + waypoint.getY() + " " + waypoint.getZ() +
                            " in §l" + waypoint.getWorldName());
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}

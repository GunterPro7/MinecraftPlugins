package commands;

import main.Waypoints;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import utils.Utils;
import utils.Waypoint;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

import static utils.Utils.checkCoordForTild;

public class SetWaypointCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("waypoints.setwaypoint")) {
            sender.sendMessage(ChatColor.RED + "You have no permission to run that command!");
            return true;
        }
        Player player = (Player) sender;


        int x, y, z;

        if (args.length < 1) {
            player.sendMessage("Usage: /waypoint create <name> <x> <y> <z> [world]");
            return true;
        } else if (args.length < 4) {
            Location playerLoc = player.getLocation();
            x = (int) playerLoc.getX();
            y = (int) playerLoc.getY();
            z = (int) playerLoc.getZ();
        } else {
            try {
                x = checkCoordForTild(args[1], player, 'x');
                y = checkCoordForTild(args[2], player, 'y');
                z = checkCoordForTild(args[3], player, 'z');
            } catch (NumberFormatException e) {
                player.sendMessage("Usage: /waypoint create <name> <x> <y> <z> [world]");
                return true;
            }
        }

        String name = args[0];
        World world;


        if (args.length >= 5) {
            world = Bukkit.getWorld(args[4]);
        } else {
            if (sender != null) {
                world = player.getWorld();
            } else {
                sender.sendMessage("You must specify a world name as fourth argument.");
                return true;
            }
        }


        if (world == null) {
            player.sendMessage(ChatColor.RED + "World '" + args[4] + "' not found!");
            return true;
        }

        Waypoint newWaypoint = new Waypoint(x, y, z, name, world);

        if (Waypoints.isDuplicateWaypoint(newWaypoint)) {
            sender.sendMessage(ChatColor.RED + "This waypoint already exists!");
            return true;
        }

        newWaypoint.addToFile();
        Waypoints.addWaypoint(newWaypoint);
        if (player.hasPermission("waypoints.tp")) {
            player.spigot().sendMessage(Utils.combineWithTooltip(ChatColor.GREEN + "Waypoint set successfully! " + ChatColor.YELLOW + "[", org.bukkit.ChatColor.YELLOW + "]", org.bukkit.ChatColor.YELLOW + "tp", "Teleport to Waypoint",
                    "/execute in minecraft:" + newWaypoint.getWorldName() + " run teleport " + player.getName() + " " + newWaypoint.getX() + " " + newWaypoint.getY() + " " + newWaypoint.getZ()
                    , ClickEvent.Action.RUN_COMMAND));
        } else {
            sender.sendMessage(ChatColor.GREEN + "Waypoint set successfully!");
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        int x, y, z;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation();
            x = location.getBlockX();
            y = location.getBlockY();
            z = location.getBlockZ();
        } else {
            x = 0;
            y = 0;
            z = 0;
        }

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
                }
                counter++;
            }

        } else if (args.length == 2) { // if the player has typed one argument
            String arg = args[1].toLowerCase();

            // Generate completions based on the argument
            if (String.valueOf(x).startsWith(arg)) {
                completions.add(String.valueOf(x));
            }

        } else if (args.length == 3) {
            String arg = args[2].toLowerCase();

            // Generate completions based on the argument
            if (String.valueOf(y).startsWith(arg)) {
                completions.add(String.valueOf(y));
            }

        } else if (args.length == 4) {
            String arg = args[3].toLowerCase();

            // Generate completions based on the argument
            if (String.valueOf(z).startsWith(arg)) {
                completions.add(String.valueOf(z));
            }
        }

        if (args.length >= 2 && args.length <= 4) {
            completions.add("~");
        }

        if (args.length == 5) {
            String arg = args[4].toLowerCase();
            int counter = 0;

            for (World world : Bukkit.getWorlds()) {
                if (counter == 64) {
                    break;
                }

                String currentWorldName = world.toString().split("=")[1].replace("}", "");
                if (currentWorldName.toLowerCase().startsWith(arg)) {
                    completions.add(currentWorldName);
                }
                counter++;
            }
        }

        return completions;
    }
}

package commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaypointCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            // Hier kannst du eine Übersicht aller verfügbaren Subcommands anzeigen
            sender.sendMessage("Available subcommands: set, list, clear, remove");
            return true;
        }

        String subcommand = args[0].toLowerCase();
        if (subcommand.equals("create")) {
            return new SetWaypointCommand().onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (subcommand.equals("list")) {
            return new ListWaypointsCommand().onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (subcommand.equals("clear")) {
            return new ClearWaypointsCommand().onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (subcommand.equals("remove")) {
            return new RemoveWaypointCommand().onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else {
            // Hier kannst du eine Fehlermeldung anzeigen, wenn der Subcommand nicht gefunden wird
            sender.sendMessage(ChatColor.RED + "Unknown command: " + subcommand);
            return true;
        }
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        String subcommand = args[0].toLowerCase();
        if (subcommand.equals("create")) {
            return new SetWaypointCommand().onTabComplete(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (subcommand.equals("list")) {
            return new ListWaypointsCommand().onTabComplete(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (subcommand.equals("remove")) {
            return new RemoveWaypointCommand().onTabComplete(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (subcommand.equals("clear")) {
            return new ClearWaypointsCommand().onTabComplete(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else {
            if ("create".startsWith(subcommand)) {
                completions.add("create");
            }
            if ("remove".startsWith(subcommand)) {
                completions.add("remove");
            }
            if ("list".startsWith(subcommand)) {
                completions.add("list");
            }
            if ("clear".startsWith(subcommand)) {
                completions.add("clear");
            }
        }
        return completions;
    }
}

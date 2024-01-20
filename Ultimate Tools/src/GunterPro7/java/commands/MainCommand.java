package commands;

import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("ultimatetools.ultimatetools")) {
                sender.sendMessage(ChatColor.RED + "You have no Permission to run that command!");
                return true;
            }
            ((Player) sender).openInventory(Main.createUtInv((Player) sender));
            return true;
        } else if (Objects.equals(args[0], "set")) {
            if (sender.hasPermission("ultimatetools.set")) {
                return new SetCommand().onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else {
                sender.sendMessage(ChatColor.RED + "You have no Permission to run that command!");
                return true;
            }

        } else if (Objects.equals(args[0], "help")) {
            sender.sendMessage(ChatColor.YELLOW + "Help:");
            Utils.sendMessageIfPermission("ultimatetools.ultimatetools", ChatColor.GREEN + "/ut" + ChatColor.WHITE + " - opens The Ultimate Tools GUI", sender);
            sender.sendMessage(ChatColor.GREEN + "/ut help" + ChatColor.WHITE + " - Help");
            Utils.sendMessageIfPermission("ultimatetools.set", ChatColor.GREEN + "/ut set" + ChatColor.WHITE + " - Set a setting Usage: /ut set <item> <type> <value>", sender);
            sender.sendMessage(ChatColor.GREEN + "/ut info" + ChatColor.WHITE + " - Info");
            Utils.sendMessageIfPermission("ultimatetools.give", ChatColor.GREEN + "/ut give" + ChatColor.WHITE + " - Giving a player a specific item of Ultimate Tools! Usage: /ut give <player> <name> [count]", sender);
            sender.sendMessage(ChatColor.GOLD + "More in the config.yml file!");
            return true;
        } else if (Objects.equals(args[0], "info")) {
            sender.sendMessage(ChatColor.AQUA + "This is Ultimate Tools v1.0 by §lGunterPro7 §r" + ChatColor.AQUA + "and §lICHGamer999§r" + ChatColor.AQUA + "!");
            return true;
        } else if (Objects.equals(args[0], "give")) {
            if (sender.hasPermission("ultimatetools.give")) {
                return new GiveCommand().onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else {
                sender.sendMessage(ChatColor.RED + "You have no Permission to run that command!");
                return true;
            }
        } else {
            sender.sendMessage("Could not find command '" + args[0] + "'!");
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> collection = new ArrayList<String>();
        if (args.length == 1) {
            String arg = args[0];
            if ("help".startsWith(arg)) {
                collection.add("help");
            }
            if ("set".startsWith(arg) && sender.hasPermission("ultimatetools.set")) {
                collection.add("set");
            }
            if ("info".startsWith(arg)) {
                collection.add("info");
            }
            if ("give".startsWith(arg) && sender.hasPermission("ultimatetools.give")) {
                collection.add("give");
            }
        } else if (args.length > 1 && Objects.equals(args[0], "set") && sender.hasPermission("ultimatetools.set")) {
            return new SetCommand().onTabComplete(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (args.length > 1 && Objects.equals(args[0], "give") && sender.hasPermission("ultimatetools.give")) {
            return new GiveCommand().onTabComplete(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        }
        return collection;
    }
}

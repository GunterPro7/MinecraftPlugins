package commands;

import costumItems.CodingBook;
import utils.Config;
import utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CodingBookCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player commandPlayer = (Player) sender;
        if (args.length == 0 || Objects.equals(args[0], "help")) {
            commandPlayer.sendMessage(ChatColor.GOLD + "Help:");
            Utils.sendMessageIfPermission("codingbook.stop", commandPlayer, ChatColor.YELLOW + "/codingbook stop " + ChatColor.GREEN + "- Stopping all current Books");
            Utils.sendMessageIfPermission("codingbook.continue", commandPlayer, ChatColor.YELLOW + "/codingbook continue " + ChatColor.GREEN + "- Continue all current Books");
            Utils.sendMessageIfPermission("codingbook.kill", commandPlayer, ChatColor.YELLOW + "/codingbook kill " + ChatColor.GREEN + "- Kill all running and stopped Books");
            Utils.sendMessageIfPermission("codingbook.allow_run_unsafe", commandPlayer, ChatColor.YELLOW + "/codingbook confirm " + ChatColor.GREEN + "- Confirms a risky Book action");
            Utils.sendMessageIfPermission("codingbook.help", commandPlayer, ChatColor.YELLOW + "/codingbook help " + ChatColor.GREEN + "- Openes the help page");
            Utils.sendMessageIfPermission("codingbook.give", commandPlayer, ChatColor.YELLOW + "/codingbook give " + ChatColor.GREEN + "- Giving <player> a book");
            Utils.sendMessageIfPermission("codingbook.convert", commandPlayer, ChatColor.YELLOW + "/codingbook convert " + ChatColor.GREEN + "- Converting a coding book to a runnable Coding Book!");
        }
        if (args.length > 0) {
            if (Objects.equals(args[0], "give")) {
                if (!sender.hasPermission("codingbook.give")) {
                    return Utils.noPermissionMessage(commandPlayer);
                }
                Player player;
                if (args.length > 1) {
                    player = Bukkit.getPlayer(args[1]);
                } else {
                    player = commandPlayer;
                }
                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "Player " + args[1] + " not found!");
                    return true;
                }
                player.getInventory().addItem(new CodingBook());
                String[] splittedMessage = Config.GAVE_CODINGBOOK.split("\\$\\{player}");
                if (splittedMessage.length == 1) {
                    player.sendMessage(ChatColor.GREEN + splittedMessage[0]);
                } else {
                    player.sendMessage(ChatColor.GREEN + splittedMessage[0] + player.getName() + splittedMessage[1]);
                }

            }
            else if (Objects.equals(args[0], "stop")) {
                if (!sender.hasPermission("codingbook.stop")) {
                    return Utils.noPermissionMessage(commandPlayer);
                }
                if (!CodingBook.getAllowRunning()) {
                    sender.sendMessage(ChatColor.RED + Config.ALREADY_STOPPED_CODINGBOOKS);
                    return true;
                }
                CodingBook.setAllowRunning(false);
                CodingBook.setKillEverything(false);
                sender.sendMessage(ChatColor.GREEN + Config.STOPPED_RUNNING_CODINGBOOKS);
            }
            else if (Objects.equals(args[0], "continue")) {
                if (!sender.hasPermission("codingbook.continue")) {
                    return Utils.noPermissionMessage(commandPlayer);
                }
                if (CodingBook.getAllowRunning()) {
                    sender.sendMessage(ChatColor.RED + Config.ALREADY_RUNNING_CODINGBOOKS);
                    return true;
                }
                CodingBook.setAllowRunning(true);
                CodingBook.setKillEverything(false);
                sender.sendMessage(ChatColor.GREEN + Config.CONTINUE_RUNNING_CODINGBOOKS);
            }
            else if (Objects.equals(args[0], "confirm")) {
                if (!sender.hasPermission("codingbook.allow_run_unsafe")) {
                    return Utils.noPermissionMessage(commandPlayer);
                }
                int counter;
                try {
                    counter = Integer.parseInt(args[1]);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax! Syntax: /codingBook confirm <number>");
                    return true;
                }
                if (!CodingBook.getActivePlayerConfirmations().contains(counter)) {
                    if (CodingBook.containsKey(counter)) {
                        sender.sendMessage(ChatColor.RED + Config.ACTION_EXPIRED);
                    } else {
                        sender.sendMessage(ChatColor.RED + Config.ACTION_NOT_CREATED_YET);
                    }
                    return true;
                }
                CodingBook.setPlayerConfirmation(counter);
                CodingBook.setAllowRunning(true);
                CodingBook.setKillEverything(false);
            }
            else if (Objects.equals(args[0], "kill")) {
                if (!sender.hasPermission("codingbook.kill")) {
                    return Utils.noPermissionMessage(commandPlayer);
                }
                CodingBook.setAllowRunning(false);
                CodingBook.setKillEverything(true);
                sender.sendMessage(ChatColor.GREEN + Config.KILLED_ALL_RUNNING_CODINGBOOKS);
            }
            else {
                sender.sendMessage(ChatColor.RED + Config.COMMAND_NOT_FOUND);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> collection = new ArrayList<>();
        if (args.length == 1) {
            for (String curPart : new String[]{"give", "continue", "kill", "stop"}) {
                if (curPart.startsWith(args[0]) && sender.hasPermission("codingbook." + curPart)) {
                    collection.add(curPart);
                }
            }
            if (CodingBook.getActivePlayerConfirmations().size() > 0) {
                if ("confirm".startsWith(args[0]) && sender.hasPermission("codingbook.confirm")) {
                    collection.add("confirm");
                }
            }
        }
        else if (args.length == 2) {
            if (Objects.equals(args[0], "give") && sender.hasPermission("codingbook.give")) {
                byte counter = 0;
                for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                    String currentPlayerName = currentPlayer.getName();
                    if (counter > 64) {
                        break;
                    }

                    if (currentPlayerName.startsWith(args[1])) {
                        collection.add(currentPlayerName);
                        counter++;
                    }
                }
            } else if (Objects.equals(args[0], "confirm") && sender.hasPermission("codingbook.confirm")) {
                byte counter = 0;
                for (int curInt : CodingBook.getActivePlayerConfirmations()) {
                    if (counter == 64) {
                        break;
                    }
                    if (String.valueOf(curInt).startsWith(args[1])) {
                        collection.add(String.valueOf(curInt));
                        counter++;
                    }
                }
            }
        }
        return collection;
    }
}

package commands;

import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GiveCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid Syntax! Usage: /ut give <player> <item>");
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Could not find Player " + args[1] + "!");
            return true;
        }
        ItemStack item = Main.getItemDict().get(args[1]).getItem();
        if (item == null) {
            sender.sendMessage(args[1] + " is not a Item!");
            return true;
        }
        item = item.clone();  // Das man nicht das Item, was man dann wieder den Spieler gibt zu count ... gibt
        int count = 1;
        if (args.length > 2) {
            try {
                count = Integer.parseInt(args[2]);
            } catch (NumberFormatException ignored) {}
        }
        if (count < 1 || count > 64) {
            sender.sendMessage(ChatColor.RED + "Invalid Count! count has to be between 1 and 64!");
            return true;
        }
        item.setAmount(count);
        player.getInventory().addItem(item);
        sender.sendMessage(ChatColor.GREEN + "Successfully gave " + player.getName() + " a " + Main.getItemDict().get(args[1]).getName() + "!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> collection = new ArrayList<String>();
        if (args.length == 1) {
            byte counter = 0;
            for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                String currentPlayerName = currentPlayer.getName();
                if (counter > 64) {
                    break;
                }

                if (currentPlayerName.startsWith(args[0])) {
                    collection.add(currentPlayerName);
                    counter++;
                }
            }
        } else if (args.length == 2) {
            for (String curItem : Main.getKeyList()) {
                if (curItem.startsWith(args[1])) {
                    collection.add(curItem);
                }
            }
        }
        return collection;
    }
}

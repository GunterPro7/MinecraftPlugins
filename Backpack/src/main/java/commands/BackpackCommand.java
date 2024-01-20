package commands;

import CostumItems.BackpackItem;
import main.Backpacks;
import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import utils.Messages;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class BackpackCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0 || Objects.equals(args[0], "help")) {
            sender.sendMessage(ChatColor.YELLOW + "Help:");
            if (sender.hasPermission("backpacks.info")) {
                sender.sendMessage("/bp " + ChatColor.GREEN + "info");
            }
            if (sender.hasPermission("backpacks.setsize")) {
                sender.sendMessage("/bp " + ChatColor.GREEN + "setsize <size: 9, 18, 27, 36, 45, 54>");
            }
            if (sender.hasPermission("backpacks.get")) {
                sender.sendMessage("/bp " + ChatColor.GREEN + "get");
            }
            sender.sendMessage("/bp " + ChatColor.GREEN + "help");
            if (sender.hasPermission("backpacks.setlore")) {
                sender.sendMessage("/bp " + ChatColor.GREEN + "setlore <lore>");
            }
            if (sender.hasPermission("backpacks.settitle")) {
                sender.sendMessage("/bp " + ChatColor.GREEN + "settitle <title>");
            }
            if (sender.hasPermission("backpacks.open")) {
                sender.sendMessage("/bp " + ChatColor.GREEN + "open <uuid>");
            }
            if (sender.hasPermission("backpacks.clear")) {
                sender.sendMessage("/bp " + ChatColor.GREEN + "clear <uuid>");
            }
            if (sender.hasPermission("backpacks.info")) {
                sender.sendMessage(ChatColor.GOLD + "More settings in the §lconfig.yml§r" + ChatColor.GOLD + " file!");
            }
        } else if (Objects.equals(args[0], "get")) {
            if (!sender.hasPermission("backpacks.get")) {
                sender.sendMessage(ChatColor.RED + "You have no permission to run that command!");
                return false;
            }
            String oldUUID = String.valueOf(UUID.randomUUID());
            Backpacks.addNewBackpackToListAndFile(oldUUID);
            try {
                Backpacks.saveInventoryToFile(oldUUID);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.getInventory().addItem(BackpackItem.createNewBackpack(oldUUID));

            String message = Messages.gaveNewBackpack;
            if (!message.contains("${value}")) {
                Messages.gaveNewBackpack = "Successfully gave ${value} a new Backpack!";
                Main.saveChangesToFile();
                message = Messages.gaveNewBackpack;
            }

            sender.sendMessage(ChatColor.GREEN + message.split("\\$\\{value\\}")[0] + sender.getName() + message.split("\\$\\{value\\}")[1]);
        } else if (Objects.equals(args[0], "info")) {
            if (!sender.hasPermission("backpacks.info")) {
                sender.sendMessage(ChatColor.RED + "You have no permission to run that command!");
                return false;
            }
            sender.sendMessage(ChatColor.GREEN + "This is Backpacks-Plugin by §lGunterPro7§r");
        } else if (Objects.equals(args[0], "setsize")) {
            if (!sender.hasPermission("backpacks.setsize")) {
                sender.sendMessage(ChatColor.RED + Messages.noPermission);
                return false;
            }
            int newSize;
            try {
                newSize = Integer.parseInt(args[1]);
            } catch (Exception err) {
                sender.sendMessage(ChatColor.RED + "Usage: /backpack setsize <size>");
                return true;
            }

            if (newSize < 0 || newSize > 54 || newSize % 9 != 0) {
                sender.sendMessage(ChatColor.RED + "Error: The size has to 9, 18, 27, 36, 45 or 54!");
                return true;
            }

            Main.continueResizeNewsize = newSize;
            Main.saveChangesToFile();
            sender.sendMessage(ChatColor.GREEN + Messages.changedSize + "§l" + newSize);
        } else if (Objects.equals(args[0], "setlore")) {
            if (!sender.hasPermission("backpacks.setlore")) {
                sender.sendMessage(ChatColor.RED + Messages.noPermission);
                return false;
            }
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /backpack setlore <lore>");
            } else {
                String newLore = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                Main.lore = newLore;
                Backpacks.enableCraftingRecipe();
                Main.saveChangesToFile();
                sender.sendMessage(ChatColor.GREEN + Messages.changedLore + newLore);
            }
        } else if (Objects.equals(args[0], "settitle")) {
            if (!sender.hasPermission("backpacks.settitle")) {
                sender.sendMessage(ChatColor.RED + Messages.noPermission);
                return false;
            }
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /backpack settitle <title>");
            } else {
                String newTitle = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                Main.title = newTitle;
                Backpacks.enableCraftingRecipe();
                Main.saveChangesToFile();
                sender.sendMessage(ChatColor.GREEN + Messages.changedTitle + newTitle);
            }
        } else if (Objects.equals(args[0], "open")) {
            if (!sender.hasPermission("backpacks.open")) {
                sender.sendMessage(ChatColor.RED + Messages.noPermission);
                return false;
            }
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /backpack open <uuid>");
            } else {
                String uuid = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

                if (Backpacks.playerDict.containsKey(uuid)) {
                    player.openInventory(Backpacks.getBPFromUUID(uuid).getInventory());
                    return true;
                }

                File directory = new File(Main.getPlugin().getDataFolder() + "/backpacks/");
                String filename = uuid + ".json";

                File file = new File(directory, filename);
                if (file.exists()) {
                    Backpacks.prepareLoadBackpackFromUUID(uuid);
                    player.openInventory(Backpacks.getBPFromUUID(uuid).getInventory());
                    return true;
                } else {
                    String message = Messages.inventoryNotExists;
                    if (!message.contains("${value}")) {
                        Messages.inventoryNotExists = "Inventory ${value} does not exist!";
                        Main.saveChangesToFile();
                        message = Messages.inventoryNotExists;
                    }
                    sender.sendMessage(ChatColor.RED + message.split("\\$\\{value\\}")[0] + args[1] + message.split("\\$\\{value\\}")[1]);
                }
            }
        } else if (Objects.equals(args[0], "clear")) {
            if (!sender.hasPermission("backpacks.clear")) {
                sender.sendMessage(ChatColor.RED + Messages.noPermission);
                return false;
            }
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /backpack clear <uuid>");
            } else {
                String uuid = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

                if (Backpacks.playerDict.containsKey(uuid)) {
                    Backpacks.clearInventory(uuid, player);
                    return true;
                }

                File directory = new File(Main.getPlugin().getDataFolder() + "/backpacks/");
                String filename = uuid + ".json";

                File file = new File(directory, filename);
                if (file.exists()) {
                    Backpacks.prepareLoadBackpackFromUUID(uuid);
                    Backpacks.clearInventory(uuid, player);
                    return true;
                } else {
                    String message = Messages.inventoryNotExists;
                    if (!message.contains("${value}")) {
                        Messages.inventoryNotExists = "Inventory ${value} does not exist!";
                        Main.saveChangesToFile();
                        message = Messages.inventoryNotExists;
                    }
                    sender.sendMessage(ChatColor.RED + message.split("\\$\\{value\\}")[0] + args[1] + message.split("\\$\\{value\\}")[1]);
                }
            }
        } else {
            String message = Messages.commandNotFound;
            if (!message.contains("${value}")) {
                Messages.commandNotFound = "Command '${value}' not found!";
                Main.saveChangesToFile();
                message = Messages.commandNotFound;
            }

            sender.sendMessage(ChatColor.RED + message.split("\\$\\{value\\}")[0] + args[0] + message.split("\\$\\{value\\}")[1]);
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String arg = args[0];
            if ("help".startsWith(arg)) {
                completions.add("help");
            }
            if ("info".startsWith(arg) && sender.hasPermission("backpacks.info")) {
                completions.add("info");
            }
            if ("setsize".startsWith(arg) && sender.hasPermission("backpacks.setsize")) {
                completions.add("setsize");
            }
            if ("setlore".startsWith(arg) && sender.hasPermission("backpacks.setlore")) {
                completions.add("setlore");
            }
            if ("settitle".startsWith(arg) && sender.hasPermission("backpacks.settitle")) {
                completions.add("settitle");
            }
            if ("get".startsWith(arg) && sender.hasPermission("backpacks.get")) {
                completions.add("get");
            }
            if ("open".startsWith(arg) && sender.hasPermission("backpacks.open")) {
                completions.add("open");
            }
            if ("clear".startsWith(arg) && sender.hasPermission("backpacks.clear")) {
                completions.add("clear");
            }
        }
        if (args.length == 2 && (Objects.equals(args[0], "open") && sender.hasPermission("backpacks.open") || Objects.equals(args[0], "clear") && sender.hasPermission("backpacks.clear"))) {
            String arg = args[1];
            int counter = 0;
            for (String uuid : Main.fileNames) {
                if (counter == 64) {
                    break;
                }
                if (uuid.startsWith(arg)) {
                    completions.add(uuid);
                }
                counter++;
            }
        }
        return completions;
    }
}

package commands;

import costumItems.CustomItemData;
import main.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Invalid Syntax! Usage: /ut set <item> <type> <value>");
            return true;
        }
        double value;
        try {
            value = Double.parseDouble(args[2].replace(',', '.'));
        } catch (NumberFormatException err) {
            sender.sendMessage(ChatColor.RED + "Invalid Syntax: Value has to be a number!");
            return true;
        }


        // Grappling Hook
        if (Objects.equals(args[0], "grappling_hook")) {
            if (Objects.equals(args[1], "range")) {
                if (value > 0 && value < 50) {
                    CustomItemData.grapplingHookRange = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Grappling hook range to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to be between 0 and 50!");
                }

            } else if (Objects.equals(args[1], "cooldown")) {
                if (value > 0) {
                    CustomItemData.grapplingHookCD = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Grappling hook cooldown to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to higher 0 or 0!");
                }

            } else {
                return Utils.cmdNotFound(args[1], sender);
            }

            // Infinity Boom
        } else if (Objects.equals(args[0], "infinity_boom")) {
            if (Objects.equals(args[1], "cooldown")) {
                if (value > 0 && value < 100) {
                    CustomItemData.boomCD = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Infinity Boom cooldown to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to be between 0 and 100!");
                }
            } else if (Objects.equals(args[1], "power")) {
                if (value > 0) {
                    CustomItemData.boomPower = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Infinity Boom Power to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to higher 0 or 0!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }

        // Infinity Pearl
        else if (Objects.equals(args[0], "infinity_pearl")) {
            if (Objects.equals(args[1], "cooldown")) {
                if (value > 0) {
                    CustomItemData.pearlCD = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Infinity Pearl cooldown to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to higher 0 or 0!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }

        // Infinity Pearl
        else if (Objects.equals(args[0], "lightning_bolt")) {
            if (Objects.equals(args[1], "cooldown")) {
                if (value >= 0) {
                    CustomItemData.boltCD = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Lightning Bolt cooldown to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to higher 0 or 0!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }

        // Terminator
        else if (Objects.equals(args[0], "terminator")) {
            if (Objects.equals(args[1], "speed")) {
                if (value > 0) {
                    CustomItemData.terminatorSpeed = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Terminator speed to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to higher 0 or 0!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }

        // Hyperion
        else if (Objects.equals(args[0], "hyperion")) {
            if (Objects.equals(args[1], "speed")) {
                if (value > 0) {
                    CustomItemData.hypeSpeed = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Hyperion speed to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to higher 0 or 0!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }

        // Winter Blast
        else if (Objects.equals(args[0], "winter_blast")) {
            if (Objects.equals(args[1], "power")) {
                if (value > 0 && value < 100) {
                    CustomItemData.winterBlastPower = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Winter Blast Power to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to be between 0 and 100!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }

        // Winter Blast
        else if (Objects.equals(args[0], "ice_wand")) {
            if (Objects.equals(args[1], "cooldown")) {
                if (value >= 0) {
                    CustomItemData.iceWandCD = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Ice Wand cooldown to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to be higher than 0 or 0!");
                }
            } else if (Objects.equals(args[1], "duration")) {
                if (value > 0) {
                    CustomItemData.iceWandDuration = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Ice Wand duration to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to be higher than 0!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }

        // Infinity Pickaxe
        else if (Objects.equals(args[0], "infinity_pickaxe")) {
            if (Objects.equals(args[1], "range")) {
                if (value > 0 && value <= 1024) {
                    CustomItemData.infinityPickaxeRange = (int) value;
                    sender.sendMessage(ChatColor.GREEN + "Set Infinity Pickaxe range to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to be between 0 and 1024!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }

        // Infinity Pickaxe
        else if (Objects.equals(args[0], "flame_thrower")) {
            if (Objects.equals(args[1], "cooldown")) {
                if (value >= 0) {
                    CustomItemData.flameThrowerCD = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Flame Thrower cooldown to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to be between 0 and 1024!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }


        // Infinity Wand
        else if (Objects.equals(args[0], "infinity_wand")) {
            if (Objects.equals(args[1], "range")) {
                if (value > 0 && value <= 1024) {
                    CustomItemData.infinityWandRange = value;
                    sender.sendMessage(ChatColor.GREEN + "Set Infinity Wand range to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to be between 0 and 1024!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        }


        // Infinity Sword
        else if (Objects.equals(args[0], "infinity_sword")) {
            if (Objects.equals(args[1], "range")) {
                if (value > 0 && value <= 1024) {
                    CustomItemData.infinitySwordRange = (int) value;
                    sender.sendMessage(ChatColor.GREEN + "Set Infinity Sword range to " + value + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax: The value has to be between 0 and 1024!");
                }
            } else {
                return Utils.cmdNotFound(args[1], sender);
            }
        } else {
            sender.sendMessage("Command '" + args[0] + "' not found!");
        }
        Main.saveSettingsToFile();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> collection = new ArrayList<String>();

        if (args.length == 1) {
            for (String curItem : Main.getKeyList()) {
                if (curItem.startsWith(args[0])) {
                    collection.add(curItem);
                }
            }
        }
        if (args.length == 2) {
            // Range
            if (Objects.equals(args[0], "grappling_hook") || Objects.equals(args[0], "infinity_pickaxe") || Objects.equals(args[0], "infinity_wand")) {
                if ("range".startsWith(args[1])) {
                    collection.add("range");
                }
            }

            // Cooldown
            if (Objects.equals(args[0], "grappling_hook") || Objects.equals(args[0], "infinity_boom") || Objects.equals(args[0], "infinity_pearl") || Objects.equals(args[0], "lightning_bolt") || Objects.equals(args[0], "ice_wand") || Objects.equals(args[0], "flame_thrower")) {
                if ("cooldown".startsWith(args[1])) {
                    collection.add("cooldown");
                }
            }

            // Speed
            if (Objects.equals(args[0], "terminator") || Objects.equals(args[0], "hyperion")) {
                if ("speed".startsWith(args[1])) {
                    collection.add("speed");
                }
            }

            // Power
            if (Objects.equals(args[0], "infinity_boom") || Objects.equals(args[0], "winter_blast")) {
                if ("power".startsWith(args[1])) {
                    collection.add("power");
                }
            }

            // Duration
            if (Objects.equals(args[0], "ice_wand")) {
                if ("duration".startsWith(args[1])) {
                    collection.add("duration");
                }
            }
        }
        return collection;
    }
}

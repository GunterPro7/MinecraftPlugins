package utils;

import costumItems.LockedSlot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static double round(double number, int multiply) {
        String pattern = repeat('#', multiply);
        return Double.parseDouble(new DecimalFormat("#." + pattern).format(number));
    }

    public static String repeat(char char_, int multiply) {
        StringBuilder returnString = new StringBuilder(String.valueOf(char_));
        for (int i = 0; i < multiply; i++) {
            returnString.append(char_);
        }
        return returnString.toString();
    }

    public static void sendCooldownMessage(Player player, double timeRemaining) {
        player.sendMessage(ChatColor.RED + "You are currently on cooldown. Please wait more " + ChatColor.YELLOW + timeRemaining + ChatColor.RED + " seconds!");
    }

    public static boolean canBreakBlock(Block block) {
        Material type = block.getType();
        return !type.equals(Material.BEDROCK) && !type.equals(Material.WATER) && !type.equals(Material.LAVA) && !type.equals(Material.AIR);
    }

    public static boolean canBeFarmland(Block block) {
        Material type = block.getType();
        return type.equals(Material.DIRT) || type.equals(Material.GRASS_BLOCK) || type.equals(Material.COARSE_DIRT) || type.equals(Material.DIRT_PATH);
    }

    public static boolean hasPlayerPermission(int customModelData, Player player) {
        switch (customModelData) {
            case 404040:
                return player.hasPermission("ultimatetools.craft.grappling_hook");
            case 404041:
                return player.hasPermission("ultimatetools.craft.hyperion");
            case 404042:
                return player.hasPermission("ultimatetools.craft.ice_wand");
            case 404043:
                return player.hasPermission("ultimatetools.craft.infinity_boom");
            case 404044:
                return player.hasPermission("ultimatetools.craft.infinity_lava_bucket");
            case 404045:
                return player.hasPermission("ultimatetools.craft.infinity_pearl");
            case 404046:
                return player.hasPermission("ultimatetools.craft.infinity_water_bucket");
            case 404047:
                return player.hasPermission("ultimatetools.craft.lightning_bolt");
            case 404048:
                return player.hasPermission("ultimatetools.craft.super_leap");
            case 404049:
                return player.hasPermission("ultimatetools.craft.terminator");
            case 404050:
                return player.hasPermission("ultimatetools.craft.winter_blast");
            case 404051:
                return player.hasPermission("ultimatetools.craft.flame_thrower");
            case 404052:
                return player.hasPermission("ultimatetools.craft.infinity_wand");
            case 404053:
                return player.hasPermission("ultimatetools.craft.infinity_pickaxe");
            case 404054:
                return player.hasPermission("ultimatetools.craft.infinity_sword");
            case 404055:
                return player.hasPermission("ultimatetools.craft.teddy_bear");
            case 404056:
                return player.hasPermission("ultimatetools.craft.auto_pick_up");
            case 404039:
                return player.hasPermission("ultimatetools.craft.three_by_three_super_tool");
            default:
                return true;
        }
    }

    public static void sendNoPermissionMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You have no Permission to use that Item!");
    }

    public static boolean cmdNotFound(String commandName, CommandSender sender) {
        sender.sendMessage("Command '" + commandName + "' not found!");
        return true;
    }

    public static List<String> readFile(BufferedReader file) {
        List<String> lines = new ArrayList<>();
        String line;
        try {
            while ((line = file.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static int checkCoords(int playerLoc, int blockLoc, boolean add1ToPlayerLoc) {
        if (add1ToPlayerLoc) {
            playerLoc++;
        }
        if (playerLoc < blockLoc) {
            return blockLoc - 1;
        } else if (playerLoc > blockLoc) {
            return blockLoc + 1;
        } else {
            return blockLoc;
        }
    }

    public static ItemStack getItemIfPermission(Player player, String permission, ItemStack item) {
        if (player.hasPermission(permission)) {
            return item;
        }
        return LockedSlot.item;
    }

    public static void sendMessageIfPermission(String permission, String message, Player player) {
        if (player.hasPermission(permission)) {
            player.sendMessage(message);
        }
    }

    public static void sendMessageIfPermission(String permission, String message, CommandSender sender) {
        sendMessageIfPermission(permission, message, (Player) sender);
    }

    public static BlockFace flipPlayerFacing(BlockFace facing) {
        switch (facing) {
            case NORTH:
                return BlockFace.SOUTH;
            case EAST:
                return BlockFace.WEST;
            case SOUTH:
                return BlockFace.NORTH;
            case WEST:
                return BlockFace.EAST;
            case UP:
            case DOWN:
            default:
                return facing;
        }
    }
}

package utils;

import org.bukkit.entity.Player;

import java.util.Arrays;

import static org.bukkit.Bukkit.getLogger;

public class Utils {
    public static String splitAb(String string, String regex, int index) {
        String[] splittedString = string.split(regex);
        return String.join(" ", Arrays.copyOfRange(splittedString, index, splittedString.length));
    }

    public void printItem(String itemToPrint) {
    }

    public static int checkCoordForTild(String coord, Player player, char direction) {
        if (coord.equals("~")) {
            switch (direction) {
                case 'x':
                    return (int) Math.round(player.getLocation().getX());
                case 'y':
                    return (int) Math.round(player.getLocation().getY());
                case 'z':
                    return (int) Math.round(player.getLocation().getZ());
                default:
                    return 0;
            }
        } else {
            return Integer.parseInt(coord);
        }
    }

    public static String repeat(String repeatString, int times) {
        StringBuilder repeatStringBuilder = new StringBuilder(repeatString);
        for (int i = 0; i < times-1; i++) {
            repeatStringBuilder.append(repeatString);
        }
        return repeatStringBuilder.toString();
    }

    public static int getInventorySize(String string) {
        return (string + ".").split(";").length;
    }
}

package utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import snake.SnakeLocation;
import snake.SnakeMap;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final byte green1 = -122;
    public static final byte green2 = -124;
    public static final byte snakeColor = 50;

    public static void setPixelArea(MapCanvas mapCanvas, int startX, int startY, int endX, int endY, byte color) {
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                mapCanvas.setPixel(x, y, color);
            }
        }
    }

    public static boolean isOutOfBoard(byte x, byte z) {
        return x > 126 || z > 126 || x < 0 || z < 0;
    }

    public static void sendLostMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You died!");

        ChatColor color;
        int length = SnakeMap.getPlayerInformation(player).getLength();

        if (length < 7) {
            color = ChatColor.RED;
        } else if (length < 15) {
            color = ChatColor.GOLD;
        } else if (length < 30) {
            color = ChatColor.YELLOW;
        } else if (length < 45){
            color = ChatColor.GREEN;
        } else {
            color = ChatColor.AQUA;
        }
        player.sendMessage(ChatColor.YELLOW + "You scored " + color + SnakeMap.getPlayerInformation(player).getLength() + ChatColor.YELLOW + ((length == 1) ? " point!" : " points!"));
    }

    public static boolean isInvalidPosition(byte b, byte b1, List<SnakeLocation> snakeLocations) {
        if (isOutOfBoard(b, b1)) {
            return true;
        }
        for (SnakeLocation snakeLocation : snakeLocations) {
            if (b == snakeLocation.getX() && b1 == snakeLocation.getZ()) {
                return true;
            }
        }
        return false;
    }

    public static byte getColorFromField(byte x, byte z) {
        if ((x / 16 + z / 16) % 2 == 0) {
            return green1;
        } else {
            return green2;
        }
    }

    public static List<SnakeLocation> getEmptyFields(List<SnakeLocation> snakeLocations) {
        List<SnakeLocation> freeSnakeLocations = new ArrayList<>();
        for (int i = 0; i < 127; i += 16) {
            for (int j = 0; j < 127; j += 16) {
                boolean conflict = false;
                for (SnakeLocation snakeLocation : snakeLocations) {
                    if (snakeLocation.equals((byte) i, (byte) j)) {
                        conflict = true;
                        break;
                    }
                }

                if (!conflict) {
                    freeSnakeLocations.add(new SnakeLocation((byte) i, (byte) j));
                }
            }
        }
        return freeSnakeLocations;
    }

    public static void sendWonMessage(Player player) {
        player.sendMessage(ChatColor.GREEN + "You have won the " + ChatColor.BOLD + "8x8 Snake Game!");
    }
}
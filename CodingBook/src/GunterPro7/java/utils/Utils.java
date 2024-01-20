package utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class Utils {
    public static final Random RANDOM = new Random();
    public static List<String> operationList = new ArrayList<>(Arrays.asList(" < ", " > ", " != ", " == ", " >= ", " <= "));
    public static void loadMessagesFromFile(File file) throws FileNotFoundException, NoSuchFieldException, IllegalAccessException {
        String[] lines = getLinesFromFile(file).split("#|-> ");
        for (String line : lines) {
            if (line.endsWith("\n")) {
                line = line.substring(0, line.length() - 1);
            }
            String[] splittedLine = line.split(": ");
            String var = splittedLine[0];
            String text = String.join(": ", Arrays.copyOfRange(splittedLine, 1, splittedLine.length));
            try {
                Field field = Config.class.getDeclaredField(var);
                field.set(null, text);
            } catch (Exception e) {
                try {
                    Field field = Config.class.getDeclaredField(var);
                    field.set(null, Boolean.parseBoolean(text.replaceAll("\n", "").replaceAll(" ", "")));
                } catch (Exception ignored) {

                }
            }
        }
    }

    public static void createMessagesFile(File file) throws IOException {
        String[] messagesFileList = new String[]{"# This is config.yml File by Coding-Book Plugin V1.0 by GunterPro7!", "# Messages:",
                "NO_PERMISSION: " + Config.NO_PERMISSION, "NO_UNSAFE_MODE_PERMISSION: " + Config.NO_UNSAFE_MODE_PERMISSION,
                "ALREADY_STOPPED_CODINGBOOKS: " + Config.ALREADY_STOPPED_CODINGBOOKS, "STOPPED_RUNNING_CODINGBOOKS: " + Config.STOPPED_RUNNING_CODINGBOOKS,
                "ALREADY_RUNNING_CODINGBOOKS: " + Config.ALREADY_RUNNING_CODINGBOOKS, "CONFIRM_UNSAFE_MODE: " + Config.CONFIRM_UNSAFE_MODE,
                "CONTINUE_RUNNING_CODINGBOOKS: " + Config.CONTINUE_RUNNING_CODINGBOOKS, "CONTINUE_RUNNING_CODINGBOOKS: " + Config.CONTINUE_RUNNING_CODINGBOOKS,
                "ACTION_EXPIRED: " + Config.ACTION_EXPIRED, "ACTION_NOT_CREATED_YET: " + Config.ACTION_NOT_CREATED_YET,
                "KILLED_ALL_RUNNING_CODINGBOOKS: " + Config.KILLED_ALL_RUNNING_CODINGBOOKS, "ACTION_CONFIRMING_CODINGBOOK: " + Config.ACTION_CONFIRMING_CODINGBOOK,
                "STARTED_RUNNING_CODINGBOOK: " + Config.STARTED_RUNNING_CODINGBOOK, "CONFIRMATION_EXPIRED: " + Config.CONFIRMATION_EXPIRED,
                "STARTED_RUNNING_CODINGBOOK_AFTER_CONFIRMATION: " + Config.STARTED_RUNNING_CODINGBOOK_AFTER_CONFIRMATION, "CODINGBOOK_STARTER_PAGE: " + Config.CODINGBOOK_STARTER_PAGE,
                "RUNNING_CODINGBOOK_INFORMATION: " + Config.RUNNING_CODINGBOOK_INFORMATION, "GAVE_CODINGBOOK: " + Config.GAVE_CODINGBOOK,
                "CONVERT_CODINGBOOK_NEEDED: " + Config.CONVERT_CODINGBOOK_NEEDED, "CONVERTED_CODINGBOOK: " + Config.CONVERTED_CODINGBOOK,
                "# Converting Book:", "CONVERT_BOOK_WITH_COLORS: " + Config.CONVERT_BOOK_WITH_COLORS, "COMMAND_COLOR: " + Config.COMMAND_COLOR,
                "OPERATION_COLOR: " + Config.OPERATION_COLOR, "VAR_COLOR: " + Config.VAR_COLOR, "COMMENT_COLOR: " + Config.COMMENT_COLOR,
                "LINE_MARKER_COLOR: " + Config.LINE_MARKER_COLOR, "INLINE_VAR_COLOR: " + Config.INLINE_VAR_COLOR, "VAR_COLOR: " + Config.VAR_COLOR,
                "DIGIT_COLOR: " + Config.DIGIT_COLOR,
        };

        FileWriter fileWriter = new FileWriter(file);
        for (String curMessage : messagesFileList) {
            String prefix = "";
            if (!curMessage.startsWith("#")) {
                prefix = "-> ";
            }
            fileWriter.write(prefix + curMessage + "\n");
        }
        fileWriter.close();
    }

    public static String getLinesFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException ignored) {
        }

        return stringBuilder.toString();
    }

    public static boolean isDigit(String digit) {
        try {
            Double.parseDouble(digit);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static void sendMessageIfPermission(String permission, Player player, String message) {
        if (player.hasPermission(permission)) {
            player.sendMessage(message);
        }
    }

    public static boolean noPermissionMessage(Player player) {
        player.sendMessage(ChatColor.RED + Config.NO_PERMISSION);
        return true;
    }

    public static List<String> getCommandoList(String s) {
        String[] splittedList = s.split("(?<= > )|(?= > )|(?<= != )|(?= != )|(?<= < )|(?= < )|(?<= == )|(?= == )|(?<= <= )|(?= <= )|(?<= >= )|(?= >= )");

        List<String> returnList = new ArrayList<>();
        for (String curPart : splittedList) {
            if (operationList.contains(curPart)) {
                returnList.add(curPart.trim());
            }
        }
        return returnList;
    }

    public static double toDouble(Object o) {
        try {
            return Double.parseDouble(((String) o).trim());
        } catch (NumberFormatException | ClassCastException e) {
            return 0;
        }
    }

    public static String replaceVar(String string, HashMap<String, Object> vars) {
        if (!string.contains("var:")) {
            return string;
        }
        String[] splittedString = string.split(" ");
        int counter = 0;
        for (String part : splittedString) {
            if (part.startsWith("var:")) {
                Object var = vars.get(part.replace("var:", ""));
                if (var != null) {
                    if (var instanceof Player) {
                        splittedString[counter] = ((Player) var).getName();
                    } else if (var instanceof World) {
                        splittedString[counter] = ((World) var).getName();
                    } else if (var instanceof Location) {
                        Location loc = ((Location) var);
                        splittedString[counter] = (int) loc.getX() + " " + (int) loc.getY() + " " + (int) loc.getZ();
                    } else if (var instanceof Boolean || var instanceof Integer) {
                        splittedString[counter] = String.valueOf(var);
                    } else if (var instanceof Block) {
                        Block block = ((Block) var);
                        splittedString[counter] = block.getType().getKey().toString().replaceAll("_", " ");
                    } else {
                        splittedString[counter] = var.toString();
                    }
                }
            }
            counter++;
        }
        return String.join(" ", splittedString);
    }

    public static TextComponent combineWithTooltip(String prefix, String suffix, String text, String tooltipText, String command, ClickEvent.Action action) {
        TextComponent message = new TextComponent(text);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(tooltipText).create()));
        message.setClickEvent(new ClickEvent(action, command));

        TextComponent returnText = new TextComponent(prefix);

        returnText.addExtra(message);
        returnText.addExtra(suffix);

        return returnText;
    }
}

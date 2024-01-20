package utils;

import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Config {
    public static String NO_PERMISSION = "You do not have permission to run that command!";
    public static String NO_UNSAFE_MODE_PERMISSION = "You do not have permission to run Coding Books in unsafe mode (0 ticks/line)! Please ask an administrator for permission or run the book with 1+ ticks/line!";
    public static String ALREADY_STOPPED_CODINGBOOKS = "You have already stopped all Coding Books!";
    public static String STOPPED_RUNNING_CODINGBOOKS = "Stopped running Coding Books! Use /codingBook continue to continue running.";
    public static String ALREADY_RUNNING_CODINGBOOKS = "You are already running all Coding Books!";
    public static String CONFIRM_UNSAFE_MODE = "You have chosen a speed of 0 ticks/line. If the code gets into an infinite loop or something similar, the only way to stop it is to manually terminate the server!";
    public static String CONTINUE_RUNNING_CODINGBOOKS = "Continue running Coding Books! Use /codingBook stop to stop running.";
    public static String ACTION_EXPIRED = "This action has expired!";
    public static String ACTION_NOT_CREATED_YET = "This action has not been created yet!";
    public static String KILLED_ALL_RUNNING_CODINGBOOKS = "Killed all running Coding Books! Use /codingBook continue to continue running.";
    public static String ACTION_CONFIRMING_CODINGBOOK = "Do ${value} within the next 20 seconds to confirm the action!";
    public static String STARTED_RUNNING_CODINGBOOK = "Use /codingbook stop or /codingbook kill to stop or kill the running command!";
    public static String CONFIRMATION_EXPIRED = "Your confirmation of /codingbook confirm ${value} has expired!";
    public static String STARTED_RUNNING_CODINGBOOK_AFTER_CONFIRMATION = "Started running confirmation ${value}!";
    public static String CODINGBOOK_STARTER_PAGE = "This is Coding Book v1.0 by GunterPro7!\n\nhttps://BlockCity.at/plugins\n\nAuthor: Player\nDate: \nSpeed(ticks/line): 2\nCode Purpose: ";  // Name, Author, and Speed(ticks/line): need to be included!
    public static String RUNNING_CODINGBOOK_INFORMATION = "Running ${author}'s Coding Book ${name} with a speed of ${speed} ticks/line!";
    public static String GAVE_CODINGBOOK = "Successfully gave ${player} a Coding Book!";
    public static String CONVERT_CODINGBOOK_NEEDED = "You need to convert the Coding Book first!";
    public static String CONVERTED_CODINGBOOK = "Successfully converted the Coding Book!";
    public static String COMMAND_NOT_FOUND = "This command does not exist!";

    public static boolean CONVERT_BOOK_WITH_COLORS = true;
    public static String COMMAND_COLOR = ChatColor.GOLD.toString();
    public static String OPERATION_COLOR = ChatColor.LIGHT_PURPLE.toString();
    public static String COMMENT_COLOR = ChatColor.GRAY.toString();
    public static String LINE_MARKER_COLOR = ChatColor.DARK_GRAY.toString();
    public static String INLINE_VAR_COLOR = ChatColor.DARK_AQUA.toString();
    public static String VAR_COLOR = ChatColor.DARK_GREEN.toString();
    public static String DIGIT_COLOR = ChatColor.BLUE.toString();

    private static final File file = new File(Main.getPlugin().getDataFolder(), "properties.json");
    public static final HashMap<String, String> map = new HashMap<>();


    public static void saveProperty(String key, String value) {
        map.put(key, value);
        savePropertyToFile(key, value);
    }

    public static String loadProperty(String key) {
        return map.get(key);
    }

    public static void readPropertyFile() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().info("Could not create 'properties.json' file, ERROR: " + e);
            }
            return;
        }
        try {
            for (Object line : new BufferedReader(new FileReader(file)).lines().toArray()) {
                String curLine = (String) line;
                map.put(curLine.split("=")[0], String.join("=", Arrays.copyOfRange(curLine.split("="), 1, curLine.split("=").length)));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void savePropertyToFile(String key, String value) {
        if (!map.containsKey(key)) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(key + "=" + value);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            StringBuilder lines = new StringBuilder();

            for (String curKey : map.keySet()) {
                String curValue = map.get(curKey);
                if (Objects.equals(curKey, key)) {
                    curValue = value;
                }
                lines.append(curKey).append('=').append(curValue).append('\n');
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(lines.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeProperty(String key) {
        map.remove(key);
        StringBuilder lines = new StringBuilder();

        for (String curKey : map.keySet()) {
            String curValue = map.get(curKey);
            lines.append(curKey).append('=').append(curValue).append('\n');
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(lines.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

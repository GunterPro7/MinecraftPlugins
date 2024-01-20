package utils;

import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodingBookColors {
    public static final List<String> commandList = new ArrayList<>(Arrays.asList("if", "goto", "minecraft", "log", "end;", "load", "save", "del", "random"));
    public static final List<String> varList = new ArrayList<>(Arrays.asList("Int", "String", "Boolean", "World", "Player", "Block", "Location"));
    public static final List<String> varList2 = new ArrayList<>(Arrays.asList("int", "var"));
    public static final List<String> operationList = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "++", "--", "^"));

    public static BookMeta changeCodingColors(BookMeta bookMeta) {
        List<String> pages = bookMeta.getPages();
        String[] stringList = new String[pages.size()];
        String curPage;

        stringList[0] = pages.get(0);

        for (int i = 0; i < pages.size(); i++) {
            if (i == 0) {
                continue;
            }
            StringBuilder string = new StringBuilder();
            boolean changedColor;
            curPage = ChatColor.stripColor(pages.get(i));
            for (String curPart : curPage.split("(?<= )(?!$)|(?<=;)(?!$)|(?<=\n)(?!$)")) {
                changedColor = false;
                String curPartCommands = curPart.replace(" ", "");
                if (commandList.contains(curPartCommands.toLowerCase().split(":")[0])) {
                    string.append(Config.COMMAND_COLOR);
                    changedColor = true;
                } else if (Utils.isDigit(curPartCommands.split(";")[0])) {
                    string.append(Config.DIGIT_COLOR);
                    changedColor = true;
                } else if (varList.contains(curPartCommands)) {
                    string.append(Config.VAR_COLOR);
                    changedColor = true;
                } else if (varList2.contains(curPartCommands.split(":")[0])) {
                    string.append(Config.INLINE_VAR_COLOR);
                    changedColor = true;
                } else if (curPartCommands.startsWith("<")) {
                    string.append(Config.LINE_MARKER_COLOR);
                    changedColor = true;
                } else if (curPartCommands.startsWith("#")) {
                    string.append(Config.COMMENT_COLOR);
                    changedColor = true;
                } else if (operationList.contains(curPartCommands)) {
                    string.append(Config.OPERATION_COLOR);
                    changedColor = true;
                }
                curPart = curPart.replace(";", ChatColor.RED + ";" + ChatColor.BLACK);
                string.append(curPart);
                if (changedColor) {
                    string.append(ChatColor.RESET);
                }

            }
            stringList[i] = string.toString();
        }

        List<String> newPages = new ArrayList<>(Arrays.asList(stringList));
        bookMeta.setPages(newPages);
        return bookMeta;
    }
}

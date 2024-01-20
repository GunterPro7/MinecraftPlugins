package costumItems;

import main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.inventory.ShapedRecipe;
import utils.Config;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import utils.Utils;

import java.util.*;

public class CodingBook extends ItemStack {
    public static final int UNIQUE_NUMBER = 785862000;
    private static boolean allowRunning = true;
    private static boolean killEverything;
    private static final HashMap<Integer, Boolean> playerConfirmed = new HashMap<>();
    private static int counter;

    public static void setPlayerConfirmation(int number) {
        playerConfirmed.put(number, true);
    }

    public static boolean containsKey(int value) {
        return playerConfirmed.containsKey(value);
    }

    public static boolean getKillEverything() {
        return killEverything;
    }

    public static void setKillEverything(boolean killEverything) {
        CodingBook.killEverything = killEverything;
    }

    public static boolean getAllowRunning() {
        return allowRunning;
    }

    public static void setAllowRunning(boolean allowRunning) {
        CodingBook.allowRunning = allowRunning;
    }

    public static List<Integer> getActivePlayerConfirmations() {
        List<Integer> returnList = new ArrayList<>();
        Set<Integer> integerSet = playerConfirmed.keySet();
        for (Integer integer : integerSet) {
            if (playerConfirmed.get(integer) != null && !playerConfirmed.get(integer)) {
                returnList.add(integer);
            }
        }
        return returnList;
    }

    public CodingBook() {
        super(Material.WRITABLE_BOOK);
        BookMeta bookMeta = (BookMeta) getItemMeta();
        bookMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Coding Book");
        bookMeta.setCustomModelData(UNIQUE_NUMBER);
        bookMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "item_id"), PersistentDataType.STRING, String.valueOf(UUID.randomUUID()));

        // Add pages to the book
        bookMeta.addPage(Config.CODINGBOOK_STARTER_PAGE);
        List<String> lore = new ArrayList<>();
        lore.add("§7§lWrite beautiful code!");
        bookMeta.setLore(lore);

        setItemMeta(bookMeta);
    }

    public static ShapedRecipe getCodingBookRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new CodingBook());
        recipe.shape(" DE", "CAD", " B ");
        recipe.setIngredient('A', Material.BOOK);
        recipe.setIngredient('B', Material.INK_SAC);
        recipe.setIngredient('C', Material.FEATHER);
        recipe.setIngredient('D', Material.REDSTONE);
        recipe.setIngredient('E', Material.STONE_BUTTON);
        return recipe;
    }

    public static void runCodingBook(Player player, ItemStack book) {
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        List<String> pages = bookMeta.getPages();
        StringBuilder allPages = new StringBuilder();
        String information = ChatColor.stripColor(pages.get(0));

        String[] informations = information.split("\n");
        int saveMode = 0;
        String author = "";
        String name = "";
        for (String curPart : informations) {
            if (curPart.startsWith("Author")) {
                author = curPart.split(":")[1].trim();
            }
            if (curPart.startsWith("Name")) {
                name = ChatColor.stripColor(bookMeta.getDisplayName());
            } else if (curPart.startsWith("Speed(ticks/line):")) {
                try {
                    saveMode = Integer.parseInt(curPart.split(":")[1].trim());
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Speed Parameter Format! Syntax: Speed(ticks/line): <number>");
                    return;
                }
                if (saveMode == 0 && !player.hasPermission("codingbook.allow_run_unsafe")) {
                    player.sendMessage(ChatColor.RED + Config.NO_UNSAFE_MODE_PERMISSION);
                    return;
                }
                String[] splittedInformation = Config.RUNNING_CODINGBOOK_INFORMATION.split("\\$\\{");
                for (int i = 0; i < splittedInformation.length; i++) {
                    String piece = splittedInformation[i];

                    if (i > 0) {
                        int index = piece.indexOf('}');
                        String variable = piece.substring(0, index);
                        String output = "";

                        if (variable.equals("author")) {
                            output = author;
                        } else if (variable.equals("name")) {
                            output = name;
                        } else if (variable.equals("speed")) {
                            output = String.valueOf(saveMode);
                        }

                        splittedInformation[i] = ChatColor.GOLD + output + ChatColor.GREEN + piece.substring(index).replace("}", ""); // Replacing the variable in the array
                    }
                }
                String readyMessage = String.join("", splittedInformation);
                player.sendMessage(ChatColor.GREEN + readyMessage);
                if (saveMode == 0) {
                    player.sendMessage(ChatColor.RED + "§l" + Config.CONFIRM_UNSAFE_MODE);
                    playerConfirmed.put(counter, false);
                    String[] splittedMessage = Config.ACTION_CONFIRMING_CODINGBOOK.split("\\$\\{value}");
                    if (splittedMessage.length == 1) {
                        player.sendMessage(splittedMessage[0]);
                    } else {
                        player.spigot().sendMessage(Utils.combineWithTooltip(ChatColor.DARK_RED + splittedMessage[0], ChatColor.DARK_RED + splittedMessage[1], ChatColor.RED + "/codingbook confirm " + counter,
                        "Run Command" + ChatColor.ITALIC + " /codingbook confirm " + counter, "/codingbook confirm " + counter++, ClickEvent.Action.RUN_COMMAND));
                    }
                } else {
                    player.sendMessage(ChatColor.YELLOW + Config.STARTED_RUNNING_CODINGBOOK);
                }
            }
        }

        for (int i = 1; i < pages.size(); i++) {
            allPages.append(ChatColor.stripColor(pages.get(i)));
        }

        String allPagesString = allPages.toString();
        allPagesString = ChatColor.stripColor(allPagesString.replaceAll("\n", ""));

        if (saveMode == 0) {
            checkForPlayerConfirmation(player, new ArrayList<>(Arrays.asList(allPagesString.split(";"))), new HashMap<>(), -1, saveMode, counter - 1, System.currentTimeMillis());
            return;
        }
        continueExecutingBook(player, new ArrayList<>(Arrays.asList(allPagesString.split(";"))), new HashMap<>(), -1, saveMode);
    }

    private static void checkForPlayerConfirmation(Player player, ArrayList<String> lines, HashMap<String, Object> vars, int index, int saveMode, int counter, long startingTime) {
        if (System.currentTimeMillis() - startingTime > 20000) {
            String[] splittedMessage = Config.CONFIRMATION_EXPIRED.split("\\$\\{value}");
            if (splittedMessage.length == 1) {
                player.sendMessage(splittedMessage[0]);
            } else {
                player.sendMessage(splittedMessage[0] + counter + splittedMessage[1]);
            }
            playerConfirmed.put(counter, null);
            return;
        }
        if (!playerConfirmed.get(counter)) {
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> checkForPlayerConfirmation(player, lines, vars, index, saveMode, counter, startingTime), 5);
            return;
        }
        String[] splittedMessage = Config.STARTED_RUNNING_CODINGBOOK_AFTER_CONFIRMATION.split("\\$\\{value}");
        if (splittedMessage.length == 1) {
            player.sendMessage(ChatColor.GREEN + splittedMessage[0]);
        } else {
            player.sendMessage(ChatColor.GREEN + splittedMessage[0] + counter + splittedMessage[1]);
        }

        Bukkit.getScheduler().runTask(Main.getPlugin(), () -> continueExecutingBook(player, lines, vars, index, saveMode));
    }

    public static void continueExecutingBook(Player player, List<String> lines, HashMap<String, Object> vars, int index, int saveMode) {
        if (killEverything) {
            return;
        }
        if (!allowRunning) {
            int finalIndex = index;
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> continueExecutingBook(player, lines, vars, finalIndex, saveMode), 20);
            return;
        }
        if (index + 1 == lines.size()) {
            return;
        }
        index++;
        String line = lines.get(index).trim();
        if (line.length() == 0) {
            int finalIndex = index;
            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> continueExecutingBook(player, lines, vars, finalIndex, saveMode));
            return;
        }
        int waitTime = 0;
        if (line.charAt(0) != '#' && line.charAt(0) != '<' && !line.startsWith("end")) {
            String[] splittedLine = line.split(" ");
            if (Objects.equals(splittedLine[0], "wait")) {
                try {
                    waitTime = (int) (Double.parseDouble(splittedLine[1]) * 20.0);
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    waitTime = 20;
                }
            } else if (Objects.equals(splittedLine[0], "goto")) {
                try {
                    index = gotoGetLine(lines, Integer.parseInt(splittedLine[1]));
                } catch (IllegalArgumentException e) {
                    player.sendMessage(ChatColor.RED + "Goto: Invalid Syntax. " + splittedLine[1] + " is not a valid line Syntax: goto 1234; - <1234>; #code;");
                }
            } else if (Objects.equals(splittedLine[0], "if")) {
                boolean var;
                String varString;

                boolean negate = false;

                if (Objects.equals(splittedLine[1], "!")) {
                    negate = true;
                    varString = splittedLine[2];
                } else {
                    varString = splittedLine[1];
                }
                try {
                    var = (boolean) vars.get(varString);
                } catch (ClassCastException e) {
                    player.sendMessage(ChatColor.RED + "Invalid IF Syntax! Syntax: if <var>: <goToLine>");
                    return;
                }

                if (negate) {
                    var = !var;
                }

                if (var) {
                    try {
                        int indexHere = 2;
                        if (negate) {
                            indexHere = 3;
                        }
                        index = gotoGetLine(lines, Integer.parseInt(splittedLine[indexHere]));
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + "Invalid IF Syntax! Syntax: if <var>: <goToLine>");
                    }
                }
            } else {
                runLine(player, line.replaceAll("\n", ""), vars);
            }
        } else if (line.startsWith("end")) {
            return;
        }

        int finalIndex = index;
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> continueExecutingBook(player, lines, vars, finalIndex, saveMode), waitTime + saveMode);
    }

    public static int gotoGetLine(List<String> lines, int index) throws IllegalArgumentException {
        int counter = 0;
        for (String line : lines) {
            if (line.startsWith("<")) {
                if (line.startsWith("<" + index + ">")) {
                    return counter;
                }
            }
            counter++;
        }
        throw new IllegalArgumentException("Index not found!");
    }

    public static void runLine(Player player, String line, HashMap<String, Object> vars) throws IllegalArgumentException {
        String[] splittedList = line.split(" ");
        String command = splittedList[0].split(":")[0];
        if (Objects.equals(command, "minecraft") || Objects.equals(command, "log")) {
            Set<String> keySet = vars.keySet();
            for (int i = 0; i < splittedList.length; i++) {
                String curPart = splittedList[i];
                if (curPart.startsWith("var:")) {
                    curPart = curPart.replace("var:", "");
                    if (keySet.contains(curPart)) {
                        if (vars.get(curPart) instanceof Player) {
                            splittedList[i] = ((Player) vars.get(curPart)).getName();
                        } else if (vars.get(curPart) instanceof World) {
                            splittedList[i] = ((World) vars.get(curPart)).getName();
                        } else if (vars.get(curPart) instanceof Location) {
                            Location loc = ((Location) vars.get(curPart));
                            splittedList[i] = (int) loc.getX() + " " + (int) loc.getY() + " " + (int) loc.getZ();
                        } else if (vars.get(curPart) instanceof Boolean || vars.get(curPart) instanceof Integer) {
                            splittedList[i] = String.valueOf(vars.get(curPart));
                        } else if (vars.get(curPart) instanceof Block) {
                            Block block = ((Block) vars.get(curPart));
                            splittedList[i] = block.getType().getKey().toString().replaceAll("_", " ");
                        } else {
                            splittedList[i] = vars.get(curPart).toString();
                        }
                    }
                }
            }
            line = String.join(" ", splittedList);
        }
        String[] splittedList2 = line.split(":");
        switch (command) {
            case "minecraft":
                Bukkit.dispatchCommand(player, Utils.replaceVar(String.join(" ", Arrays.copyOfRange(splittedList2, 1, splittedList2.length)), vars));
                break;
            case "log:":
                Bukkit.getLogger().info(Utils.replaceVar(String.join(" ", Arrays.copyOfRange(splittedList2, 1, splittedList2.length)), vars));
                break;
            case "Player":
                try {
                    vars.put(splittedList[1], Bukkit.getPlayer(Utils.replaceVar(line.split("=")[1].trim(), vars)));
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Player '" + line.split("=")[1].trim() + "' not found!");
                }
                break;
            case "World":
                String arg2 = line.split("=")[1].trim();
                if (arg2.startsWith("var:") && arg2.endsWith(".world")) {
                    String keyword = "var:";
                    String suffix = ".world";
                    int startIndex = arg2.indexOf(keyword) + keyword.length();
                    int endIndex = arg2.indexOf(suffix, startIndex);
                    String key = arg2.substring(startIndex, endIndex);

                    if (vars.get(key) instanceof Player) {
                        vars.put(splittedList[1], ((Player) vars.get(key)).getWorld());
                    } else if (vars.get(key) instanceof Block) {
                        vars.put(splittedList[1], ((Block) vars.get(key)).getWorld());
                    } else if (vars.get(key) instanceof Location) {
                        vars.put(splittedList[1], ((Location) vars.get(key)).getWorld());
                    } else {
                        player.sendMessage(ChatColor.RED + "Error when getting a World object from var: " + key + "! The Object wasn't a Player!");
                        break;
                    }
                } else {
                    try {
                        vars.put(splittedList[1], Bukkit.getWorld(line.split("=")[1].trim()));
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "World '" + line.split("=")[1].trim() + "' not found!");
                    }
                }
                break;
            case "Block":
                String blockArgs = line.split("=")[1].trim();
                if (blockArgs.startsWith("var:") && blockArgs.endsWith(".block")) {
                    String keyword = "var:";
                    String suffix = ".block";
                    int startIndex = blockArgs.indexOf(keyword) + keyword.length();
                    int endIndex = blockArgs.indexOf(suffix, startIndex);
                    String key = blockArgs.substring(startIndex, endIndex);

                    try {
                        vars.put(splittedList[1], ((Location) vars.get(key)).getBlock());
                    } catch (ClassCastException err) {
                        player.sendMessage(ChatColor.RED + "Error when getting a Block object from var: " + key + "! The Object wasn't a Location!");
                        break;
                    }
                }

                break;
            case "Location":
                String arg = line.split("=")[1].trim();
                if (arg.startsWith("var:") && arg.endsWith(".location")) {
                    String keyword = "var:";
                    String suffix = ".location";
                    int startIndex = arg.indexOf(keyword) + keyword.length();
                    int endIndex = arg.indexOf(suffix, startIndex);
                    String key = arg.substring(startIndex, endIndex);

                    try {
                        vars.put(splittedList[1], ((Player) vars.get(key)).getLocation());
                    } catch (ClassCastException err) {
                        player.sendMessage(ChatColor.RED + "Error when getting a Location object from var: " + key + "! The Object wasn't a Player!");
                        break;
                    }
                } else {
                    String[] args = line.split("=")[1].trim().split(" ");
                    if (args.length != 3 && args.length != 4) {
                        player.sendMessage(ChatColor.RED + "Wrong Syntax while creating Location variable. Syntax:");
                        player.sendMessage(ChatColor.RED + "Location <name> = <x> <y> <z> [world]");
                        break;
                    }
                    World curWorld;
                    if (args.length == 3) {
                        curWorld = player.getWorld();
                    } else {
                        try {
                            curWorld = Bukkit.getWorld(args[3]);
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "World '" + line.split("=")[1].trim() + "' not found!");
                            break;
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        if (args[i].startsWith("var:")) {
                            args[i] = String.valueOf(vars.get(args[i].replace("var:", "")));
                        }
                    }

                    try {
                        vars.put(splittedList[1], new Location(curWorld, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Wrong Syntax while creating Location variable. Syntax:");
                        player.sendMessage(ChatColor.RED + "Location <name> = <x> <y> <z> [world]");
                    }
                    break;
                }
                break;
            case "String":
                StringBuilder string = new StringBuilder();
                String[] args = line.split("=")[1].trim().split("\\+");
                for (String curPart : args) {
                    if (curPart.startsWith("var:")) {
                        curPart = curPart.replace("var:", "");
                        Object curObject = vars.get(curPart);
                        if (curObject instanceof Player) {
                            string.append(((Player) curObject).getName());
                        } else if (curObject instanceof World) {
                            string.append(((World) curObject).getName());
                        } else if (curObject instanceof Location) {
                            Location loc = ((Location) vars.get(curPart));
                            string.append(loc.getX()).append(" ").append(loc.getY()).append(" ").append(loc.getZ());
                        } else if (curObject instanceof Block) {
                            Block block = ((Block) vars.get(curPart));
                            string.append(block.getType().getKey().toString().replaceAll("_", " "));
                        } else {
                            string.append(curObject);
                        }
                    } else {
                        string.append(curPart);
                    }
                }
                vars.put(splittedList[1], string.toString());
                break;
            case "Boolean":
                if (line.contains("true")) {
                    vars.put(splittedList[1], true);
                } else if (line.contains("false")) {
                    vars.put(splittedList[1], false);
                } else {
                    boolean value = false;
                    String[] args2 = String.join("=", Arrays.copyOfRange(line.split("="), 1, line.split("=").length)).trim().split(" \\| ");
                    int counter;
                    int index = 0;
                    List<String> commandoList = Utils.getCommandoList(String.join("=", Arrays.copyOfRange(line.split("="), 1, line.split("=").length)).trim());
                    for (String curPart : args2) {
                        counter = 0;
                        String[] curSplittedPart = curPart.split(" & ");
                        for (String curAndPart : curSplittedPart) {
                            String[] curAndPartList = curAndPart.split(" == | != | < | > | <= | >= ");
                            Object[] objectList = new Object[2];
                            for (int i = 0; i < 2; i++) {
                                if (curAndPartList[i].startsWith("var:")) {
                                    curAndPartList[i] = curAndPartList[i].replace("var:", "");
                                    Object curObject = vars.get(curAndPartList[i]);
                                    if (curObject instanceof Player) {
                                        objectList[i] = ((Player) curObject).getName();
                                    } else if (curObject instanceof World) {
                                        objectList[i] = ((World) curObject).getName();
                                    } else if (curObject instanceof Location) {
                                        Location loc = ((Location) curObject);
                                        objectList[i] = Math.floor(loc.getX()) + ", " + Math.floor(loc.getY()) + ", " + Math.floor(loc.getZ()) + ", " + loc.getWorld();
                                    } else if (curObject instanceof Boolean) {
                                        objectList[i] = String.valueOf(vars.get(curPart));
                                    } else if (curObject instanceof Block) {
                                        objectList[i] = ((Block) curObject).getType();
                                    } else if (curObject instanceof String) {
                                        objectList[i] = curObject;
                                    } else {
                                        objectList[i] = curObject.toString();
                                    }
                                } else {
                                    objectList[i] = curAndPartList[i];
                                }
                            }
                            if (objectList[0] == null || objectList[1] == null) {
                                break;
                            }
                            boolean negation = false;
                            boolean bool = false;
                            switch (commandoList.get(index++)) {
                                case "!=":
                                    negation = true;
                                case "==":
                                    bool = objectList[0].equals(objectList[1]);
                                    break;
                                case "<":
                                    bool = Utils.toDouble(objectList[0]) < Utils.toDouble(objectList[1]);
                                    break;
                                case ">":
                                    bool = Utils.toDouble(objectList[0]) > Utils.toDouble(objectList[1]);
                                    break;
                                case ">=":
                                    bool = Utils.toDouble(objectList[0]) >= Utils.toDouble(objectList[1]);
                                    break;
                                case "<=":
                                    bool = Utils.toDouble(objectList[0]) <= Utils.toDouble(objectList[1]);
                                    break;
                            }
                            if (negation) {
                                bool = !bool;
                            }
                            if (bool) {
                                counter++;
                            }
                        }
                        if (counter == curSplittedPart.length) {
                            value = true;
                            break;
                        }
                    }
                    vars.put(splittedList[1], value);
                    break;
                }
                break;
            case "Int":
                try {
                    vars.put(splittedList[1], Double.parseDouble(Utils.replaceVar(line.split("=")[1].trim(), vars)));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Int Syntax! Syntax: Int <name> = <value>;");
                }
                break;
            case "random":
                try {
                    String[] parts = line.split("=")[1].trim().split(" ");
                    double from = Double.parseDouble(parts[0]);
                    double to = Double.parseDouble(parts[1]);
                    vars.put(splittedList[1], from + (to - from) * Utils.RANDOM.nextDouble());
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Int Syntax! Syntax: random <name> = <value>;");
                }
                break;
            case "int": // syntax     int:VariablenName + 50;
                String key = String.join(" ", Arrays.copyOfRange(splittedList2, 1, splittedList2.length)).split(" ")[0];
                double value = (double) vars.get(key);
                double value2;
                for (int i = 1; i < splittedList.length; i += 2) {
                    switch (splittedList[i]) {
                        case "++":
                            value++;
                            break;
                        case "--":
                            value--;
                            break;
                        default:
                            if (splittedList[i + 1].startsWith("int:")) {
                                value2 = Double.parseDouble(vars.get(splittedList[i + 1].replace("int:", "").replace(" ", "")).toString());
                            } else {
                                value2 = Double.parseDouble(splittedList[i + 1].replace(" ", ""));
                            }
                            switch (splittedList[i]) {
                                case "+":
                                    value += value2;
                                    break;
                                case "-":
                                    value -= value2;
                                    break;
                                case "*":
                                    value *= value2;
                                    break;
                                case "/":
                                    value /= value2;
                                    break;
                                case "^":
                                    value = Math.pow(value, value2);
                                    break;
                            }
                    }
                }
                vars.put(key, value);
                break;
            case "save":
                try {
                    Config.saveProperty(splittedList[1], Utils.replaceVar(line.split("=")[1].trim(), vars));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Int Syntax! Syntax: Int <name> = <value>;");
                }
                break;
            case "load":
                try {
                    vars.put(splittedList[1], Config.loadProperty(line.split("=")[1].trim()));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Int Syntax! Syntax: Int <name> = <value>;");
                }
                break;
            case "del":
                try {
                    Config.removeProperty(splittedList[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Int Syntax! Syntax: Int <name> = <value>;");
                }
                break;
        }
    }

    public static boolean isItemToRun(ItemStack item) {
        return checkModelData(item) && item.getType() == Material.WRITTEN_BOOK;
    }

    public static boolean isItemToConvert(ItemStack item) {
        return checkModelData(item) && item.getType() == Material.WRITABLE_BOOK;
    }

    private static boolean checkModelData(ItemStack item) {
        return item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }
}

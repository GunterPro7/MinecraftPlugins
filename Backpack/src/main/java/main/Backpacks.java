package main;

import CostumItems.BackpackItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import utils.Backpack;
import utils.Messages;
import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.bukkit.Bukkit.getLogger;

public class Backpacks {
    // Erstelle eine HashMap, die Spieler als Schlüssel und Integer als Werte speichert
    public static HashMap<String, Backpack> playerDict = new HashMap<String, Backpack>();
    private static HashMap<Player, String> playerUUID = new HashMap<Player, String>();

    public static Backpack getBPFromUUID(String uuid) {
        return playerDict.get(uuid);
    }

    public static String getUUIDFromPlayer(Player player) {
        return playerUUID.get(player);
    }

    public static String setUUIDForPlayer(Player player, String uuid) {
        return playerUUID.put(player, uuid);
    }

    public static void prepareLoadBackpackFromUUID(String uuid) {
        if (!Backpacks.playerDict.containsKey(uuid)) {
            Backpacks.loadBackpackFromUUID(uuid + ".json");
        }
    }

    public static void saveBasicInvToFile(String filename) {
        File currentFile = new File(Main.getPlugin().getDataFolder() + "/backpacks/" + filename);
        if (!currentFile.exists()) {
            try {
                currentFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(currentFile);
            fileWriter.write(filename.split(".json")[0] + "\n");
            fileWriter.write(Utils.repeat(";", Main.continueResizeNewsize));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadBackpackFromUUID(String filename) {
        File currentFile = new File(Main.getPlugin().getDataFolder() + "/backpacks/" + filename);
        if (!currentFile.exists()) {
            saveBasicInvToFile(filename);
        }
        List<String> backpackInfo = getAllBackpacksFromFile(currentFile);

        String uuid = backpackInfo.get(0);

        String[] itemList = backpackInfo.get(1).split(";");
        Inventory inv = Bukkit.createInventory(null, Utils.getInventorySize(backpackInfo.get(1)), Main.title);
        int counter = 0;
        for (String itemName : itemList) {
            Material material = Material.getMaterial(itemName.toUpperCase().split("\\{")[0]);
            if (material != null) {
                ItemStack itemStack = new ItemStack(material);
                if (itemName.contains("size:")) {
                    String size = itemName.split("size:")[1].split(",")[0];
                    itemStack.setAmount(Integer.parseInt(size));
                } if (itemName.contains("durability")) {
                    String durability = itemName.split("durability:")[1].split(",")[0];
                    itemStack.setDurability(Short.parseShort(durability));
                }
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemName.contains("Enchantment")) {
                    String enchantments = ", Enchantment" + itemName.split("\\{Enchantment")[1].split("\\}")[0];
                    String[] enchantmentList = enchantments.split(", Enchantment");

                    for (String s : enchantmentList) {
                        if (Objects.equals(s, "")) {
                            continue;
                        }
                        short lvl = Short.parseShort(s.split("=")[1]);
                        String name = s.split("minecraft:")[1].split(",")[0];
                        NamespacedKey key = new NamespacedKey("minecraft", name);
                        Enchantment enchantment = Enchantment.getByKey(key);
                        if (enchantment != null && itemMeta != null) {
                            itemMeta.addEnchant(enchantment, lvl, true);
                        }
                    }

                } if (itemName.contains("displayname:")) {
                    if (itemMeta != null) {
                        itemMeta.setDisplayName(itemName.split("displayname:")[1].split(",")[0]);
                    }
                }

                itemStack.setItemMeta(itemMeta);
                inv.setItem(counter, itemStack);
            }
            counter++;
        }

        Backpack bp = new Backpack(inv);
        playerDict.put(uuid, bp);
        Main.fileNames.add(uuid);
    }

    public static List<String> getAllBackpacksFromFile(File file) {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static void addNewBackpackToListAndFile(String uuid) {
        Backpack newBackpack = new Backpack(Main.continueResizeNewsize);
        playerDict.put(uuid, newBackpack);
        Main.fileNames.add(uuid);
    }

    public static void saveInventoryToFile(String uuid) throws IOException {
        Backpack playerBP = getBPFromUUID(uuid);
        Inventory inv = playerBP.getInventory();
        StringBuilder invString = new StringBuilder();

        // Adds all the items that can have durability or enchantments to a list
        ArrayList<Material> itemsWithEnchantsOrDurability = new ArrayList<>();
        for (Material material : Material.values()) {
            // Get the item stack and item meta
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            // Check if the item has either enchants or durability
            if (meta != null) {
                if (meta.hasEnchants() || meta instanceof Damageable) {
                    itemsWithEnchantsOrDurability.add(material);
                }
            }

        }

        // Adds all items in the backpack to a list
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item != null) {
                ItemMeta itemMeta = item.getItemMeta();
                if (itemsWithEnchantsOrDurability.contains(item.getType())) {
                    ItemMeta meta = item.getItemMeta();
                    invString.append(meta.toString());
                } else {
                    Material material = item.getType();
                    invString.append(material.name());
                }
                invString.append("{");
                int size = item.getAmount();
                if (size != 1) {
                    invString.append("size:").append(size).append(",");
                }
                if (itemMeta != null) {
                    if (itemMeta.hasEnchants()) {
                        invString.append(itemMeta.getEnchants()).append(",");
                    }
                    int durability = item.getDurability();
                    if (durability != 0) {
                        invString.append("durability:");
                        invString.append(item.getDurability()).append(",");
                    }
                    if (itemMeta.hasDisplayName()) {
                        invString.append("displayname:");
                        invString.append(itemMeta.getDisplayName()).append(",");
                    }
                }
                invString.append("}");
            }
            invString.append(";");
        }
        invString.deleteCharAt(invString.length() - 1);


        File file = new File(Main.getPlugin().getDataFolder() + "/backpacks/" + uuid + ".json");
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            FileWriter writer = new FileWriter(Main.getPlugin().getDataFolder() + "/backpacks/" + uuid + ".json");
            writer.write(uuid + "\n");
            writer.write(invString + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void enableCraftingRecipe() {
        createNewBackpack();
    }

    public static void createNewBackpack() {
        NamespacedKey key = new NamespacedKey(Main.getPlugin(), "Backpack_" + UUID.randomUUID());
        ItemStack costumItem = BackpackItem.createNewBackpack();
        ShapedRecipe recipe = new ShapedRecipe(key, costumItem);
        recipe.shape("DLD", "LNL", "DLD");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        recipe.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(recipe);
    }

    public static void clearInventory(String uuid, Player player) {
        int oldSlots = playerDict.get(uuid).getInventory().getSize();
        String message = Messages.inventoryCleared;
        if (!message.contains("${value}")) {
            Messages.inventoryCleared = "Inventory ${value} cleared successful!";
            Main.saveChangesToFile();
            message = Messages.inventoryCleared;
        }
        player.sendMessage(ChatColor.GREEN + message.split("\\$\\{value\\}")[0] + uuid + message.split("\\$\\{value\\}")[1]);
        playerDict.put(uuid, new Backpack(oldSlots));
    }
}

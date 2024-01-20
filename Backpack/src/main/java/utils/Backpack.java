package utils;

import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Backpack {
    private Inventory inventory;

    public Backpack(int slots) {
        this.inventory = Bukkit.createInventory(null, slots, Main.title);
    }

    public Backpack(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean addToFile(Player player) {
        // Erstelle eine Instanz der Plugin-Klasse, um auf das Plugin-Verzeichnis zugreifen zu können
        Plugin plugin = Main.getPlugin();

        // Erstelle einen neuen Ordner namens "data" im Plugin-Verzeichnis, falls er noch nicht existiert
        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        // Erstelle eine Datei namens "waypoints.txt" im "data"-Ordner
        File waypointsFile = new File(dataFolder, player.getName() + ".json");
        if (!waypointsFile.exists()) {
            try {
                waypointsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        // Schreibe die Waypoints in die Datei
        try (FileWriter writer = new FileWriter(waypointsFile, true)) {
            writer.write(toString() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public String toString() {
        return inventory.toString();
    }

    /*public static Backpack fromString(String backpackString) {
        /*String[] args = backpackString.split(",");
        World world = Bukkit.getWorld(args[1]);
        String itemsString = args[0];
        List<String> itemList = Arrays.asList(itemsString.split(","));

        // create a new inventory with the same size and title as the original
        Inventory newInventory = Bukkit.createInventory(null, inventory.getSize(), inventory.getTitle());

// set the items to their corresponding slots
        for (int i = 0; i < itemList.size(); i++) {
            ItemStack item = itemList.get(i);
            int slot = slots.get(i);
            newInventory.setItem(slot, item);
        }


        int z = Integer.parseInt(args[2]);
        String name = args[3];
        World world = Bukkit.getWorld(args[4].split("=")[1].replace("}", ""));
        return new Backpack(world, 54);
        return new Backpack(Bukkit.getWorld("world"), 2);
    }*/
}

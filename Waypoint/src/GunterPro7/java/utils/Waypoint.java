package utils;

import main.Main;
import main.Waypoints;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Waypoint {
    private final int x;
    private final int y;
    private final int z;
    private final String name;
    private final World world;

    public Waypoint(int x, int y, int z, String name, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public String getName() {
        return name;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public World getWorld() {
        return world;
    }

    public String getWorldName() {
        String worldName = world.getName();
        switch (worldName) {
            case "world":
                worldName = "overworld";
                break;
            case "world_nether":
                worldName = "the_nether";
                break;
            case "world_the_end":
                worldName = "the_end";
                break;
        }
        return worldName;
    }

    public boolean addToFile() {
        // Erstelle eine Instanz der Plugin-Klasse, um auf das Plugin-Verzeichnis zugreifen zu können
        Plugin plugin = Main.getPlugin();

        // Erstelle einen neuen Ordner namens "data" im Plugin-Verzeichnis, falls er noch nicht existiert
        File dataFolder = new File(plugin.getDataFolder().toString());
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        // Erstelle eine Datei namens "waypoints.txt" im "data"-Ordner
        File waypointsFile = new File(dataFolder, "waypoints.txt");
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
        return x + "," + y + "," + z + "," + name + "," + world;
    }

    public static Waypoint fromString(String waypointString) {
        String[] args = waypointString.split(",");
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        String name = args[3];
        World world = Bukkit.getWorld(args[4].split("=")[1].replace("}", ""));
        return new Waypoint(x, y, z, name, world);
    }
}

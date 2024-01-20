package main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.EulerAngle;
import utils.Waypoint;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Waypoints {
    private static final List<Waypoint> waypoints = new ArrayList<>();

    public static List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public static void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
        Location loc = new Location(waypoint.getWorld(), waypoint.getX(), waypoint.getY(), waypoint.getZ());
        ArmorStand armorStand = (ArmorStand) waypoint.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        PersistentDataContainer container = armorStand.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Main.getPlugin(), "waypoint");
        container.set(key, PersistentDataType.INTEGER, 404040);
        armorStand.setCustomName(waypoint.getName());
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
    }

    public static void clearWaypoints() {
        waypoints.clear();
    }

    public static void removeWaypoint(int index) {
        waypoints.remove(index);
    }

    public static void loadWaypointsFromFile() {
        File waypointsFile = new File(Main.getPlugin().getDataFolder(), "waypoints.txt");
        if (waypointsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(waypointsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Waypoint waypoint = Waypoint.fromString(line);
                    if (waypoint != null) {
                        addWaypoint(waypoint);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeWaypointFromFile(int index) {
        File file = new File(Main.getPlugin().getDataFolder(), "waypoints.txt");
        List<String> lines = getAllWaypointsFromFile(file);

        if (index >= 0 && index < lines.size()) {
            lines.remove(index);

            try {
                FileWriter writer = new FileWriter(file);
                for (String line : lines) {
                    writer.write(line + "\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> getAllWaypointsFromFile(File file) {
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

    public static void removeAllWaypoints() {
        File file = new File(Main.getPlugin().getDataFolder(), "waypoints.txt");
        List<String> lines = getAllWaypointsFromFile(file);

        for (String line : lines) {
            Waypoint currentWaypoint = Waypoint.fromString(line);
            removeWaypoint(currentWaypoint);
        }
    }

    public static void removeWaypoint(Waypoint currentWaypoint) {
        Location location = new Location(currentWaypoint.getWorld(), currentWaypoint.getX(), currentWaypoint.getY(), currentWaypoint.getZ());
        List<Entity> entities = (List<Entity>) currentWaypoint.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5);
        for (Entity entity : entities) {
            if (entity instanceof ArmorStand && entity.getLocation().equals(location)) {
                PersistentDataContainer container = entity.getPersistentDataContainer();
                int value;
                try {
                    value = container.get(Main.KEY, PersistentDataType.INTEGER);
                } catch (NullPointerException e) {
                    continue;
                }
                if (value == 404040) {
                    entity.remove();
                    break;
                }
            }
        }
    }

    public static Waypoint getWaypointByName(String name) {
        name = name.toLowerCase();
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getName().toLowerCase().equals(name)) {
                return waypoint;
            }
        }
        return null;
    }

    public static boolean isDuplicateWaypoint(Waypoint newWaypoint) {
        return getDuplicatedWaypointIndex(newWaypoint) != -1;
    }

    public static int getDuplicatedWaypointIndex(Waypoint newWaypoint) {
        int counter = 0;
        for (Waypoint waypoint : Waypoints.getWaypoints()) {
            if (waypoint.getX() == newWaypoint.getX() && waypoint.getY() == newWaypoint.getY()
                    && waypoint.getZ() == newWaypoint.getZ() && waypoint.getWorld().equals(newWaypoint.getWorld()) ||
                    Objects.equals(waypoint.getName().toLowerCase(), newWaypoint.getName().toLowerCase())) {
                return counter;
            }
            counter++;
        }
        return -1;
    }
}

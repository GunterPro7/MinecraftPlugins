package utils;

import main.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

// Erstelle eine Runnable-Klasse, die den Partikel-Effekt erzeugt
public class BeaconEffect implements Runnable {

    // Definiere die Welt, in der der Effekt stattfindet
    private final World world;

    // Definiere die Position, an der der Effekt startet
    private final double x;
    private final double y;
    private final double z;

    // Definiere die Höhe, bis zu der der Effekt reicht
    private final double height;

    // Definiere den Partikel-Typ und die Farbe
    private final Particle particle;
    private final Particle.DustOptions color;
    private final int taskId;
    private final Player player;

    // Erstelle einen Konstruktor für die Klasse
    public BeaconEffect(Player player, World world, double x, double y, double z, double height, Particle particle, Particle.DustOptions color) {
        this(player, world, x, y, z, height, particle, color, 200);
    }

    public BeaconEffect(Player player, World world, double x, double y, double z, double height, Particle particle, Particle.DustOptions color, int duration) {
        this.player = player;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.height = height;
        this.particle = particle;
        this.color = color;
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), this, 0L, 1L);
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            Bukkit.getScheduler().cancelTask(taskId);
            world.strikeLightning(player.getLocation());
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                ItemStack[] itemStacks = player.getInventory().getContents();
                player.teleport(Worlds.WORLD_BOSS.getWorld().getSpawnLocation());
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    for (int i = 0; i < itemStacks.length; i++) {
                        player.getInventory().setItem(i, itemStacks[i]);
                    }
                }, 1);
            }, 4);
        }, duration);
    }

    // Überschreibe die run-Methode, die den Partikel-Effekt erzeugt
    @Override
    public void run() {
        // Erstelle eine Schleife, die von der Startposition bis zur Höhe läuft
        for (double i = y; i < y + height; i += 0.25) {
            // Erzeuge einen Partikel an der aktuellen Position mit der gewählten Farbe
            world.spawnParticle(particle, x, i, z, 0, 0.0D, 0.0D, 0.0D, 1.0D, color);
        }
    }
}

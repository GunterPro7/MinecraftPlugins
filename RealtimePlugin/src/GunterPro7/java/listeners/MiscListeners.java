package listeners;

import fragments.TreasureFragment;
import main.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidSpawnWaveEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.Inventory;
import utils.Coordinate;
import utils.RandomCoords;
import utils.Utils;

import java.util.Arrays;

public class MiscListeners implements Listener {
    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event) { // TODO wenn in Boss welt dann muss die Landschaft etc verändert werden in diesen event hier...
        Chunk chunk = event.getChunk();
        Utils.waitForLoaded(chunk);

        for (BlockState tile : chunk.getTileEntities()) {
            if (tile instanceof Chest) {
                Chest chest = (Chest) tile;
                Inventory inventory = chest.getBlockInventory();

                if (Main.RANDOM.nextInt(10) == 1) {
                    chest.getBlockInventory().setItem(Main.RANDOM.nextInt(inventory.getSize()), new TreasureFragment());
                }
                if (Main.RANDOM.nextInt(100) == 1) {
                    chest.getBlockInventory().setItem(Main.RANDOM.nextInt(inventory.getSize()), Utils.getRandomSpawnEgg());
                }
            }
        }
    }

    @EventHandler
    public void onRaidSpawnWave(RaidSpawnWaveEvent event) {
        if (Main.RANDOM.nextInt(5) == 1) {
            Location location = event.getPatrolLeader().getLocation();
            World world = event.getWorld();
            world.spawnEntity(location, EntityType.ILLUSIONER);
        }
    }
}

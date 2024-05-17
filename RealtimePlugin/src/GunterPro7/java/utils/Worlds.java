package utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

public enum Worlds {
    WORLD(Bukkit.getWorld("world")),
    WORLD_NETHER(Bukkit.getWorld("world_nether")),
    WORLD_THE_END(Bukkit.getWorld("world_the_end")),
    WORLD_BOSS(Bukkit.getWorld("world_boss_area")),
    WORLD_SKYBLOCK(Bukkit.getWorld("world_skyblock")),
    ;

    World world;

    Worlds(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}

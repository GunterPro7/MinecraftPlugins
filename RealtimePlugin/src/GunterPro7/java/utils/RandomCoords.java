package utils;

import main.Main;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.Random;

public class RandomCoords {
    private boolean old;
    public RandomCoords(boolean old) {
        this.old = old;
    }

    public Coordinate nextCoordinate() {
        Random random = Main.RANDOM;
        return new Coordinate(random.nextInt(16), old ? random.nextInt(256) : random.nextInt(384) - 64, random.nextInt(16));
    }

    public Coordinate[] nextCoordinates(int size) {
        return Arrays.stream(new int[size]).mapToObj(i -> nextCoordinate()).toArray(Coordinate[]::new);
    }
}


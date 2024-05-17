package listeners;

import main.Main;
import org.bukkit.map.MapCanvas;
import snake.SnakeInformation;
import snake.SnakeLocation;
import snake.SnakeMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import utils.Utils;

import java.util.List;

public class Run {
    BukkitScheduler scheduler = Bukkit.getScheduler();


    public Run(Player player) {
        run(player);
    }

    private void run(Player player) {
        SnakeInformation snakeInformation = SnakeMap.getPlayerInformation(player);
        if (snakeInformation.getWon()) {
            Utils.sendWonMessage(player);
            return;
        }
        List<SnakeLocation> snakeLocations = snakeInformation.getSnakeLocations();
        int snakeLocationsSize = snakeLocations.size();
        MapCanvas mapCanvas = snakeInformation.getMapCanvas();
        int snakeLength = snakeInformation.getLength();

        byte color = Utils.snakeColor;
        byte x = 0;
        byte z = 0;
        int counter = 0;

        SnakeLocation snakeLocationToRemove = null;
        for (SnakeLocation snakeLocation : snakeLocations) {
            x = snakeLocation.getX();
            z = snakeLocation.getZ();
            counter++;
            if (counter <= snakeLocationsSize - snakeLength) {
                Utils.setPixelArea(mapCanvas, x + 4, z + 4, x + 11, z + 11, Utils.getColorFromField(x, z));
                snakeLocationToRemove = snakeLocation;
            } else {
                Utils.setPixelArea(mapCanvas, x + 4, z + 4, x + 11, z + 11, color);
            }
        }
        if (snakeLocationToRemove != null) {
            snakeLocations.remove(snakeLocationToRemove);
        }

        byte offsetX = 0;
        byte offsetZ = 0;
        switch (snakeInformation.getDirection()) {
            case UP:
                offsetZ = -16;
                break;
            case LEFT:
                offsetX = -16;
                break;
            case RIGHT:
                offsetX = 16;
                break;
            case DOWN:
                offsetZ = 16;
                break;
        }

        if (Utils.isInvalidPosition((byte) (x + offsetX), (byte) (z + offsetZ), snakeLocations)) {
            Utils.sendLostMessage(player);
            SnakeMap.addPlayerInformation(player);
            return;
        }


        if (snakeInformation.getAppleLocation().equals(new SnakeLocation((byte) (x + offsetX), (byte) (z + offsetZ)))) {
            snakeInformation.setAppleOnScreen(false);
            snakeInformation.setLength(snakeLength + 1);
        }

        snakeLocations.add(new SnakeLocation((byte) (x + offsetX), (byte) (z + offsetZ)));
        Utils.setPixelArea(mapCanvas, x + offsetX + 4, z + offsetZ + 4, x + offsetX + 11, z + offsetZ + 11, color);

        if (snakeInformation.isPlaying()) {
            scheduler.runTaskLater(Main.getPlugin(), () -> run(player), 7);
        }
    }
}

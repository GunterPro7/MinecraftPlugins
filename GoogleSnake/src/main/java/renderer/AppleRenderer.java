package renderer;

import main.Main;
import org.bukkit.Bukkit;
import snake.SnakeInformation;
import snake.SnakeLocation;
import snake.SnakeMap;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import utils.Utils;

import java.util.List;

public class AppleRenderer extends MapRenderer {
    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        SnakeInformation snakeInformation = SnakeMap.getPlayerInformation(player);
        if (snakeInformation.isAppleOnScreen() || snakeInformation.getMapCanvas() == null) {
            return;
        }

        SnakeLocation appleLocation = snakeInformation.getAppleLocation();
        if (appleLocation != null) {
            //Utils.setPixelArea(mapCanvas, appleLocation.getX() + 4, appleLocation.getZ() + 4, appleLocation.getX() + 11, appleLocation.getZ() + 11, Utils.getColorFromField(appleLocation.getX(), appleLocation.getZ()));
        }

        List<SnakeLocation> emptyFields = Utils.getEmptyFields(snakeInformation.getSnakeLocations());
        SnakeLocation location;
        int emptyFieldsSize = emptyFields.size();
        if (emptyFieldsSize == 0) {
            snakeInformation.setWon(true);
            return;
        } else {
            location = emptyFields.get(Main.RANDOM.nextInt(emptyFieldsSize));
        }

        int posX = location.getX();
        int posZ = location.getZ();

        Utils.setPixelArea(snakeInformation.getMapCanvas(), posX + 4, posZ + 4, posX + 11, posZ + 11, (byte) 16);

        snakeInformation.setAppleLocation(new SnakeLocation((byte) posX, (byte) posZ));
        snakeInformation.setAppleOnScreen(true);
    }
}

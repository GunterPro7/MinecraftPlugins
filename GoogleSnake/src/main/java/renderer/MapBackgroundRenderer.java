package renderer;

import org.bukkit.Bukkit;
import org.bukkit.map.MapPalette;
import snake.SnakeMap;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import utils.Utils;

public class MapBackgroundRenderer extends MapRenderer {
    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if (SnakeMap.getPlayerInformation(player).isBackgroundLoaded()) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                byte color;
                if ((i + j) % 2 == 0) {
                    color = Utils.green1;
                } else {
                    color = Utils.green2;
                }

                Utils.setPixelArea(mapCanvas, i * 16, j * 16, i * 16 + 16, j * 16 + 16, color);
            }
        }
        SnakeMap.getPlayerInformation(player).setMapCanvas(mapCanvas);
        SnakeMap.getPlayerInformation(player).setBackgroundLoaded(true);
    }
}

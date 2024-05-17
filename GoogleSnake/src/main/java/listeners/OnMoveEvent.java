package listeners;

import snake.SnakeDirection;
import snake.SnakeMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnMoveEvent implements Listener {
    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event) {
        if (!SnakeMap.getPlayerInformation(event.getPlayer()).isPlaying()) {
            return;
        }
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from == null || to == null) {
            return; // Ignore the event if the locations are null
        }

        double deltaX = to.getX() - from.getX();
        double deltaZ = to.getZ() - from.getZ();

        // Determine the primary movement direction
        SnakeDirection direction = SnakeMap.getPlayerInformation(player).getDirection();
        if (deltaX > 0 && Math.abs(deltaX) > Math.abs(deltaZ)) {
            // Player is moving towards the positive X-axis (to the east)
            direction = SnakeDirection.RIGHT;
        } else if (deltaX < 0 && Math.abs(deltaX) > Math.abs(deltaZ)) {
            // Player is moving towards the negative X-axis (to the west)
            direction = SnakeDirection.LEFT;
        } else if (deltaZ > 0 && Math.abs(deltaZ) > Math.abs(deltaX)) {
            // Player is moving towards the positive Z-axis (to the south)
            direction = SnakeDirection.DOWN;
        } else if (deltaZ < 0 && Math.abs(deltaZ) > Math.abs(deltaX)) {
            // Player is moving towards the negative Z-axis (to the north)
            direction = SnakeDirection.UP;
        }

        SnakeMap.getPlayerInformation(player).setDirection(direction);

    }
}

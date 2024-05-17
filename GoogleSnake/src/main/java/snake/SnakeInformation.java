package snake;

import org.bukkit.map.MapCanvas;

import java.util.ArrayList;
import java.util.List;

public class SnakeInformation {
    private boolean appleOnScreen;
    private boolean isPlaying;
    private boolean backgroundLoaded;
    private List<SnakeLocation> snakeLocations = new ArrayList<>();
    private SnakeLocation appleLocation;
    private SnakeDirection direction = SnakeDirection.RIGHT;
    private int length = 1;
    private MapCanvas mapCanvas;
    private boolean won;

    public SnakeInformation() {}

    public SnakeInformation(boolean appleOnScreen, boolean isPlaying, boolean backgroundLoaded, List<SnakeLocation> snakeLocations, SnakeLocation appleLocation, int length, SnakeDirection direction) {
        this.appleOnScreen = appleOnScreen;
        this.isPlaying = isPlaying;
        this.backgroundLoaded = backgroundLoaded;
        this.snakeLocations = snakeLocations;
        this.appleLocation = appleLocation;
        this.length = length;
        this.direction = direction;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public boolean isAppleOnScreen() {
        return appleOnScreen;
    }

    public void setAppleOnScreen(boolean appleOnScreen) {
        this.appleOnScreen = appleOnScreen;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isBackgroundLoaded() {
        return backgroundLoaded;
    }

    public void setBackgroundLoaded(boolean backgroundLoaded) {
        this.backgroundLoaded = backgroundLoaded;
    }

    public List<SnakeLocation> getSnakeLocations() {
        return snakeLocations;
    }

    public void setSnakeLocations(List<SnakeLocation> snakeLocations) {
        this.snakeLocations = snakeLocations;
    }

    public SnakeLocation getAppleLocation() {
        return appleLocation;
    }

    public void setAppleLocation(SnakeLocation appleLocation) {
        this.appleLocation = appleLocation;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public MapCanvas getMapCanvas() {
        return mapCanvas;
    }

    public void setMapCanvas(MapCanvas mapCanvas) {
        this.mapCanvas = mapCanvas;
    }

    public void setWon(boolean won) {
        this.won = true;
    }

    public boolean getWon() {
        return won;
    }
}

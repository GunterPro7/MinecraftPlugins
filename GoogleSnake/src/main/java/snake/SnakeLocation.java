package snake;

import org.bukkit.Bukkit;

import java.util.Objects;

public class SnakeLocation {
    private byte x;
    private byte z;

    public SnakeLocation(byte x, byte z) {
        this.x = x;
        this.z = z;
    }

    public byte getX() {
        return x;
    }

    public byte getZ() {
        return z;
    }

    public void setX(byte x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnakeLocation that = (SnakeLocation) o;
        return x == that.x && z == that.z;
    }

    public boolean equals(byte x, byte z) {
        return this.x == x && this.z == z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }

    public void setZ(byte z) {
        this.z = z;
    }
}

package costumItems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.HashMap;

public abstract class CustomItemData extends MaterialData {

    // Grappling Hook
    public static HashMap<Player, Long> lastGrapplingHook = new HashMap<Player, Long>();
    public static double grapplingHookCD = 2;
    public static double grapplingHookRange = 10;

    // Infinity Boom
    public static HashMap<Player, Long> lastBoom = new HashMap<Player, Long>();
    public static double boomCD = 10;
    public static double boomPower = 4f;

    // Winter Blast
    public static double winterBlastPower = 7.5f;

    // Hyperion
    public static HashMap<Player, Long> lastHypeBoom = new HashMap<Player, Long>();
    public static double hypeSpeed = 0.1;

    // Infinity Pearls
    public static HashMap<Player, Long> lastPearlHook = new HashMap<Player, Long>();
    public static double pearlCD = 10;

    // Lightning Bolt
    public static HashMap<Player, Long> lastBolt = new HashMap<Player, Long>();
    public static double boltCD = 3;

    // Terminator
    public static HashMap<Player, Long> lastTerminatorArrow = new HashMap<Player, Long>();
    public static double terminatorSpeed = 0.25;

    // Super Leaps
    public static HashMap<Player, Long> lastLeap = new HashMap<Player, Long>();
    public static double leapCD = 20;

    // Ice Wand
    public static HashMap<Player, Long> lastIceWandFreeze = new HashMap<Player, Long>();
    public static HashMap<Player, Long> lastIceWand = new HashMap<Player, Long>();
    public static double iceWandCD = 10;
    public static double iceWandDuration = 3;

    // Infinity Pickaxe
    public static int infinityPickaxeRange = 128;

    // Flame Thrower
    public static HashMap<Player, Long> lastFlameThrower = new HashMap<Player, Long>();
    public static double flameThrowerCD = 1.5;

    // Infinity Wand
    public static HashMap<Player, Long> lastInfinityWand = new HashMap<Player, Long>();
    public static double infinityWandCD = 0.15;
    public static double infinityWandRange = 128;

    // Teddy Bear
    public static HashMap<Player, Long> lastBasicTeddy = new HashMap<Player, Long>();

    // Infinity Sword
    public static int infinitySwordRange = 128;

    public CustomItemData(Material type) {
        super(type);
    }
}

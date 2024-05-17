package snake;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import renderer.AppleRenderer;
import renderer.MapBackgroundRenderer;

import java.util.HashMap;

public class SnakeMap {
    private static final HashMap<String, SnakeInformation> playerInformationDict = new HashMap<>();

    private final ItemStack map;

    public SnakeMap(Player player) {
        MapView mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));

        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);

        MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
        mapMeta.setCustomModelData(4040299);
        mapMeta.setMapView(mapView);
        mapItem.setItemMeta(mapMeta);
        addPlayerInformation(player);

        mapView.addRenderer(new AppleRenderer());
        mapView.addRenderer(new MapBackgroundRenderer());
        map = mapItem;
    }

    public static SnakeInformation getPlayerInformation(Player player) {
        return playerInformationDict.get(player.getName());
    }

    public static void addPlayerInformation(Player player) {
        playerInformationDict.put(player.getName(), new SnakeInformation());
    }

    public ItemStack getMap() {
        return map;
    }

    public static boolean isItem(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 4040299;
    }
}

package listeners;

import main.Backpacks;
import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.bukkit.Bukkit.getLogger;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String uuid;
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        File file = new File(Main.getPlugin().getDataFolder() + "/backpacks/" + player.getName() + ".json");

        if (!file.exists()) {
            try {
                FileWriter writer = new FileWriter(Main.getPlugin().getDataFolder()+"/backpacks/" + player.getName() + ".json");
                writer.write(player.getName() + "\n");
                writer.write("CraftWorld{name=world}" + "\n");
                writer.write(Utils.repeat(",", Main.continueResizeNewsize) + "\n"); // TODO Dazuschreiben, abnutzung, enchants, stackssize
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

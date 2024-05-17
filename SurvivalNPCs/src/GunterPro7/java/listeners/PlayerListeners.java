package listeners;

import main.Main;
import main.NPC;
import main.NPCInventorys;
import main.PlayerInformation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Main.PLAYERS.put(event.getPlayer(), new PlayerInformation());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Main.PLAYERS.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerInformation pi = Main.PLAYERS.get(player);
        String message = event.getMessage();

        if (pi.isListening() || message.equals("exit")) {
            player.sendMessage(ChatColor.RED + "Your request has expired or you stopped your session!");
            pi.setListening(-1L);
            event.setCancelled(true);
        } else {
            NPC npc = pi.getLastNPC();
            if (npc.getInteractionMessages().contains(message)) {
                player.sendMessage(ChatColor.RED + "You can't say the same message twice!");
                event.setCancelled(true);
                pi.setListening(System.currentTimeMillis());
            } else {
                npc.addInteractionLine(message);
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> player.openInventory(NPCInventorys.getInteractionInventory(npc.getInteractionMessages(), npc.getInteractionPage())), 1);
                event.setCancelled(true);
            }
        }
    }
}

package listeners;

import costumItems.CodingBook;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.meta.BookMeta;
import utils.CodingBookColors;
import utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class OnEditBook implements Listener {
    @EventHandler
    public void onBookEdit(PlayerEditBookEvent event) {
        if (CodingBook.isItemToConvert(event.getPlayer().getInventory().getItemInMainHand())) {
            if (event.isSigning()) {
                ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
                BookMeta newBookMeta = event.getNewBookMeta();
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.MAGIC + "abc" + ChatColor.RESET + ChatColor.BOLD + " Coding Book " + ChatColor.RESET + ChatColor.ITALIC + ChatColor.MAGIC + "cba");
                newBookMeta.setLore(lore);
                newBookMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + newBookMeta.getTitle());
                if (Config.CONVERT_BOOK_WITH_COLORS) {
                    item.setItemMeta(CodingBookColors.changeCodingColors(newBookMeta));
                } else {
                    item.setItemMeta(newBookMeta);
                }
                Player player = event.getPlayer();
                player.getInventory().addItem(item);
                player.sendMessage(ChatColor.GREEN + Config.CONVERTED_CODINGBOOK);

            }
            event.setCancelled(true);
        }



    }
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        TextComponent message = new TextComponent("Das ist ein Tooltip");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Das ist der Text, der angezeigt wird").create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/say Hallo"));

        TextComponent prefix = new TextComponent("[");
        prefix.setColor(ChatColor.GRAY.asBungee());

        TextComponent suffix = new TextComponent("]");
        suffix.setColor(ChatColor.GRAY.asBungee());

        prefix.addExtra(message);
        prefix.addExtra(suffix);

        event.getPlayer().spigot().sendMessage(prefix);
    }
}

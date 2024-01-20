package utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class Utils {
    public void printItem(String itemToPrint) {
        getLogger().info(itemToPrint);
    }

    public static int checkCoordForTild(String coord, Player player, char direction) {
        if (coord.equals("~")) {
            switch (direction) {
                case 'x':
                    return (int) Math.round(player.getLocation().getX());
                case 'y':
                    return (int) Math.round(player.getLocation().getY());
                case 'z':
                    return (int) Math.round(player.getLocation().getZ());
                default:
                    return 0;
            }
        } else {
            return Integer.parseInt(coord);
        }
    }

    public static TextComponent combineWithTooltip(String prefix, String suffix, String text, String tooltipText, String command, ClickEvent.Action action) {
        TextComponent message = new TextComponent(text);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(tooltipText).create()));
        message.setClickEvent(new ClickEvent(action, command));

        TextComponent returnText = new TextComponent(prefix);

        returnText.addExtra(message);
        returnText.addExtra(suffix);

        return returnText;
    }
}

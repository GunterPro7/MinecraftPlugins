package commands;

import main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.Worlds;

import java.util.Objects;

public class GunterModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && Objects.equals(args[0], "skyblock")) {
            ((Player) sender).teleport(Worlds.WORLD_SKYBLOCK.getWorld().getSpawnLocation());
            return false;
        }
        ((Player) sender).openInventory(Main.INVENTORY);
        return true;
    }
}
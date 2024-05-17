package utils;

import items.EternitysArmor;
import items.EternitysEdge;
import items.EternitysPickaxe;
import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class CustomItems {
    public static final NamespacedKey KEY = new NamespacedKey(Main.getPlugin(), "SpecialItemKey");
    public static final Integer UNIQUE_KEY_NUMBER = 20182105;

    public static boolean isSpecialItem(ItemStack item) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }

        return Objects.equals(item.getItemMeta().getPersistentDataContainer().get(KEY, PersistentDataType.INTEGER), UNIQUE_KEY_NUMBER);
    }

    public static boolean customItemCheck(ItemStack item, int uniqueNumber) {
        return isSpecialItem(item) && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == uniqueNumber;
    }

    public static ItemStack upgrade(ItemStack item) {
        if (item != null) {
            Bukkit.getLogger().info("stage1");
            if (item.getItemMeta() != null && item.getItemMeta().hasCustomModelData()) {
                Bukkit.getLogger().info("stage2");
                Bukkit.getLogger().info(String.valueOf(item.getItemMeta().getCustomModelData()));
                if (item.getItemMeta().getCustomModelData() == EternitysEdge.UNIQUE_NUMBER) {
                    Bukkit.getLogger().info("stage3");
                    return EternitysEdge.upgrade(item);
                } else if (item.getItemMeta().getCustomModelData() == EternitysPickaxe.UNIQUE_NUMBER) {
                    Bukkit.getLogger().info("stage3");
                    return EternitysPickaxe.upgrade(item);
                } else if (item.getItemMeta().getCustomModelData() == EternitysArmor.UNIQUE_NUMBER) {
                    Bukkit.getLogger().info("starge3");
                    return EternitysArmor.upgrade(item);
                }
            }
        }
        return null;
    }
}

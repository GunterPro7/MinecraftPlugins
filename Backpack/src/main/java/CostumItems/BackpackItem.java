package CostumItems;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BackpackItem extends ItemStack {
    public static ItemStack createNewBackpack() {
        String uuid = String.valueOf(UUID.randomUUID());
        return createNewBackpack(uuid);
    }

    public static ItemStack createNewBackpack(String uuid) {
        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM1MWU1MDU5ODk4MzhlMjcyODdlN2FmYmM3Zjk3ZTc5NmNhYjVmMzU5OGE3NjE2MGMxMzFjOTQwZDBjNSJ9fX0=";
        Main.setCurrentID(uuid);
        ItemStack customItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) customItem.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));
        Field profileField;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        customItem.setItemMeta(skullMeta);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + Main.title);
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "item_id"), PersistentDataType.STRING, uuid);

        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + Main.lore);
        meta.setLore(lore);
        customItem.setItemMeta(meta);

        return customItem;
    }
}

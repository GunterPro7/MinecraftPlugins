package costumItems;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlameThrower extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.PLAYER_HEAD);

    public FlameThrower() {
        super(Material.PLAYER_HEAD);
    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("FFF", "ADA", "FFF");
        recipe.setIngredient('F', Material.FLINT_AND_STEEL);
        recipe.setIngredient('D', Material.DIAMOND_SWORD);
        recipe.setIngredient('A', Material.DIAMOND_AXE);
        FlameThrower.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);

        // Optional: Setze den Namen des Items
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GREEN + "Flame Thrower");
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setCustomModelData(404051);

        // Setting the custom lore
        List<String> lore = new ArrayList<>();
        lore.add("§7This is fine!");
        itemMeta.setLore(lore);

        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM4NDQ3NDVkNGY0NjIyY2Y5MjcyZjhmOGMzZmE2M2JlMGM4OGRhM2UyYWI3ZDhkOWJiYjMyOTk0ZDI4NzM2OCJ9fX0=";
        SkullMeta skullMeta = (SkullMeta) itemMeta;
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
        item.setItemMeta(itemMeta);
    }

    public static boolean isFlameThrower(ItemStack itemStack) {
        if (itemStack != null && itemStack.getType() == Material.PLAYER_HEAD && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            return meta != null && meta.getCustomModelData() == 404051;
        }
        return false;
    }

    public static boolean isCooldownOver(Player player) {
        if (CustomItemData.lastFlameThrower.get(player) == null) {
            return true;
        }
        return System.currentTimeMillis() - CustomItemData.lastFlameThrower.get(player) > CustomItemData.flameThrowerCD * 1000;
    }
}

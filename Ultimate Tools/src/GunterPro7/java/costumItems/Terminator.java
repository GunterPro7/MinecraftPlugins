package costumItems;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Terminator extends CustomItemData {
    public static ShapedRecipe recipe;
    public static final ItemStack item = new ItemStack(Material.BOW);

    public Terminator() {
        super(Material.BOW);

    }

    public static void enableCraftingRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("ABA", "BCB", "ABA");
        recipe.setIngredient('A', Material.PRISMARINE);
        recipe.setIngredient('B', Material.SPONGE);
        recipe.setIngredient('C', Material.WATER_BUCKET);
        Terminator.recipe = recipe;
        Bukkit.addRecipe(recipe);
    }

    public static void createItem() {
        // Erstelle eine neue ItemMeta-Instanz
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_SWORD);
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add("§7JUJU But better!");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(404049);

        // Optional: Setze den Namen des Items
        itemMeta.setDisplayName(ChatColor.GREEN + "Terminator");
        //itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        // Gib die ItemMeta-Instanz zurück
        item.setItemMeta(itemMeta);
    }

    public static boolean isCooldownOver(Player player) {
        if (CustomItemData.lastTerminatorArrow.get(player) == null) {
            return true;
        }
        return System.currentTimeMillis() - CustomItemData.lastTerminatorArrow.get(player) > CustomItemData.terminatorSpeed * 1000;
    }

    public static boolean isTerminator(ItemStack item) {
        if (item != null && item.getType() == Material.BOW && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName() && meta.getCustomModelData() == 404049) {
                return true;
            }
        }
        return false;
    }

    public static void shootArrows(Player player) {
        if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW)) {
            return;
        }
        if (player.getGameMode() != GameMode.CREATIVE) {
            player.getInventory().removeItem(new ItemStack(Material.ARROW));
        }

        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        // Pfeil nach links oben
        Vector offset1 = new Vector(-0.1, 0, 0);
        Vector velocity1 = direction.clone().add(offset1).multiply(2.5);
        shootArrow(player, eyeLocation, velocity1);

        // Gerader Pfeil
        Vector offset2 = new Vector(0, 0, 0);
        Vector velocity2 = direction.clone().add(offset2).multiply(2.5);
        shootArrow(player, eyeLocation, velocity2);

        // Pfeil nach rechts oben
        Vector offset3 = new Vector(0.1, 0, 0);
        Vector velocity3 = direction.clone().add(offset3).multiply(2.5);
        shootArrow(player, eyeLocation, velocity3);
    }

    public static void shootArrow(Player player, Location location, Vector velocity) {
        Arrow arrow = player.launchProjectile(Arrow.class, velocity);
        arrow.setShooter(player);
        arrow.setDamage(15);
        arrow.setFireTicks(80);
    }
}

package items;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import utils.CustomItems;

import java.util.ArrayList;
import java.util.List;

public class IllusionerWand extends ItemStack {
    public static final int UNIQUE_NUMBER = 404205;

    public IllusionerWand() {
        super(Material.GOLDEN_SWORD);
        ItemMeta itemMeta = getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomItems.KEY, PersistentDataType.INTEGER, CustomItems.UNIQUE_KEY_NUMBER);

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.MAGIC + "abc" + ChatColor.AQUA + " Illusioner Wand " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "cba");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Ability: Ultimate Damage");
        lore.add(ChatColor.YELLOW + "Right click to make an Ultimate Flächen Damage");
        lore.add(ChatColor.RED + "1 second cooldown!");
        lore.add("");
        lore.add(ChatColor.GOLD.toString() + ChatColor.BOLD + "LEGENDARY ITEM");
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(UNIQUE_NUMBER);

        setItemMeta(itemMeta);
    }

    public static boolean isItem(ItemStack item) {
        return item != null && item.getType() == Material.GOLDEN_SWORD && item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == UNIQUE_NUMBER;
    }

    public static void damageNearbyEntitys(Player player) {
        Location playerLoc = player.getLocation();
        World world = playerLoc.getWorld();
        double x = playerLoc.getX();// -5  -4  -3  -2  -1   0   1   2   3   4   5
        double y = playerLoc.getY();
        double z = playerLoc.getZ();
        if (world != null) {
            for (int i = -10; i < 11; i++) {
                for (int j = -10; j < 11; j++) {
                    world.spawnParticle(Particle.TOTEM, new Location(world, x + j, y, z + i), (10 - Math.abs(j)) + (10 - Math.abs(i)));
                }
            }
        }
        for (Entity entity : world.getNearbyEntities(playerLoc, 15, 15, 15)) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (livingEntity instanceof Player) {
                    if (livingEntity.getName().equals(player.getName())) {
                        continue;
                    }
                }
                double damage = (15 - playerLoc.distance(entity.getLocation())) * 2;
                if (damage > 0) {
                    livingEntity.damage(damage);
                }
            }
        }
    }
}

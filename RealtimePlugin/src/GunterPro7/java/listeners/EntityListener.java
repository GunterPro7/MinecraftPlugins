package listeners;

import fragments.IllusionerFragment;
import fragments.NautilusFragment;
import fragments.RubyFragment;
import fragments.WitherEssence;
import items.*;
import main.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import utils.Config;

import java.util.ArrayList;
import java.util.List;

public class EntityListener implements Listener {
    public static long lastZombies = System.currentTimeMillis();

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        Location location = event.getLocation();
        World world = location.getWorld();

        if (entity instanceof Zombie) {
            if (System.currentTimeMillis() - lastZombies > 100_000) {
                if (Main.RANDOM.nextInt(100) == 1) {
                    for (int i = 0; i < Main.RANDOM.nextInt(32); i++) {
                        world.spawnEntity(location, entity.getType());
                    }
                }
                lastZombies = System.currentTimeMillis();
            }
        } else if (entity instanceof Creeper) {
            if (Main.RANDOM.nextInt(10) == 1) {
                ((Creeper) entity).setPowered(true);
            }
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (Config.isEnabled) {
            EntityType entityType = event.getEntityType();
            Location location = event.getEntity().getLocation();
            World world = location.getWorld();
            if (world != null) {
                if (entityType == EntityType.WITHER) {
                    if (Main.RANDOM.nextInt(5) == 1) {
                        world.dropItem(location, new WitherEssence());
                    }
                } else if (entityType == EntityType.DROWNED) {
                    if (event.getEntity().getEquipment().getItemInMainHand().getType() == Material.TRIDENT) {
                        if (Main.RANDOM.nextInt(3) == 1) {
                            world.dropItem(location, new NautilusFragment());
                        }
                    }
                } else if (entityType == EntityType.ILLUSIONER) {
                    if (Main.RANDOM.nextInt(5) == 1) {
                        world.dropItem(location, new RubyFragment());
                    } if (Main.RANDOM.nextInt(500) == 1) {
                        world.dropItem(location, new IllusionerFragment());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack item = null;
            PlayerInventory playerInventory = player.getInventory();
            if (playerInventory.getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING) {
                item = playerInventory.getItemInMainHand();
            } else if (player.getInventory().getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING) {
                item = playerInventory.getItemInOffHand();
            }

            if (UltimateTotem.isItem(item)) {
                ItemMeta itemMeta = item.getItemMeta();
                List<String> lore = itemMeta.getLore();
                String usesLeftString = lore.get(lore.size() - 1);
                int usesLeft = Integer.parseInt(ChatColor.stripColor(usesLeftString).split(" Uses Left!")[0]);
                if (--usesLeft != 0) {
                    lore.set(lore.size() - 1, ChatColor.RED.toString() + usesLeft + " Uses Left!");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);

                    player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();

            // Adjust prices of the villager's trades
            List<MerchantRecipe> recipes = new ArrayList<>(villager.getRecipes());
            for (MerchantRecipe recipe : recipes) {
                List<ItemStack> ingredients = recipe.getIngredients();
                for (ItemStack ingredient : ingredients) {
                    Bukkit.getLogger().info(ingredient.toString());
                    ingredient.setAmount(ingredient.getAmount());

                }
                recipe.setIngredients(ingredients);
            }
            villager.setRecipes(recipes);
        }
    }
}

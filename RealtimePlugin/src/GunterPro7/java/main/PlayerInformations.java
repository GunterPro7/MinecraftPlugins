package main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import utils.Utils;

import java.util.HashMap;

public class PlayerInformations {
    private final Player player;
    private final HashMap<CooldownType, Long> map = new HashMap<>();
    private final HashMap<PlayerActivites, Boolean> activitesMap = new HashMap<>();
    private int exp;
    private double stamina = 0.5d;
    private final BossBar staminaBar = Bukkit.createBossBar("Stamina", BarColor.RED, BarStyle.SOLID);
    private Location lastLocation;
    private boolean staminaRegenRunning;
    private boolean sitting;


    public PlayerInformations(Player player) {
        exp = 0; // TODO Read from file TODO same as stamina
        this.player = player;
        fillStamina(); // old method
        setStamina(0.5d);
        staminaBar.addPlayer(player);

        Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
            if (stamina == 0d) {
                player.damage(0.5);
            }
            if (stamina < 0.05d) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35, 3));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
            } else if (stamina < 0.10d) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35, 1));
            } else if (stamina < 0.25d) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35, 0));
            }
        }, 1L, 20L);
    }

    public void setCooldown(CooldownType cooldownType) {
        if (isCooldownOver(cooldownType)) {
            map.put(cooldownType, System.currentTimeMillis());
        }
    }

    public boolean isCooldownOver(CooldownType cooldownType) {
        return !map.containsKey(cooldownType) /* Existiert nicht, noch kein CD gesetzt */ || (System.currentTimeMillis() - map.get(cooldownType)) > (cooldownType.getDuration() * 1000L);
    }

    public void activate(PlayerActivites playerActivite) {
        activitesMap.put(playerActivite, true);
    }

    public boolean isActive(PlayerActivites playerActivite) {
        return !activitesMap.containsKey(playerActivite) /* Existiert nicht, noch kein CD gesetzt */ || activitesMap.get(playerActivite);
    }

    public void deactive(PlayerActivites playerActivites) {
        activitesMap.put(playerActivites, false);
    }

    public int getExp() {
        return exp;
    }

    public int getExpInLevels() {
        return Utils.getLevel(exp);
    }

    public void addExp(int amount) {
        exp += amount;
        Utils.savePlayersToFile();
    }

    public void setExp(int amount) {
        exp = amount;
    }

    public double getStamina() {
        return stamina;
    }

    public void setStamina(double stamina) {
        if (player.getGameMode() == GameMode.SURVIVAL) {
            if (stamina > 1d) {
                stamina = 1d;
            } else if (stamina < 0d) {
                stamina = 0d;
            }
            this.stamina = stamina;

            staminaBar.setProgress(stamina);
            if (stamina < 0.25d) {
                staminaBar.setColor(BarColor.RED);
                staminaBar.setStyle(BarStyle.SEGMENTED_20);
            } else if (stamina < 0.5d) {
                staminaBar.setColor(BarColor.YELLOW);
                staminaBar.setStyle(BarStyle.SEGMENTED_12);
            } else {
                staminaBar.setColor(BarColor.GREEN);
                staminaBar.setStyle(BarStyle.SEGMENTED_6);
            }
        }
    }

    public void doStaminaInterval(boolean doSleepingWay) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            if ((doSleepingWay && player.isSleeping()) || (!doSleepingWay && isSitting())) {
                double newStamina;
                if (doSleepingWay) {
                    newStamina = stamina + 0.01d;
                } else {
                    newStamina = stamina + 0.0075d;
                }

                setStamina(newStamina);
                doStaminaInterval(doSleepingWay);
            }
        }, 10);
    }

    public void disableBossbar() {
        staminaBar.removePlayer(player);
    }

    public void fillStamina() {
        lastLocation = player.getLocation();
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            Location tmpLocation = lastLocation.clone();
            if (lastLocation.equals(player.getLocation())) {
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    if (tmpLocation.equals(player.getLocation())) {
                        if (!staminaRegenRunning) {
                            staminaRegenRunning = true;
                            givePointsUntilMove(tmpLocation);
                        }
                    }
                }, 300);
            }
            if (player.isOnline()) {
                fillStamina();
            }
        }, 20);
    }

    public void givePointsUntilMove(Location location) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            if (player.getLocation().equals(location)) {
                setStamina(stamina + 0.0035d);
                givePointsUntilMove(location);
            } else {
                staminaRegenRunning = false;
            }
        }, 10);
    }

    public boolean isStaminaCritical() {
        return stamina < 0.05d;
    }

    public boolean isSitting() {
        return sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }

    public void doSitInterval(Location location, Player player) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            if (Utils.sameLocation(location, player.getLocation(), 0.5)) {
                doSitInterval(location, player);
            } else {
                Utils.removePigForChairs(location);
                setSitting(false);
            }
        }, 5);
    }
}

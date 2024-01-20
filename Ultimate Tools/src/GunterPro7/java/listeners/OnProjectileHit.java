package listeners;

import costumItems.CustomItemData;
import costumItems.WinterBlast;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import utils.Utils;

public class OnProjectileHit implements Listener {
    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball && WinterBlast.isWinterBlast(((Snowball) event.getEntity()).getItem()) && event.getEntity().getShooter() instanceof Player) {
            if (!((Player) event.getEntity().getShooter()).hasPermission("ultimatetools.use.winter_blast")) {
                Utils.sendNoPermissionMessage(((Player) event.getEntity().getShooter()).getPlayer());
                return;
            }
            Snowball snowball = (Snowball) event.getEntity();
            Location location = snowball.getLocation();
            World world = location.getWorld();

            // Erstelle eine große Explosion an der Position, an der der Schneeball aufgetroffen ist
            if (world != null) {
                world.createExplosion(location, (float) CustomItemData.winterBlastPower, true, true, (Entity) snowball.getShooter());
            }

            // Entferne den Schneeball
            snowball.remove();
        }
    }
}

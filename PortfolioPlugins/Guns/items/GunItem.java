package net.moruto.guns.items;

import com.destroystokyo.paper.ParticleBuilder;
import net.moruto.guns.Guns;
import net.moruto.guns.utils.Helper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GunItem {
    private ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);

    private final float damage;
    private boolean isReloading;
    private final Player player;
    private final String name;

    public GunItem(String name, float damage, Player player) {
        this.name = name;
        this.isReloading = false;
        this.damage = damage;
        this.player = player;

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Helper.trans("&fGun: " + name));
        item.setItemMeta(meta);
    }

    public boolean isReloading() {
        return isReloading;
    }
    public void setGunItem(ItemStack item) {
        this.item = item;
    }
    public ItemStack getGun() {
        return item;
    }

    public void shoot() {
        new BukkitRunnable() {
            final Vector direction = player.getLocation().getDirection().normalize();
            final Location location = player.getLocation();
            double t = 0;
            @Override
            public void run() {
                t += 0.5;
                double x = direction.getX() * t;
                double y = direction.getY() * t + 1.5;
                double z = direction.getZ() * t;
                location.add(x ,y, z);
                player.getWorld().spawnParticle(Particle.DRIP_LAVA, location, 10);

                for (Entity entity : location.getChunk().getEntities()) {
                    if (entity.getLocation().distance(location) < 1.0) {
                        if (entity != player) {
                            entity.setFireTicks((int) (20 * damage));
                        }
                    }
                }

                location.subtract(x, y, z);
                if (t > 40) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Guns.getInstance(), 0L, 1L);
    }
}

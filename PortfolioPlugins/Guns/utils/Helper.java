package net.moruto.guns.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class Helper {
    public static String trans(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    //i don't own this code below
    public static <T extends Entity> T getWhatIsPlayerLookingAt(final Entity entity, final Iterable<T> entities) {
        if (entity == null)
            return null;
        T target = null;
        final double threshold = 1;
        for (final T other : entities) {
            final Vector n = other.getLocation().toVector()
                    .subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n)
                    .lengthSquared() < threshold
                    && n.normalize().dot(entity.getLocation().getDirection().normalize()) >= 0) {
                if (target == null
                        || target.getLocation().distanceSquared(entity.getLocation()) > other.getLocation()
                        .distanceSquared(entity.getLocation()))
                    target = other;
                }
            }
        return target;
    }
}

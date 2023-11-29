package net.moruto.guns.listeners;

import net.moruto.guns.items.GunItem;
import net.moruto.guns.utils.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GunListener implements Listener {
    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) return;

        if (item.getItemMeta().getDisplayName().toLowerCase().contains(Helper.trans("gun: "))) {
            event.setCancelled(true);

            GunItem gun = new GunItem("gun: " + item.getItemMeta().getDisplayName().toLowerCase().split("gun: ")[1], 5, player);
            gun.setGunItem(item);
            player.getInventory().getItemInMainHand();
            Action action = event.getAction();

            //shoot
            if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                gun.shoot();
            }
        }
    }
}

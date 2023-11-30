package net.moruto.economy.listeners;

import net.moruto.economy.MorutosEconomy;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.List;

public class EconomyListener implements Listener {
    public List<String> method = List.of(MorutosEconomy.getInstance().getConfigManager().getMethod());
    public int amount = MorutosEconomy.getInstance().getConfigManager().getAmount();


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (method.contains("blockbreaking")) {
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                MorutosEconomy.getInstance().economyImplementer.depositPlayer(player, amount);
            }
        }
    }

    @EventHandler
    private void onKill(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && !(event.getEntity() instanceof Player)) {
            if (method.contains("mobkilling")) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                if (entity.getHealth() == 0) {
                    MorutosEconomy.getInstance().economyImplementer.depositPlayer((Player) event.getDamager(), amount);
                }
            }
        }
    }
    
    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        if (method.contains("moving")) {
            MorutosEconomy.getInstance().economyImplementer.depositPlayer(event.getPlayer(), amount);
        }
    }
}

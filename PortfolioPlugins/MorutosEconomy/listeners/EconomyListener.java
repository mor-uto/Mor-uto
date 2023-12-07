package net.moruto.economy.listeners;

import net.moruto.economy.MorutosEconomy;
import net.moruto.economy.utils.ConfigManager;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class EconomyListener implements Listener {
    public List<String> method = MorutosEconomy.getInstance().getConfigManager().getMethod();
    private final ConfigManager config = MorutosEconomy.getInstance().getConfigManager();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (workChecksNo(player)) {
            return;
        }

        if (method.contains("blockbreaking")) {
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                MorutosEconomy.getInstance().economyImplementer.depositPlayer(player, config.getPerBlockAmount());
            }
        }
    }

    @EventHandler
    private void onKill(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && !(event.getEntity() instanceof Player)) {
            if (workChecksNo((Player) event.getDamager())) {
                return;
            }

            if (method.contains("mobkilling")) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                if (entity.getHealth() == 0) {
                    MorutosEconomy.getInstance().economyImplementer.depositPlayer((Player) event.getDamager(), config.getPerMobkillAmount());
                }
            }
        }
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        if (workChecksNo(event.getPlayer())) {
            return;
        }

        if (method.contains("moving")) {
            MorutosEconomy.getInstance().economyImplementer.depositPlayer(event.getPlayer(), config.getPerBlockWalkAmount());
        }
    }

    private boolean workChecksNo(Player player) {
        return !config.getWorkingWorlds().isEmpty() && !config.getWorkingWorlds().contains(player.getWorld().getName().toLowerCase());
    }
}

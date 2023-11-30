package net.moruto.economy.utils;

import net.milkbowl.vault.economy.Economy;
import net.moruto.economy.MorutosEconomy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {
    private Economy provider;

    public void hook() {
        provider = MorutosEconomy.getInstance().economyImplementer;
        Bukkit.getServicesManager().register(Economy.class, this.provider, MorutosEconomy.getInstance(), ServicePriority.Normal);
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
    }
}

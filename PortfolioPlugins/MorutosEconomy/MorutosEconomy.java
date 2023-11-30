package net.moruto.economy;

import net.moruto.economy.command.EconomyCommand;
import net.moruto.economy.listeners.EconomyListener;
import net.moruto.economy.utils.ConfigManager;
import net.moruto.economy.utils.StorageManager;
import net.moruto.economy.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class MorutosEconomy extends JavaPlugin {
    private EconomyListener economy;
    private ConfigManager configManager;
    public HashMap<UUID, Double> playerBank = new HashMap<>();
    public EconomyImplementer economyImplementer;
    private StorageManager storageManager;
    private final VaultHook vaultHook = new VaultHook();

    public static MorutosEconomy getInstance() {
        return JavaPlugin.getPlugin(MorutosEconomy.class);
    }

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage("Vault plugin is not installed... its a required dependency");
            return;
        }

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        economyImplementer = new EconomyImplementer();

        storageManager = new StorageManager(economyImplementer);

        configManager = new ConfigManager();
        economy = new EconomyListener();

        this.vaultHook.hook();

        getServer().getPluginManager().registerEvents(economy, this);
        EconomyCommand economyCommand = new EconomyCommand();
        getCommand("economy").setExecutor(economyCommand);
        getCommand("balance").setExecutor(economyCommand);
    }

    @Override
    public void onDisable() {
        this.vaultHook.unhook();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}

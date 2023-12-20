package net.moruto.economy;

import net.moruto.economy.command.EconomyCommand;
import net.moruto.economy.listeners.EconomyListener;
import net.moruto.economy.database.MySQL;
import net.moruto.economy.utils.ConfigManager;
import net.moruto.economy.database.LocalDatabase;
import net.moruto.economy.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class MorutosEconomy extends JavaPlugin {
    private ConfigManager configManager;
    private MySQL mySQL;
    public HashMap<UUID, Double> playerBank = new HashMap<>();
    public EconomySystem economyImplementer;
    private final VaultHook vaultHook = new VaultHook();

    public static MorutosEconomy getInstance() {
        return JavaPlugin.getPlugin(MorutosEconomy.class);
    }

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Vault plugin is not installed; it's a required dependency.");
            return;
        }

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        economyImplementer = new EconomySystem();

        configManager = new ConfigManager();
        EconomyListener economy = new EconomyListener();

        if (configManager.getDbMethod().equalsIgnoreCase("mysql")) {
            mySQL = new MySQL();
            mySQL.connect();
            if (mySQL.isConnected()) {
                playerBank = mySQL.loadPlayerData();
            }
        } else {
            new LocalDatabase(economyImplementer);
        }

        this.vaultHook.hook();

        getServer().getPluginManager().registerEvents(economy, this);

        EconomyCommand economyCommand = new EconomyCommand();
        getCommand("economy").setExecutor(economyCommand);
        getCommand("balance").setExecutor(economyCommand);
    }

    @Override
    public void onDisable() {
        this.vaultHook.unhook();
        if (mySQL != null && mySQL.isConnected()){
            mySQL.savePlayerData(playerBank);
            mySQL.disconnect();
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}

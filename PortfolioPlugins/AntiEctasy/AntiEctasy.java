package net.moruto.antiectasy;

import net.moruto.antiectasy.command.CommandsManager;
import net.moruto.antiectasy.tools.Disinfector;
import net.moruto.antiectasy.utils.ConfigManager;
import net.moruto.antiectasy.tools.JarFileChecker;
import net.moruto.antiectasy.utils.Helper;
import net.moruto.antiectasy.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiEctasy extends JavaPlugin {
    private JarFileChecker jarFileChecker;
    private Disinfector disinfector;
    private ConfigManager configManager;

    public static AntiEctasy getInstance() {
        return JavaPlugin.getPlugin(AntiEctasy.class);
    }

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        sendStartup();

        configManager = new ConfigManager();
        jarFileChecker = new JarFileChecker();
        disinfector = new Disinfector();

        new CommandsManager();

        new Metrics(this, 20521);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    private void sendStartup() {
        Bukkit.getServer().getConsoleSender().sendMessage(Helper.trans("&b-&3-&9-".repeat(15)));
        Bukkit.getServer().getConsoleSender().sendMessage(Helper.trans("&bMorutos&9Productions"));
        Bukkit.getServer().getConsoleSender().sendMessage(Helper.trans("&9Discord: moruto_"));
        Bukkit.getServer().getConsoleSender().sendMessage(Helper.trans("Version: " + getDescription().getVersion()));
        Bukkit.getServer().getConsoleSender().sendMessage(Helper.trans("&b-&3-&9-".repeat(15)));
    }

    public JarFileChecker getJarFileChecker() {
        return jarFileChecker;
    }

    public Disinfector getDisinfector() {
        return disinfector;
    }
}

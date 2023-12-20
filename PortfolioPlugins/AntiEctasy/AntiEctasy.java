package net.moruto.antiectasy;

import net.moruto.antiectasy.tools.Disinfector;
import net.moruto.antiectasy.utils.ConfigManager;
import net.moruto.antiectasy.tools.JarFileChecker;
import net.moruto.antiectasy.utils.Helper;
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

        configManager = new ConfigManager();
        jarFileChecker = new JarFileChecker();
        disinfector = new Disinfector();
        new MainCommand();
        sendStartup();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public String getPrefix() {
        return "&7[&bAnti&9Ectasy&7]&r";
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

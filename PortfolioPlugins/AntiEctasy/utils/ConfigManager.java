package net.moruto.antiectasy.utils;

import net.moruto.antiectasy.AntiEctasy;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final boolean disableInfectedPlugins;
    private final String disinfectPermission, supportPermission, scanPermission, defaultPrefix, customPrefix;

    public ConfigManager() {
        FileConfiguration config = AntiEctasy.getInstance().getConfig();

        disableInfectedPlugins = config.getBoolean("disableInfectedPlugins");

        scanPermission = config.getString("scanCommandPermission");
        supportPermission = config.getString("supportCommandPermission");
        disinfectPermission = config.getString("disinfectCommandPermission");

        defaultPrefix = "&7[&bAnti&9Ectasy&7]&r";
        customPrefix = config.getString("customPrefix");
    }

    public boolean isDisableInfectedPlugins() {
        return disableInfectedPlugins;
    }

    public String getScanPermission() {
        return scanPermission;
    }

    public String getSupportPermission() {
        return supportPermission;
    }

    public String getDisinfectPermission() {
        return disinfectPermission;
    }

    public String getPrefix() {
        if (!customPrefix.equalsIgnoreCase("")) {
            return customPrefix;
        } else {
            return "&7[&bAnti&9Ectasy&7]&r";
        }
    }
}

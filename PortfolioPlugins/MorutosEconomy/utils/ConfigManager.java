package net.moruto.economy.utils;

import net.moruto.economy.MorutosEconomy;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private final FileConfiguration config = MorutosEconomy.getInstance().getConfig();
    
    private final List<String> method;
    private final List<String> workingWorlds;
    private final double perBlockAmount, perBlockWalkAmount, perMobkillAmount;
    private final String prefix, dbMethod;
    private final boolean usePrefix;

    public ConfigManager() {
        String methodsBase = config.getString("moneyMethod");
        String workingWorldsBase = config.getString("workingWorlds");

        method = new ArrayList<>();
        workingWorlds = new ArrayList<>();

        method.addAll(List.of(methodsBase.replaceAll(" ", "").split(",")));
        workingWorlds.addAll(List.of(workingWorldsBase.replaceAll(" ", "").split(",")));

        prefix = config.getString("prefix");
        usePrefix = config.getBoolean("enablePrefix");
        perBlockAmount = config.getDouble("moneyPerKillOrBlockBreak");
        perBlockWalkAmount = config.getDouble("moneyPerBlockWalked");
        perMobkillAmount = config.getDouble("moneyPerMobKilled");
    
        dbMethod = config.getString("databaseMethod");
    }

    public List<String> getMethod() {
        return method;
    }

    public List<String> getWorkingWorlds() {
        return workingWorlds;
    }

    public double getPerBlockWalkAmount() {
        return perBlockWalkAmount;
    }
    public double getPerBlockAmount() {
        return perBlockAmount;
    }
    public double getPerMobkillAmount() {
        return perMobkillAmount;
    }

    public String getPrefix() {
        return (usePrefix) ? prefix : "&7[&6Eco&enomy&7]&r";
    }

    public String getDbMethod() {
        return dbMethod;
    }
}

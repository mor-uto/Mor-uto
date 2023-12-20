package net.moruto.economy.utils;

import net.moruto.economy.MorutosEconomy;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final List<String> method;
    private final List<String> workingWorlds;
    private final double perBlockAmount, perBlockWalkAmount, perMobkillAmount;
    private final String host, port, database, username, password;
    private final String prefix, dbMethod;
    private final boolean usePrefix;

    public ConfigManager() {
        FileConfiguration config = MorutosEconomy.getInstance().getConfig();
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

        host = config.getString("host");
        port = config.getString("port");
        database = config.getString("database");
        username = config.getString("username");
        password = config.getString("password");
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

    //MySQL stuff
    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

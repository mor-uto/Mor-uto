package net.moruto.economy.utils;

import net.moruto.economy.MorutosEconomy;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private final List<String> method;
    private final List<String> workingWorlds;
    private final double perBlockAmount, perBlockWalkAmount, perMobkillAmount;
    private final String prefix;
    private final boolean usePrefix;

    public ConfigManager() {
        String methodsBase = MorutosEconomy.getInstance().getConfig().getString("moneyMethod");
        String workingWorldsBase = MorutosEconomy.getInstance().getConfig().getString("workingWorlds");

        method = new ArrayList<>();
        workingWorlds = new ArrayList<>();

        method.addAll(List.of(methodsBase.replaceAll(" ", "").split(",")));
        workingWorlds.addAll(List.of(workingWorldsBase.replaceAll(" ", "").split(",")));

        prefix = MorutosEconomy.getInstance().getConfig().getString("prefix");
        usePrefix = MorutosEconomy.getInstance().getConfig().getBoolean("enablePrefix");
        perBlockAmount = MorutosEconomy.getInstance().getConfig().getDouble("moneyPerKillOrBlockBreak");
        perBlockWalkAmount = MorutosEconomy.getInstance().getConfig().getDouble("moneyPerBlockWalked");
        perMobkillAmount = MorutosEconomy.getInstance().getConfig().getDouble("moneyPerMobKilled");
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
        return (usePrefix) ? prefix : "";
    }
}

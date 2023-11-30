package net.moruto.economy.utils;

import net.moruto.economy.MorutosEconomy;

public class ConfigManager {
    private final String[] method;
    private final int amount;

    public ConfigManager() {
        if (MorutosEconomy.getInstance().getConfig().getString("moneyMethod").contains(",")) method = MorutosEconomy.getInstance().getConfig().getString("moneyMethod").split(",");
        else method = new String[]{MorutosEconomy.getInstance().getConfig().getString("moneyMethod")};

        amount = MorutosEconomy.getInstance().getConfig().getInt("moneyPerKillOrBlockBreak");
    }

    public String[] getMethod() {
        return method;
    }

    public int getAmount() {
        return amount;
    }
}

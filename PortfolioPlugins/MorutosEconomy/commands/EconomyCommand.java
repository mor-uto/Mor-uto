package net.moruto.economy.command;

import net.moruto.economy.EconomyImplementer;
import net.moruto.economy.MorutosEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EconomyCommand implements CommandExecutor, TabCompleter {
    private final MorutosEconomy plugin = MorutosEconomy.getInstance();
    private final EconomyImplementer eco = plugin.economyImplementer;

    private void sendMessageBranded(Player player, String str) {
        String prefix = "&7[&eEco&6nomy&7] ";
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + str));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName() == "balance" || command.getAliases().contains("bal")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        int balance = (int) plugin.economyImplementer.getBalance(target);
                        sendMessageBranded(player, "&a" + target.getName() + " &7has &a$" + balance);
                    }
                } else {
                    int balance = (int) plugin.economyImplementer.getBalance(player);
                    sendMessageBranded(player, "&7You have &a$" + balance);
                }
                return true;
            }

            if (!plugin.playerBank.containsKey(player.getUniqueId())){
                plugin.playerBank.put(player.getUniqueId(), 0.0);
            }

            if (args.length == 0) {
                sendMessageBranded(player, "&7<add/set/remove> <player> <amount>");
                return true;
            }

            if (args[0].equalsIgnoreCase("add")) {
                if (player.hasPermission("economy.add")) {
                    if (args.length == 3) {
                        Player target = Bukkit.getPlayer(args[1]);
                        int depositAmount = Integer.parseInt(args[2]);

                        if (target != null) {
                            eco.depositPlayer(target, depositAmount);
                            sendMessageBranded(player, "&7You added &a$" + depositAmount + "&7 to &a" + target.getName() + "&7's account");
                        }
                    }
                } else sendMessageBranded(player, "You dont have the required permission to use this");

                return true;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (player.hasPermission("economy.remove")) {
                    if (args.length == 3) {
                        Player target = Bukkit.getPlayer(args[1]);
                        double withdrawAmount = Integer.parseInt(args[2]);

                        if (target != null) {
                            eco.withdrawPlayer(target, withdrawAmount);
                            sendMessageBranded(player, "&7You removed &a$" + withdrawAmount + "&7 from &a" + target.getName() + "&7's account");
                        }
                    }
                } else sendMessageBranded(player, "You dont have the required permission to use this");

            return true;
            }

            if (args[0].equalsIgnoreCase("set")) {
                if (player.hasPermission("economy.set")) {
                    if (args.length == 3) {
                        Player target = Bukkit.getPlayer(args[1]);
                        int amount = Integer.parseInt(args[2]);

                        if (target != null) {
                            eco.withdrawPlayer(target, eco.getBalance(player));
                            eco.depositPlayer(target, amount);
                            sendMessageBranded(player, "&7You set &a" + target.getName() + "&7 's balance amount to &a$" + amount);
                        }
                    }
                } else sendMessageBranded(player, "You dont have the required permission to use this");

                return true;
            }

            if (args[0].equalsIgnoreCase("balance")) {
                if (player.hasPermission("economy.balance")) {
                    if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            int balance = (int) plugin.economyImplementer.getBalance(target);
                            sendMessageBranded(player, "&a" + target.getName() + " &7has &a$" + balance);
                        }
                    } else {
                        int balance = (int) plugin.economyImplementer.getBalance(player);
                        sendMessageBranded(player, "You have &a$" + balance);
                    }
                } else sendMessageBranded(player, "You dont have the required permission to use this");
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("balance");
            suggestions.add("set");
            suggestions.add("remove");
            suggestions.add("add");
        }

        if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
        }

        if (args.length == 3) {
            suggestions.add("amount");
        }

        return suggestions;
    }
}

package net.moruto.antiectasy.command;

import net.moruto.antiectasy.AntiEctasy;
import net.moruto.antiectasy.command.subcommands.*;
import net.moruto.antiectasy.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandsManager implements CommandExecutor, TabCompleter {
    private final ArrayList<SubCommand> subCommands = new ArrayList<>(Arrays.asList(
            new Scanner(),
            new Support(),
            new Disinfector()
    ));

    public SubCommand getCommand(String name) {
        for (SubCommand subCommand : subCommands) {
            if (subCommand.getName().equalsIgnoreCase(name)) {
                return subCommand;
            }
        }
        return null;
    }

    public CommandsManager() {
        AntiEctasy.getInstance().getCommand("antiectasy").setExecutor(this);
        AntiEctasy.getInstance().getCommand("antiectasy").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            Helper.sendMsg(commandSender, "&c/AntiEctasy <Command> <Argument>");
            return true;
        }

        if (args.length == 2 && !args[0].equalsIgnoreCase("support")) {
            Helper.sendMsg(commandSender, "&c/AntiEctasy <Command> <Argument>");
            return true;
        }

        String arg1 = args[0];
        SubCommand subCommand = getCommand(arg1);
        if (subCommand != null) {
            if (commandSender.hasPermission(subCommand.getPermission())) {
                subCommand.execute(commandSender, args);
            } else {
                Helper.sendMsg(commandSender, "&cYou dont have the required permission to use this!");
            }
        } else {
            Helper.sendMsg(commandSender, "&cThis command doesnt exist.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            for (SubCommand subCommand : subCommands) {
                suggestions.add(subCommand.getName());
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("scan") || args[0].equalsIgnoreCase("disinfect")) {
                for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                    suggestions.add(plugin.getName());
                }
            }
        }

        return suggestions;
    }
}

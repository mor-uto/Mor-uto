package net.moruto.antiectasy;

import net.moruto.antiectasy.tools.Disinfector;
import net.moruto.antiectasy.tools.JarFileChecker;
import net.moruto.antiectasy.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    public MainCommand() {
        AntiEctasy.getInstance().getCommand("antiectasy").setExecutor(this);
        AntiEctasy.getInstance().getCommand("antiectasy").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sendMsg(commandSender, "&c/AntiEctasy <SubCommand> <Argument>");
            return true;
        }

        if (args[0].equalsIgnoreCase("check")) {
            if (args.length == 2) {
                String pluginName = args[1];
                Plugin targetPlugin = Bukkit.getPluginManager().getPlugin(pluginName);

                if (targetPlugin != null) {
                    File pluginFile = getPluginFile(targetPlugin, commandSender);

                    JarFileChecker checker = AntiEctasy.getInstance().getJarFileChecker();
                    boolean check = checker.check(pluginFile, targetPlugin);

                    if (check) {
                        sendMsg(commandSender, "&c" + targetPlugin.getName() + " is infected with ectasy");
                        sendMsg(commandSender, "&cDisabling " + targetPlugin.getName() + " for protection");
                        if (AntiEctasy.getInstance().getConfigManager().isDisableInfectedPlugins()) {
                            Bukkit.getPluginManager().disablePlugin(targetPlugin);
                        }
                    } else {
                        sendMsg(commandSender, "&a" + targetPlugin.getName() + " is not infected");
                    }
                }
            } else {
                sendMsg(commandSender, "&c/AntiEctasy check <Plugin>");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("disinfect")) {
            if (args.length == 2) {
                String pluginName = args[1];
                Plugin targetPlugin = Bukkit.getPluginManager().getPlugin(pluginName);
                if (targetPlugin != null) {
                    File pluginFile = getPluginFile(targetPlugin, commandSender);

                    Disinfector disinfector = AntiEctasy.getInstance().getDisinfector();
                    boolean disInfected = disinfector.disinfect(pluginFile.getPath(), "net.md_5.bungee.api.chat.TextComponentDeserializer");

                    sendMsg(commandSender, "&cThis command/feature is still being worked on...");

                    /*
                    if (disInfected) {
                        sendMsg(commandSender, "&a" + targetPlugin.getName() + " has been disinfected from ectasy");
                    } else {
                        sendMsg(commandSender, "&cSomething wrong happened while trying to disinfect " + targetPlugin.getName());
                    }*/
                }
            } else {
                sendMsg(commandSender, "&c/AntiEctasy check <Plugin>");
            }
        }

        if (args[0].equalsIgnoreCase("support")) {
            sendMsg(commandSender, "&bAdd moruto_ on discord for support");
            return true;
        }

        return true;
    }

    private void sendMsg(CommandSender commandSender, String str) {
        String prefix = "&7[&bAnti&9Ectasy&7]&r";
        commandSender.sendMessage(Helper.trans(prefix + " " + str));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("check");
            suggestions.add("disinfect");
            suggestions.add("support");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("check") || args[0].equalsIgnoreCase("disinfect")) {
                for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                    suggestions.add(plugin.getName());
                }
            }
        }

        return suggestions;
    }

    private File getPluginFile(Plugin plugin, CommandSender commandSender) {
        try {
            return new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ignored) {
            commandSender.sendMessage(Helper.trans("&cThis plugin doesnt exist."));
        }

        return null;
    }
}

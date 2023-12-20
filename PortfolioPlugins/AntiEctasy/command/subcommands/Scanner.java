package net.moruto.antiectasy.command.subcommands;

import net.moruto.antiectasy.AntiEctasy;
import net.moruto.antiectasy.command.SubCommand;
import net.moruto.antiectasy.tools.JarFileChecker;
import net.moruto.antiectasy.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Scanner extends SubCommand {
    public Scanner() {
        super("scan", AntiEctasy.getInstance().getConfigManager().getScanPermission());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String pluginName = args[1];
            Plugin targetPlugin = Bukkit.getPluginManager().getPlugin(pluginName);

            if (targetPlugin != null) {
                File pluginFile = Helper.getPluginFile(targetPlugin, sender);

                JarFileChecker checker = AntiEctasy.getInstance().getJarFileChecker();
                boolean check = checker.check(pluginFile, targetPlugin);

                if (check) {
                    Helper.sendMsg(sender, "&c" + targetPlugin.getName() + " is infected with ectasy");
                    Helper.sendMsg(sender, "&cDisabling " + targetPlugin.getName() + " for protection");
                    if (AntiEctasy.getInstance().getConfigManager().isDisableInfectedPlugins()) {
                        Bukkit.getPluginManager().disablePlugin(targetPlugin);
                    }
                } else {
                    Helper.sendMsg(sender, "&a" + targetPlugin.getName() + " is not infected");
                }
            } else {
                Helper.sendMsg(sender, "&cThis plugin doesnt exist");
            }
        } else {
            Helper.sendMsg(sender, "&c/AntiEctasy scan <Plugin>");
        }
    }
}

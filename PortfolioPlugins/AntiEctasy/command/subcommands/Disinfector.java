package net.moruto.antiectasy.command.subcommands;

import net.moruto.antiectasy.AntiEctasy;
import net.moruto.antiectasy.command.SubCommand;
import net.moruto.antiectasy.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Disinfector extends SubCommand {
    public Disinfector() {
        super("disinfect", AntiEctasy.getInstance().getConfigManager().getDisinfectPermission());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String pluginName = args[1];
            Plugin targetPlugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if (targetPlugin != null) {
                File pluginFile = Helper.getPluginFile(targetPlugin, sender);

                net.moruto.antiectasy.tools.Disinfector disinfector = AntiEctasy.getInstance().getDisinfector();
                boolean disInfected = disinfector.disinfect(pluginFile.getPath(), "net.md_5.bungee.api.chat.TextComponentDeserializer");

                Helper.sendMsg(sender, "&cThis command/feature is still being worked on...");

                /*
                if (disInfected) {
                    sendMsg(commandSender, "&a" + targetPlugin.getName() + " has been disinfected from ectasy");
                } else {
                    sendMsg(commandSender, "&cSomething wrong happened while trying to disinfect " + targetPlugin.getName());
                }*/
            }
        } else {
            Helper.sendMsg(sender, "&c/AntiEctasy disinfect <Plugin/All>");
        }
    }
}

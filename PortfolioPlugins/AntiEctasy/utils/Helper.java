package net.moruto.antiectasy.utils;

import net.md_5.bungee.api.ChatColor;
import net.moruto.antiectasy.AntiEctasy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.net.URISyntaxException;

public class Helper {
    public static String trans(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static File getPluginFile(Plugin plugin, CommandSender player) {
        try {
            return new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ignored) {
            player.sendMessage(Helper.trans("&cThis plugin doesnt exist."));
        }

        return null;
    }

    public static void sendMsg(CommandSender sender, String str) {
        sender.sendMessage(Helper.trans(AntiEctasy.getInstance().getConfigManager().getPrefix() + " " + str));
    }
}

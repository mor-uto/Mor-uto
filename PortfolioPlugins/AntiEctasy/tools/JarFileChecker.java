package net.moruto.antiectasy.tools;

import net.moruto.antiectasy.AntiEctasy;
import net.moruto.antiectasy.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileChecker {
    public boolean check(File file, Plugin plugin) {
        String targetClassName = "net.md_5.bungee.api.chat.TranslatableComponentDeserializer";

        try {
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replaceAll("/", ".").substring(0, entry.getName().length() - 6);

                    if (className.equalsIgnoreCase(targetClassName)) {
                        if (plugin.isEnabled()) Bukkit.getServer().getPluginManager().disablePlugin(plugin);
                        deleteBungee();
                        return true;
                    }
                }
            }
            jarFile.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deleteBungee() {
        File bungeeFile = new File("plugins/pluginMetrics/bungee.jar");
        if (bungeeFile.exists()) {
            boolean delete = bungeeFile.delete();
            if (!delete) {
                bungeeFile.deleteOnExit();
            }
        }
    }
}

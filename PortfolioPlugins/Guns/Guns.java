package net.moruto.guns;

import net.moruto.guns.commands.command.CommandsManager;
import net.moruto.guns.listeners.listener.ListenersManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Guns extends JavaPlugin {
    public static Guns getInstance() {
        return JavaPlugin.getPlugin(Guns.class);
    }

    @Override
    public void onEnable() {
        new CommandsManager();
        new ListenersManager();
    }
}

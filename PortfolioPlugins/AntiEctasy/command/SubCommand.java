package net.moruto.antiectasy.command;

import net.moruto.antiectasy.utils.Helper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {
    private final String name, permission;

    public SubCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public abstract void execute(CommandSender sender, String[] args);
}

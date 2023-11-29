package net.moruto.guns.commands.command;

import net.moruto.guns.utils.Helper;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class Command implements CommandExecutor {
    private final String name;
    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void sendMessageBranded(Player player, String str) {
        player.sendMessage(Helper.trans(str));
    }

    public abstract void execute(Player player, String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) return true;

        execute((Player) commandSender, args);

        return true;
    }
}

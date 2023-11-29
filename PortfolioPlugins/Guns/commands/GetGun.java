package net.moruto.guns.commands;

import net.moruto.guns.Guns;
import net.moruto.guns.commands.command.Command;
import net.moruto.guns.items.GunItem;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GetGun extends Command implements TabCompleter {
    public GetGun() {
        super("getgun");
        Guns.getInstance().getCommand("getgun").setTabCompleter(this);
	}

    @Override
    public void execute(Player player, String[] args) {
        String name = "example";
        int damage = 5;
        if (args.length >= 1) name = args[0];
        if (args.length == 2) damage = Integer.parseInt(args[1]);

        GunItem gun = new GunItem(name, damage, player);
        player.getInventory().setItemInMainHand(gun.getGun());
        sendMessageBranded(player, "executed command...");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("GunName");
        }

        if (args.length == 2) {
            suggestions.add("Damage");
        }

        return suggestions;
    }
}

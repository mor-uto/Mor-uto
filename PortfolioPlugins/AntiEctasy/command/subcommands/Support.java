package net.moruto.antiectasy.command.subcommands;

import net.moruto.antiectasy.AntiEctasy;
import net.moruto.antiectasy.command.SubCommand;
import net.moruto.antiectasy.utils.Helper;
import org.bukkit.command.CommandSender;

public class Support extends SubCommand {
    public Support() {
        super("support",  AntiEctasy.getInstance().getConfigManager().getSupportPermission());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Helper.sendMsg(sender, "&bAdd moruto_ on discord for support");
    }
}

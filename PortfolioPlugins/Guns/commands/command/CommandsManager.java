package net.moruto.guns.commands.command;

import net.moruto.guns.Guns;
import net.moruto.guns.commands.GetGun;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandsManager {
    public CommandsManager() {
        ArrayList<Command> commands = new ArrayList<>(Arrays.asList(
                new GetGun()
        ));

        for (Command command : commands) {
            Guns.getInstance().getCommand(command.getName()).setExecutor(command);
        }
    }
}

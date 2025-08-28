package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import me.marensovich.bestCheck.BestCheck;

public class ReloadCommand {

    public static CommandAPICommand reloadCommand(){
        return new CommandAPICommand("reload")
                .withPermission("check.reload")
                .withHelp("Reload plugin", "Command reload the configuration files of the plugin")
                .withUsage("/check reload")
                .executes(((sender, args) -> {
                    BestCheck.getInstance().reloadConfig();
                    sender.sendMessage("Plugin reloaded");
                }));
    }
}

package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;

public class StopCommand {

    public static CommandAPICommand stopCommand(){
        return new CommandAPICommand("stop")
                .withHelp("Stop Check", "Command stop the check")
                .withPermission("check.stop")
                .withUsage("/check stop")
                .executes((sender, args) -> {
                    sender.sendMessage("/check stop command text");
                });
    }
}


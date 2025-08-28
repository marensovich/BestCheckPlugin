package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;

public class RunCommand {

    public static CommandAPICommand runCommand(){
        return new CommandAPICommand("run")
                .withHelp("Run Check", "Command start the check")
                .withPermission("check.run")
                .withUsage("/check run")
                .executes((sender, args) -> {
                    sender.sendMessage("/check run command text");
                });
    }
}

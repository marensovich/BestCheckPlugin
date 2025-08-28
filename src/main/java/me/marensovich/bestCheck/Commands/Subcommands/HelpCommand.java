package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;

public class HelpCommand {

    public static CommandAPICommand helpCommand(){
        return new CommandAPICommand("help")
                .withHelp("Help command", "Shows the help about the plugin")
                .withPermission("check.help")
                .withUsage("/check help")
                .executes((sender, args) -> {
                    sender.sendMessage("/help command text");
                });
    }

}

package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;

public class StatsCommand {

    public static CommandAPICommand statsCommand(){
        return new CommandAPICommand("stats")
                .withHelp("Stats command", "Shows the stats about the plugin")
                .withPermission("check.stats")
                .withUsage("/check stats")
                .executes((sender, args) -> {
                    sender.sendMessage("/check stats command text");
                });
    }
}

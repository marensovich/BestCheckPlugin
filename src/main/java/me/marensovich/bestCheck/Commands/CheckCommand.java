package me.marensovich.bestCheck.Commands;

import dev.jorel.commandapi.CommandAPICommand;
import me.marensovich.bestCheck.Commands.Subcommands.*;

public class CheckCommand {

    public static void registerCommands(){
        new CommandAPICommand("check")
                .withSubcommand(HelpCommand.helpCommand())
                .withSubcommand(ReloadCommand.reloadCommand())
                .withSubcommand(StatsCommand.statsCommand())
                .withSubcommand(RunCommand.runCommand())
                .withSubcommand(StopCommand.stopCommand())
                .withHelp("Base command", "Default command")
                .withPermission("check.check")
                .executes(((sender, args) -> {
                    sender.sendMessage("Info about the plugin");
                }))
                .register();
    }
}

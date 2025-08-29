package me.marensovich.bestCheck.Commands;

import dev.jorel.commandapi.CommandAPICommand;
import me.marensovich.bestCheck.BestCheck;
import me.marensovich.bestCheck.Commands.Subcommands.*;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Objects;

public class CheckCommand {

    public static void registerCommands(){
        new CommandAPICommand("check")
                .withSubcommand(HelpCommand.helpCommand())
                .withSubcommand(ReloadCommand.reloadCommand())
                .withSubcommand(StatsCommand.statsCommand())
                .withSubcommand(RunCommand.runCommand())
                .withSubcommand(StopCommand.stopCommand())
                .withHelp("Main command", "The main command of the plugin")
                .withPermission("check.check")
                .executes(((sender, args) -> {
                    List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("check-message");
                    for(String s : message){
                        sender.sendMessage(
                                ChatColor.translateAlternateColorCodes('&',
                                        Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                ChatColor.translateAlternateColorCodes('&', s));
                    }
                }))
                .register();
    }
}

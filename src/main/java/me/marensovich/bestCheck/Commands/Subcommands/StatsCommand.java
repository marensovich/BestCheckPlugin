package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import me.marensovich.bestCheck.BestCheck;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Objects;

public class StatsCommand {

    public static CommandAPICommand statsCommand(){
        return new CommandAPICommand("stats")
                .withHelp("Stats command", "Shows the stats about the plugin")
                .withPermission("check.stats")
                .withUsage("/check stats")
                .executes((sender, args) -> {
                    List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("stats-command");
                    for(String s : message){
                        sender.sendMessage(
                                ChatColor.translateAlternateColorCodes('&',
                                        Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                ChatColor.translateAlternateColorCodes('&', s));
                    }
                });
    }
}

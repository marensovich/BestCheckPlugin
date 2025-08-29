package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import me.marensovich.bestCheck.BestCheck;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Objects;

public class HelpCommand {

    public static CommandAPICommand helpCommand(){
        return new CommandAPICommand("help")
                .withHelp("Help with the plugin", "Find out information about using the plugin")
                .withPermission("check.help")
                .withUsage("/check help")
                .executes((sender, args) -> {
                    List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("help-message");
                    for(String s : message){
                        sender.sendMessage(
                                ChatColor.translateAlternateColorCodes('&',
                                        Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                ChatColor.translateAlternateColorCodes('&', s));
                    }
                });
    }

}

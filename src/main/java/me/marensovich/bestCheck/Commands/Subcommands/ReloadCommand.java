package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import me.marensovich.bestCheck.BestCheck;
import org.bukkit.ChatColor;

import java.util.Objects;

public class ReloadCommand {

    public static CommandAPICommand reloadCommand(){
        return new CommandAPICommand("reload")
                .withPermission("check.reload")
                .withHelp("Reload the plugin", "Reloading the plugin configuration files")
                .withUsage("/check reload")
                .executes(((sender, args) -> {
                    BestCheck.getInstance().reloadConfig();
                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                            ChatColor.translateAlternateColorCodes('&',
                                    Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("reload-message"))));
                }));
    }
}

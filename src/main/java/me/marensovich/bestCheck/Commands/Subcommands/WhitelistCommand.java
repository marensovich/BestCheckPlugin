package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.marensovich.bestCheck.BestCheck;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class WhitelistCommand {

    public static CommandAPICommand whitelistCommand(){
        return new CommandAPICommand("whitelist")
                .withHelp("WhiteList Management", "The whitelist management command")
                .withPermission("check.whitelist")
                .withUsage(
                        "/check whitelist add <player>",
                        "/check whitelist remove <player>",
                        "/check whitelist clear"
                )
                .withSubcommand(new CommandAPICommand("add")
                        .withPermission("check.whitelist.add")
                        .withUsage("/check whitelist add <player>")
                        .withHelp("Add a player to whitelist", "Add a player to whitelist")
                        .withArguments(new PlayerArgument("player"))
                        .executes((sender, args) -> {
                            Player player = (Player) args.get("player");
                            if (!BestCheck.getInstance().getWhitelistConfigManager().checkPlayerExists(player.getUniqueId().toString())){
                                BestCheck.getInstance().getWhitelistConfigManager().addPlayerToWhitelist(player.getUniqueId().toString());
                                List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("whitelist-add-message");
                                for(String s : message){
                                    sender.sendMessage(
                                            ChatColor.translateAlternateColorCodes('&',
                                                    Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                                    ChatColor.translateAlternateColorCodes('&', s)
                                                            .replace("%player%", player.getName())
                                                            .replace("%uuid%", player.getUniqueId().toString()));
                                }
                            } else {
                                List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("whitelist-add-player-exists");
                                for(String s : message){
                                    sender.sendMessage(
                                            ChatColor.translateAlternateColorCodes('&',
                                                    Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                                    ChatColor.translateAlternateColorCodes('&', s)
                                                            .replace("%player%", player.getName())
                                                            .replace("%uuid%", player.getUniqueId().toString()));
                                }
                            }
                        })
                )
                .withSubcommand(new CommandAPICommand("remove")
                        .withPermission("check.whitelist.remove")
                        .withUsage("/check whitelist remove <player>")
                        .withHelp("Remove a player from whitelist", "Remove a player from whitelist")
                        .withArguments(new PlayerArgument("player"))
                        .executes((sender, args) -> {
                            Player player = (Player) args.get("player");
                            if (!BestCheck.getInstance().getWhitelistConfigManager().checkPlayerExists(player.getUniqueId().toString())){
                                BestCheck.getInstance().getWhitelistConfigManager().removePlayerFromWhitelist(player.getUniqueId().toString());
                                List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("whitelist-remove-player-not-exists");
                                for(String s : message){
                                    sender.sendMessage(
                                            ChatColor.translateAlternateColorCodes('&',
                                                    Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                                    ChatColor.translateAlternateColorCodes('&', s)
                                                            .replace("%player%", player.getName())
                                                            .replace("%uuid%", player.getUniqueId().toString()));
                                }
                            } else {
                                BestCheck.getInstance().getWhitelistConfigManager().removePlayerFromWhitelist(player.getUniqueId().toString());
                                List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("whitelist-remove-message");
                                for(String s : message){
                                    sender.sendMessage(
                                            ChatColor.translateAlternateColorCodes('&',
                                                    Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                                    ChatColor.translateAlternateColorCodes('&', s)
                                                            .replace("%player%", player.getName())
                                                            .replace("%uuid%", player.getUniqueId().toString()));
                                }
                            }
                        })
                )
                .withSubcommand(new CommandAPICommand("clear")
                        .withPermission("check.whitelist.clear")
                        .withUsage("/check whitelist clear")
                        .withHelp("Clear whitelist", "Clear whitelist")
                        .executes((sender, args) -> {
                            BestCheck.getInstance().getWhitelistConfigManager().clearWhitelist();
                            List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("whitelist-clear-message");
                            for(String s : message){
                                sender.sendMessage(
                                        ChatColor.translateAlternateColorCodes('&',
                                                Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                                ChatColor.translateAlternateColorCodes('&', s));
                            }
                        })
                )
                .withSubcommand(new CommandAPICommand("info")
                        .withPermission("check.whitelist.info")
                        .withUsage("/check whitelist info")
                        .withHelp("Checks if a player is in the whitelist", "Сhecks if a player is in the whitelist")
                        .withArguments(new PlayerArgument("player"))
                        .executes((sender, args) -> {
                            Player player = (Player) args.get("player");
                            if (BestCheck.getInstance().getWhitelistConfigManager().checkPlayerExists(player.getUniqueId().toString())){
                                List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("whitelist-check-true-message");
                                for(String s : message){
                                    sender.sendMessage(
                                            ChatColor.translateAlternateColorCodes('&',
                                                    Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                                    ChatColor.translateAlternateColorCodes('&', s).replace("%player%", player.getName()));
                                }
                            } else {
                                List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("whitelist-check-false-message");
                                for(String s : message){
                                    sender.sendMessage(
                                            ChatColor.translateAlternateColorCodes('&',
                                                    Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                                    ChatColor.translateAlternateColorCodes('&', s).replace("%player%", player.getName()));
                                }
                            }

                        })
                )
                .executes((sender, args) -> {
                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    Objects.requireNonNull(
                                            BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                            ChatColor.translateAlternateColorCodes('&',
                                    Objects.requireNonNull(
                                            BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("whitelist-message"))));
                });
    }

}

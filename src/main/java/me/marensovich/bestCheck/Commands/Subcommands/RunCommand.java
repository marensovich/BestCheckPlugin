package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.SafeSuggestions;
import me.marensovich.bestCheck.BestCheck;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class RunCommand {



    public static CommandAPICommand runCommand(){
        return new CommandAPICommand("run")
                .withHelp("Run Check", "Command start the check")
                .withPermission("check.run")
                .withArguments(new PlayerArgument("player")
                        .replaceSafeSuggestions(SafeSuggestions.suggest(info ->
                                Bukkit.getOnlinePlayers().toArray(new Player[0])
                        )))
                .withUsage("/check run")
                .executes((sender, args) -> {
                    Player admin = (Player) sender;
                    Player player = (Player) args.get("player");

                    /*if (player.getUniqueId().equals(admin.getUniqueId())) {
                        List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("check-run-player-yourself");
                        for(String s : message){
                            sender.sendMessage(
                                    ChatColor.translateAlternateColorCodes('&',
                                            Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                            ChatColor.translateAlternateColorCodes('&', s));
                        }
                        return;
                    }*/
                    if (BestCheck.getInstance().getWhitelistConfigManager().checkPlayerExists(player.getUniqueId().toString())) {
                        List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("check-run-player-in-whitelist");
                        for(String s : message){
                            sender.sendMessage(
                                    ChatColor.translateAlternateColorCodes('&',
                                            Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                            ChatColor.translateAlternateColorCodes('&', s));
                        }
                        return;
                    }
                    if (!player.isOnline()) {
                        List<String> message = BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getStringList("check-run-player-offline");
                        for(String s : message){
                            sender.sendMessage(
                                    ChatColor.translateAlternateColorCodes('&',
                                            Objects.requireNonNull(BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("plugin-prefix"))) +
                                            ChatColor.translateAlternateColorCodes('&', s));
                        }
                        return;
                    }

                    BestCheck.getInstance().getCheckManager().freezePlayer(player);

                    player.showTitle(
                            Title.title(
                                    Component.text(
                                            Objects.requireNonNull(
                                                    BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("notification.title"))),
                                    Component.text(
                                            Objects.requireNonNull(
                                                    BestCheck.getInstance().getMessageConfigManager().getMessageConfig().getString("notification.subtitle"))),
                                    Title.Times.times(
                                            Duration.ofMillis(500),
                                            Duration.ofSeconds(3600),
                                            Duration.ofMillis(500)
                                    )
                            )
                    );
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 3));
                });
    }
}

package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import me.marensovich.bestCheck.BestCheck;
import org.bukkit.entity.Player;

public class StopCommand {

    public static CommandAPICommand stopCommand(){
        return new CommandAPICommand("stop")
                .withHelp("Stop Check", "Command stop the check")
                .withPermission("check.stop")
                .withUsage("/check stop")
                .executes((sender, args) -> {
                    if (BestCheck.getInstance().getCheckManager().isFrozen((Player) sender)){
                        BestCheck.getInstance().getCheckManager().unfreezePlayer((Player) sender);
                        sender.clearTitle();
                        ((Player) sender).clearActivePotionEffects();
                    }
                });
    }
}


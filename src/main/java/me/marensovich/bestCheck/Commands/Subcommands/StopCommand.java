package me.marensovich.bestCheck.Commands.Subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import me.marensovich.bestCheck.BestCheck;
import me.marensovich.bestCheck.Data.CheckResult;
import me.marensovich.bestCheck.Data.CheckStatus;
import me.marensovich.bestCheck.Database.Managers.MySQLManager;
import me.marensovich.bestCheck.Database.Managers.SQLiteManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

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

                        Player player = (Player) sender;

                        switch (BestCheck.getInstance().getConfigManager().getConfig().getString("database.type").toLowerCase()) {
                            case "mysql" -> {
                                try {
                                    MySQLManager.updateLastCheckByAdmin(player.getUniqueId().toString(), CheckStatus.COMPLETED.getStatus(), CheckResult.RELEASED.getResult());
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            case "sqlite" -> {
                                try {
                                    SQLiteManager.updateLastCheckByAdmin(player.getUniqueId().toString(), CheckStatus.COMPLETED.getStatus(), CheckResult.RELEASED.getResult());
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            default -> {

                            }
                        }

                    }
                });
    }
}


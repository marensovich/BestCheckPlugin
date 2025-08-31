package me.marensovich.bestCheck;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import me.marensovich.bestCheck.Commands.CheckCommand;
import me.marensovich.bestCheck.Configs.DefaultConfig;
import me.marensovich.bestCheck.Configs.MessageConfig;
import me.marensovich.bestCheck.Configs.WhitelistConfig;
import me.marensovich.bestCheck.Database.DatabaseManager;
import me.marensovich.bestCheck.Database.Managers.MySQLManager;
import me.marensovich.bestCheck.Database.Managers.SQLiteManager;
import me.marensovich.bestCheck.Listeners.PlayerMoveEventListener;
import me.marensovich.bestCheck.Managers.CheckManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class BestCheck extends JavaPlugin {

    @Getter private static BestCheck instance;

    @Getter private FileConfiguration config;

    @Getter private DefaultConfig configManager;
    @Getter private MessageConfig messageConfigManager;
    @Getter private WhitelistConfig whitelistConfigManager;

    @Getter private DatabaseManager databaseManager;

    @Getter private CheckManager checkManager;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        this.config = super.getConfig();

        if (messageConfigManager != null) {
            messageConfigManager.reload();
        }
        if (whitelistConfigManager != null) {
            whitelistConfigManager.reload();
        }
    }

    @Override
    public void onEnable() {instance = this;
        saveDefaultConfig();


        this.checkManager = new CheckManager();
        this.configManager = new DefaultConfig(this);
        this.messageConfigManager = new MessageConfig(this);
        this.whitelistConfigManager = new WhitelistConfig(this);

        CommandAPI.onEnable();
        CheckCommand.registerCommands();
        registerEvents();

        switch (getConfigManager().getConfig().getString("database.type").toLowerCase()) {
            case "mysql" -> {
                databaseManager = new MySQLManager();
            }
            case "sqlite" -> {
                databaseManager = new SQLiteManager();
            }
            default -> {
                getLogger().severe("Invalid database type! Plugin disabled!");
                getServer().getPluginManager().disablePlugin(this);
            }
        }
        databaseManager.connect();
        getLogger().info("BestCheck plugin enabled");
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new PlayerMoveEventListener(), this);
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.disconnect();
        }
        CommandAPI.onDisable();
        getLogger().info("Plugin has been disabled!");
    }
}

package me.marensovich.bestCheck;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import me.marensovich.bestCheck.Commands.CheckCommand;
import me.marensovich.bestCheck.Configs.DefaultConfig;
import me.marensovich.bestCheck.Configs.MessageConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BestCheck extends JavaPlugin {

    @Getter private static BestCheck instance;

    @Getter private FileConfiguration config;

    @Getter private DefaultConfig configManager;
    @Getter private MessageConfig messageConfigManager;

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
    }

    @Override
    public void onEnable() {instance = this;
        saveDefaultConfig();

        this.configManager = new DefaultConfig(this);
        this.messageConfigManager = new MessageConfig(this);

        CommandAPI.onEnable();
        CheckCommand.registerCommands();
        getLogger().info("BestCheck plugin enabled");
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        getLogger().info("Plugin has been disabled!");
    }
}

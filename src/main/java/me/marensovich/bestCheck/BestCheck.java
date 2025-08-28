package me.marensovich.bestCheck;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class BestCheck extends JavaPlugin {

    private static BestCheck instance;
    public static BestCheck getInstance() {
        return instance;
    }

    private FileConfiguration config;
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
        new CommandAPICommand("ping")
                .executes((sender, args) -> {
                    sender.sendMessage("pong!");
                }).register();
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        this.config = super.getConfig();
    }

    @Override
    public void onEnable() {instance = this;
        saveDefaultConfig();
        reloadConfig();

        CommandAPI.onEnable();

        getLogger().info("BestCheck plugin enabled");

    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        getLogger().info("Plugin has been disabled!");
    }
}

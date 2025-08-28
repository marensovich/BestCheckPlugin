package me.marensovich.bestCheck;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import me.marensovich.bestCheck.Commands.CheckCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public class BestCheck extends JavaPlugin {

    @Getter private static BestCheck instance;

    @Getter private FileConfiguration config;

    private File messageFile;
    @Getter private FileConfiguration messageConfig;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }

    // TODO Вынести работы с конфигами в отдельные методы

    @Override
    public void reloadConfig() {
        if (messageFile == null) {
            messageFile = new File(getDataFolder(), "message.yml");
        }
        if (!messageFile.exists()) {
            saveResource("message.yml", false);
        }
        messageConfig = new YamlConfiguration();
        try {
            messageConfig.load(messageFile);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().severe("Failed to load message.yml!");
            e.printStackTrace();
        }
        super.reloadConfig();
        this.config = super.getConfig();
    }

    @Override
    public void onEnable() {instance = this;
        saveDefaultConfig();
        reloadConfig();

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

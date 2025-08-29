package me.marensovich.bestCheck.Configs;

import lombok.Getter;
import me.marensovich.bestCheck.BestCheck;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessageConfig {

    private final BestCheck plugin;
    private File messageFile;
    @Getter private FileConfiguration messageConfig;

    public MessageConfig(BestCheck plugin) {
        this.plugin = plugin;
        setup();
    }

    public void setup() {
        messageFile = new File(plugin.getDataFolder(), "message.yml");
        if (!messageFile.exists()) {
            plugin.saveResource("message.yml", false);
        }
        messageConfig = new YamlConfiguration();
        try {
            messageConfig.load(messageFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().severe("Failed to load message.yml!");
            e.printStackTrace();
        }
    }

    public void reload() {
        setup();
    }
}
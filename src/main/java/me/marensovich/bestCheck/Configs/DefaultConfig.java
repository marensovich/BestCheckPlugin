package me.marensovich.bestCheck.Configs;


import lombok.Getter;
import me.marensovich.bestCheck.BestCheck;
import org.bukkit.configuration.file.FileConfiguration;

public class DefaultConfig {

    private final BestCheck plugin;

    @Getter
    private FileConfiguration config;

    public DefaultConfig(BestCheck plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

}

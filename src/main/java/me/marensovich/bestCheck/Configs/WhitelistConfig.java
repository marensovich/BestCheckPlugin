package me.marensovich.bestCheck.Configs;

import lombok.Getter;
import me.marensovich.bestCheck.BestCheck;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WhitelistConfig {

    private final BestCheck plugin;
    private File whiteListFile;
    @Getter private FileConfiguration whiteListConfig;

    public WhitelistConfig(BestCheck plugin) {
        this.plugin = plugin;
        setup();
    }

    public void saveWhiteListConfig() {
        try {
            whiteListConfig.save(whiteListFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Не удалось сохранить whitelist.yml!");
            e.printStackTrace();
        }
    }

    public void setup() {
        whiteListFile = new File(plugin.getDataFolder(), "whitelist.yml");
        if (!whiteListFile.exists()) {
            plugin.saveResource("whitelist.yml", false);
        }
        whiteListConfig = new YamlConfiguration();
        try {
            whiteListConfig.load(whiteListFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().severe("Failed to load message.yml!");
            e.printStackTrace();
        }
    }

    public void reload() {
        setup();
    }


    public List<String> getAllPlayers(){
        return this.getWhiteListConfig().getStringList("whitelist");
    }

    public void clearWhitelist() {
        this.getWhiteListConfig().set("whitelist", new java.util.ArrayList<>());
        saveWhiteListConfig();
    }

    public void addPlayerToWhitelist(String uuid) {
        List<String> whitelist = this.getWhiteListConfig().getStringList("whitelist");
        if (!whitelist.contains(uuid)) {
            whitelist.add(uuid);
            this.getWhiteListConfig().set("whitelist", whitelist);
            this.saveWhiteListConfig();
        }
    }

    public boolean checkPlayerExists(String uuid){
        return this.getWhiteListConfig().getStringList("whitelist").contains(uuid);
    }

    public void removePlayerFromWhitelist(String uuid) {
        List<String> whitelist = this.getWhiteListConfig().getStringList("whitelist");
        if (whitelist.contains(uuid)) {
            whitelist.remove(uuid);
            this.getWhiteListConfig().set("whitelist", whitelist);
            this.saveWhiteListConfig();
        }
    }

}
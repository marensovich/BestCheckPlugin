package me.marensovich.bestCheck;

import org.bukkit.plugin.java.JavaPlugin;

public final class BestCheck extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled!");
    }
}

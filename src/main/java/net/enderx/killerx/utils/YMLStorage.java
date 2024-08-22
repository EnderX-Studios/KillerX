package net.enderx.killerx.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import net.enderx.killerx.KillerX;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class YMLStorage {

    private final KillerX plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private final String fileName;
    private final String path;

    public YMLStorage(KillerX plugin, String fileName, String path) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.path = path;
        saveDefaultConfig();
    }

    public KillerX getPlugin() {
        return plugin;
    }

    public void reloadConfig() {
        if (configFile == null)
            configFile = new File(path, fileName);
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (dataConfig == null)
            reloadConfig();
        return dataConfig;
    }

    public void saveConfig() {
        if (dataConfig == null || configFile == null)
            return;
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(path, fileName);
        }

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();

                FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                config.save(configFile);

                plugin.getLogger().info("Created " + fileName + " with default settings.");
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not create " + fileName, e);
            }
        }
    }
}

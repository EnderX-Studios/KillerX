package com.enderx.killerx;

import com.enderx.killerx.commands.KillerXCommand;
import com.enderx.killerx.database.DatabaseManager;
import com.enderx.killerx.events.PlayerDeathListener;
import com.enderx.killerx.placeholder.PlaceholderHook;
import com.enderx.killerx.utils.UpdateChecker;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillerX extends JavaPlugin {

    private DatabaseManager dbManager;

    public static KillerX instance;

    @Override
    @SneakyThrows
    public void onEnable() {

        instance = this;

        Class.forName("org.sqlite.JDBC");

        dbManager = new DatabaseManager("jdbc:sqlite:killdeathcounter.db");
        PlayerDeathListener listener = new PlayerDeathListener(dbManager);
        this.getServer().getPluginManager().registerEvents(listener, this);

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PlaceholderHook(this, dbManager).register();
        }

        new UpdateChecker(this, 111951).getLatestVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getLogger().info("\n\nKillerX is at the latest version\n");
            } else {
                Bukkit.getLogger().warning("\n\nThere is an update of KillerX\n");
                Bukkit.getLogger().warning("");
            }
        });


        registerCommands();
    }

    @Override
    @SneakyThrows
    public void onDisable() {
        if (dbManager != null) {
            dbManager.close();
        }
    }

    private void registerCommands(){
        getCommand("killerx").setExecutor(new KillerXCommand(this));
    }
}

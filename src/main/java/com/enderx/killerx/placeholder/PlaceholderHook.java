package com.enderx.killerx.placeholder;

import com.enderx.killerx.KillerX;
import com.enderx.killerx.database.DatabaseManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PlaceholderHook extends PlaceholderExpansion {

    private KillerX plugin;
    private DatabaseManager dbManager;

    public PlaceholderHook(KillerX plugin, DatabaseManager dbManager) {
        this.plugin = plugin;
        this.dbManager = dbManager;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "killerx";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){
        if(player == null){
            return "";
        }

        // %killerx_kills%
        if(identifier.equals("kills")){
            try {
                return String.valueOf(dbManager.getKillCount(player.getUniqueId().toString()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // %killerx_deaths%
        if(identifier.equals("deaths")){
            try {
                return String.valueOf(dbManager.getDeathCount(player.getUniqueId().toString()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        // %killerx_streak%
        if(identifier.equals("streak")){
            try {
                return String.valueOf(dbManager.getKillStreak(player.getUniqueId().toString()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}

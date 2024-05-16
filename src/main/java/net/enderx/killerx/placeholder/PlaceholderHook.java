package net.enderx.killerx.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.enderx.killerx.KillerX;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlaceholderHook extends PlaceholderExpansion {

    private final KillerX plugin;

    public PlaceholderHook(KillerX plugin) {
        this.plugin = plugin;
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
        UUID playerUUID = player.getUniqueId();

        if(identifier.equals("kills")){
            return String.valueOf(plugin.getDatabase().getKills(playerUUID));
        }else if(identifier.equals("deaths")){
            return String.valueOf(plugin.getDatabase().getDeaths(playerUUID));
        }else if(identifier.equals("streak")){
            return String.valueOf(plugin.getDatabase().getKillStreak(playerUUID));
        }
        return null;
    }
}

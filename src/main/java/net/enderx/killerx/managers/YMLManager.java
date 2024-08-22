package net.enderx.killerx.managers;

import net.enderx.killerx.utils.YMLStorage;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class YMLManager implements IDataManager {

    private final YMLStorage ymlStorage;

    public YMLManager(YMLStorage ymlStorage) {
        this.ymlStorage = ymlStorage;
    }

    public void registerPlayer(UUID uuid) {
        FileConfiguration dataConfig = ymlStorage.getConfig();
        if (!dataConfig.contains(uuid.toString())) {
            dataConfig.set(uuid.toString() + ".kills", 0);
            dataConfig.set(uuid.toString() + ".deaths", 0);
            dataConfig.set(uuid.toString() + ".killstreak", 0);
            ymlStorage.saveConfig();
        }
    }

    public boolean isRegistered(OfflinePlayer player) {
        return ymlStorage.getConfig().contains(player.getUniqueId().toString());
    }

    public void addKill(UUID uuid) {
        FileConfiguration dataConfig = ymlStorage.getConfig();
        int kills = dataConfig.getInt(uuid.toString() + ".kills");
        int killstreak = dataConfig.getInt(uuid.toString() + ".killstreak");
        dataConfig.set(uuid.toString() + ".kills", kills + 1);
        dataConfig.set(uuid.toString() + ".killstreak", killstreak + 1);
        ymlStorage.saveConfig();
    }

    public void addDeath(UUID uuid) {
        FileConfiguration dataConfig = ymlStorage.getConfig();
        int deaths = dataConfig.getInt(uuid.toString() + ".deaths");
        dataConfig.set(uuid.toString() + ".deaths", deaths + 1);
        dataConfig.set(uuid.toString() + ".killstreak", 0);
        ymlStorage.saveConfig();
    }

    public void addKillStreak(UUID uuid) {
        FileConfiguration dataConfig = ymlStorage.getConfig();
        int killstreak = dataConfig.getInt(uuid.toString() + ".killstreak");
        dataConfig.set(uuid.toString() + ".killstreak", killstreak + 1);
    }
    
    public int getKills(UUID uuid) {
        return ymlStorage.getConfig().getInt(uuid.toString() + ".kills");
    }

    public int getDeaths(UUID uuid) {
        return ymlStorage.getConfig().getInt(uuid.toString() + ".deaths");
    }

    public int getKillStreak(UUID uuid) {
        return ymlStorage.getConfig().getInt(uuid.toString() + ".killstreak");
    }

    public void setKill(UUID uuid, int kills) {
        ymlStorage.getConfig().set(uuid.toString() + ".kills", kills);
        ymlStorage.saveConfig();
    }

    public void setDeath(UUID uuid, int deaths) {
        ymlStorage.getConfig().set(uuid.toString() + ".deaths", deaths);
        ymlStorage.saveConfig();
    }

    public void setKillStreak(UUID uuid, int streak) {
        ymlStorage.getConfig().set(uuid.toString() + ".killstreak", streak);
        ymlStorage.saveConfig();
    }
}

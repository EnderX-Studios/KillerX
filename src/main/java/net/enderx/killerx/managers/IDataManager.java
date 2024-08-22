package net.enderx.killerx.managers;

import lombok.SneakyThrows;
import org.bukkit.OfflinePlayer;

import java.util.UUID;



public interface IDataManager {


    void registerPlayer(UUID uuid);
    boolean isRegistered(OfflinePlayer player);
    void addKill(UUID uuid);
    void addDeath(UUID uuid);
    void addKillStreak(UUID uuid);
    int getKills(UUID uuid);
    int getDeaths(UUID uuid);
    int getKillStreak(UUID uuid);
    void setKill(UUID uuid, int kills);
    void setDeath(UUID uuid, int deaths);
    void setKillStreak(UUID uuid, int streak);
}

package net.enderx.killerx.database;

import lombok.SneakyThrows;
import net.enderx.killerx.KillerX;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class DatabaseManager{

    private final KillerX plugin;

    public DatabaseManager(KillerX plugin) {
        this.plugin = plugin;
    }

    @SneakyThrows
    public void registerPlayer(UUID uuid) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT IGNORE INTO player_stats (uuid) VALUES (?)")) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    public boolean isRegistered(OfflinePlayer player) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM player_stats WHERE uuid = ?")) {
            ps.setString(1, player.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @SneakyThrows
    public void addKill(UUID uuid) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE player_stats SET kills = kills + 1, killstreak = killstreak + 1 WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    public void addDeath(UUID uuid) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE player_stats SET deaths = deaths + 1, killstreak = 0 WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    public void addKillStreak(UUID uuid) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE player_stats SET killstreak = killstreak + 1 WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    public int getKills(UUID uuid) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT kills FROM player_stats WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("kills");
                }
            }
        }
        return 0;
    }

    @SneakyThrows
    public int getDeaths(UUID uuid) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT deaths FROM player_stats WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("deaths");
                }
            }
        }
        return 0;
    }

    @SneakyThrows
    public int getKillStreak(UUID uuid) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT killstreak FROM player_stats WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("killstreak");
                }
            }
        }
        return 0;
    }

    @SneakyThrows
    public void setKill(UUID uuid, int kills) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE player_stats SET kills = ? WHERE uuid = ?")) {
            ps.setInt(1, kills);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    public void setDeath(UUID uuid, int deaths) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE player_stats SET deaths = ? WHERE uuid = ?")) {
            ps.setInt(1, deaths);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    public void setKillStreak(UUID uuid, int streak) {
        try (Connection connection = plugin.getMySQL().getHikari().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE player_stats SET killstreak = ? WHERE uuid = ?")) {
            ps.setInt(1, streak);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }
    }
}

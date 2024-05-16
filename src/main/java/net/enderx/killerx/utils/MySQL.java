package net.enderx.killerx.utils;

import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import net.enderx.killerx.KillerX;

import java.sql.PreparedStatement;

public class MySQL {

    private final KillerX plugin;

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    private HikariDataSource hikari;

    public MySQL(KillerX plugin) {
        this.plugin = plugin;
        this.host = plugin.getConfig().getString("Database.hostname");
        this.port = plugin.getConfig().getInt("Database.port");
        this.database = plugin.getConfig().getString("Database.database");
        this.username = plugin.getConfig().getString("Database.user");
        this.password = plugin.getConfig().getString("Database.password");
    }

    @SneakyThrows
    public void connect() {
        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);

        createTables();
    }

    public void disconnect() {
        if (isConnected()) {
            hikari.close();
        }
    }

    @SneakyThrows
    private void createTables(){
        PreparedStatement player_data = plugin.getMySQL().getHikari().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS player_stats (" +
                "uuid VARCHAR(36) PRIMARY KEY, " +
                "kills INT DEFAULT 0, " +
                "deaths INT DEFAULT 0, " +
                "killstreak INT DEFAULT 0)");
        player_data.executeUpdate();
    }

    public boolean isConnected() {
        return hikari != null && !hikari.isClosed();
    }

    public HikariDataSource getHikari() {
        return hikari;
    }
}

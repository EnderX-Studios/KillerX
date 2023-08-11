package com.enderx.killerx.database;

import lombok.SneakyThrows;

import java.sql.*;

public class DatabaseManager {


    private Connection connection;

    public DatabaseManager(String databaseUrl) throws SQLException {
        connection = DriverManager.getConnection(databaseUrl);
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Kills (PlayerID TEXT PRIMARY KEY, KillCount INTEGER DEFAULT 0)");
        statement.execute("CREATE TABLE IF NOT EXISTS Deaths (PlayerID TEXT PRIMARY KEY, DeathCount INTEGER DEFAULT 0)");
        statement.execute("CREATE TABLE IF NOT EXISTS killstreak (PlayerID TEXT PRIMARY KEY, kills INTEGER DEFAULT 0)");
    }

    @SneakyThrows
    public void incrementKillCount(String playerId) throws SQLException {
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE Kills SET KillCount = KillCount + 1 WHERE PlayerID = ?");
        updateStatement.setString(1, playerId);
        int updatedRows = updateStatement.executeUpdate();

        if (updatedRows == 0) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Kills (PlayerID, KillCount) VALUES (?, 1)");
            insertStatement.setString(1, playerId);
            insertStatement.execute();
        }
    }

    @SneakyThrows
    public void incrementDeathCount(String playerId) throws SQLException {
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE Deaths SET DeathCount = DeathCount + 1 WHERE PlayerID = ?");
        updateStatement.setString(1, playerId);
        int updatedRows = updateStatement.executeUpdate();

        if (updatedRows == 0) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Deaths (PlayerID, DeathCount) VALUES (?, 1)");
            insertStatement.setString(1, playerId);
            insertStatement.execute();
        }
    }

    public void incrementKillStreak(String playerId) throws SQLException {
        String query = "SELECT kills FROM killstreak WHERE playerId = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, playerId);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            query = "INSERT INTO killstreak (playerId, kills) VALUES (?, 1)";
            ps = connection.prepareStatement(query);
            ps.setString(1, playerId);
            ps.executeUpdate();
        } else {
            query = "UPDATE killstreak SET kills = kills + 1 WHERE playerId = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, playerId);
            ps.executeUpdate();
        }
    }

    public void resetKillStreak(String playerId) throws SQLException {
        String query = "UPDATE killstreak SET kills = 0 WHERE playerId = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, playerId);
        stmt.executeUpdate();
    }


    public int getKillCount(String playerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT KillCount FROM Kills WHERE PlayerID = ?");
        statement.setString(1, playerId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("KillCount");
        } else {
            return 0;
        }
    }

    public int getDeathCount(String playerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT DeathCount FROM Deaths WHERE PlayerID = ?");
        statement.setString(1, playerId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("DeathCount");
        } else {
            return 0;
        }
    }

    public int getKillStreak(String playerId) throws SQLException {
        String query = "SELECT kills FROM killstreak WHERE playerId = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, playerId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("kills");
        } else {
            return 0;
        }
    }

    public void close()  throws SQLException{
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

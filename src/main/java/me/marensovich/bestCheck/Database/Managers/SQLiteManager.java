package me.marensovich.bestCheck.Database.Managers;

import me.marensovich.bestCheck.BestCheck;
import me.marensovich.bestCheck.Data.CheckResult;
import me.marensovich.bestCheck.Data.CheckStatus;
import me.marensovich.bestCheck.Database.DatabaseManager;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;

public class SQLiteManager implements DatabaseManager {

    private static Connection connection;

    @Override
    public void connect() {
        try {
            File dbFile = new File(BestCheck.getInstance().getDataFolder(), "database.db");
            if (!dbFile.exists()) {
                dbFile.getParentFile().mkdirs();
            }
            String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();
            connection = DriverManager.getConnection(url);
            BestCheck.getInstance().getLogger().info("Connected to SQLite!");
            checkTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                BestCheck.getInstance().getLogger().info("SQLite connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void executeQuery(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkTables(){
        String checksTable = """
        CREATE TABLE IF NOT EXISTS checks (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            admin TEXT NOT NULL,
            adminUUID TEXT NOT NULL,
            player TEXT NOT NULL,
            playerUUID TEXT NOT NULL,
            time DATETIME NOT NULL,
            status TEXT NOT NULL,
            result TEXT NOT NULL
        )
        """;

        executeQuery(checksTable);
    }

    public static void createCheckData(String adminName,
                                 String adminUUID,
                                 String playerName,
                                 String playerUUID) throws SQLException {
        String sqlQuery = """
                        INSERT INTO checks (admin, adminUUID, player, playerUUID, time, status, result)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                        """;

        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ps.setString(1, adminName);
        ps.setString(2, adminUUID);
        ps.setString(3, playerName);
        ps.setString(4, playerUUID);
        ps.setString(5, LocalDateTime.now().toString());
        ps.setString(6, CheckStatus.IN_PROGRESS.getStatus());
        ps.setString(7, CheckResult.WAITING.getResult());
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateLastCheckByAdmin(String adminUUID, String newStatus, String newResult) throws SQLException {
        String sqlQuery = """
            UPDATE checks
            SET status = ?, result = ?
            WHERE id = (
                SELECT id FROM checks
                WHERE adminUUID = ?
                ORDER BY time DESC
                LIMIT 1
            )
            """;

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, newStatus);
            ps.setString(2, newResult);
            ps.setString(3, adminUUID);
            ps.executeUpdate();
        }
    }


}

package me.marensovich.bestCheck.Database.Managers;

import me.marensovich.bestCheck.BestCheck;
import me.marensovich.bestCheck.Data.CheckResult;
import me.marensovich.bestCheck.Data.CheckStatus;
import me.marensovich.bestCheck.Database.DatabaseManager;

import java.sql.*;

public class MySQLManager implements DatabaseManager {

    private static Connection connection;

    @Override
    public void connect() {
        String username = BestCheck.getInstance().getConfigManager().getConfig().getString("database.username");
        String password = BestCheck.getInstance().getConfigManager().getConfig().getString("database.password");
        String url = BestCheck.getInstance().getConfigManager().getConfig().getString("database.url");

        try {
            connection = DriverManager.getConnection(url, username, password);
            BestCheck.getInstance().getLogger().info("Connected to MySQL!");
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
                BestCheck.getInstance().getLogger().info("MySQL connection closed.");
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
                CREATE TABLE IF NOT EXISTS `checks` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `admin` VARCHAR(45) NOT NULL,
                  `adminUUID` VARCHAR(36) NOT NULL,
                  `player` VARCHAR(45) NOT NULL,
                  `playerUUID` VARCHAR(36) NOT NULL,
                  `time` DATETIME NOT NULL,
                  `status` ENUM("in_progress", "completed") NOT NULL,
                  `result` ENUM("banned", "released", "wait") NOT NULL,
                  PRIMARY KEY (`id`),
                  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
                ENGINE = InnoDB
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

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, adminName);
            ps.setString(2, adminUUID);
            ps.setString(3, playerName);
            ps.setString(4, playerUUID);

            String now = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ps.setString(5, now);

            ps.setString(6, CheckStatus.IN_PROGRESS.getStatus());
            ps.setString(7, CheckResult.WAITING.getResult());

            ps.executeUpdate();
        }
    }


    public static void updateLastCheckByAdmin(String adminUUID, String newStatus, String newResult) throws SQLException {
        String sqlQuery = """
            UPDATE checks
            SET status = ?, result = ?
            WHERE adminUUID = ?
            ORDER BY time DESC
            LIMIT 1
            """;

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, newStatus);
            ps.setString(2, newResult);
            ps.setString(3, adminUUID);
            ps.executeUpdate();
        }
    }


}

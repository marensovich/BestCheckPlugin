package me.marensovich.bestCheck.Database;

import me.marensovich.bestCheck.BestCheck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLManager implements DatabaseManager {

    private Connection connection;

    @Override
    public void connect() {
        String username = BestCheck.getInstance().getConfigManager().getConfig().getString("database.username");
        String password = BestCheck.getInstance().getConfigManager().getConfig().getString("database.password");
        String url = BestCheck.getInstance().getConfigManager().getConfig().getString("database.url");

        try {
            connection = DriverManager.getConnection(url, username, password);
            BestCheck.getInstance().getLogger().info("Connected to MySQL!");
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
}

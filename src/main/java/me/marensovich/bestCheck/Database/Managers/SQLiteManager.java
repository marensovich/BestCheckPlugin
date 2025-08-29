package me.marensovich.bestCheck.Database.Managers;

import me.marensovich.bestCheck.BestCheck;
import me.marensovich.bestCheck.Database.DatabaseManager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager implements DatabaseManager {

    private Connection connection;

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
}

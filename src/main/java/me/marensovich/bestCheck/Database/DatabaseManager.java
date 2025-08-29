package me.marensovich.bestCheck.Database;

import java.sql.Connection;

public interface DatabaseManager {
    void connect();
    void disconnect();
    Connection getConnection();
    void executeQuery(String query);
}

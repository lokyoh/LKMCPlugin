package com.lkmc.lkmcplugin.api.dataBase;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SQLite {
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setSharedCache(true);
        config.enableRecursiveTriggers(true);
        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl("jdbc:sqlite:plugins/LKMCPlugin/DataBase.db");
        return dataSource.getConnection();
    }

    public static void createTable(Connection connection, String tableName, String arg) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS '" + tableName + "' (" + arg + ");";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public static void delTable(Connection connection, String tableName) throws SQLException {
        String sql = "DROP TABLE '" + tableName + "';";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public static void insert(Connection connection, String tableName, String arg, String value) throws SQLException {
        String sql = "INSERT INTO '" + tableName + "' (" + arg + ") VALUES (" + value + ");";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public static void update(Connection connection, String tableName, String change, String addon){
        // addon WHERE ...
        String sql = "UPDATE '" + tableName + "' SET " + change + " " + addon + ";";
        executorService.submit(()->{
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

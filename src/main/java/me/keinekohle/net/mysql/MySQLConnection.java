package me.keinekohle.net.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static final String DATABASE = "dungeons";
    public static final String HOST = "localhost";
    public static final String TABLE_PREFIX = "dungeon_";

    private Connection connection = null;

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws SQLException {
        if(!isConnected()) connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE, USERNAME, PASSWORD);
    }


    public Connection getConnection() {
        try {
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

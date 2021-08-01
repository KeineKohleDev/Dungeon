package me.keinekohle.net.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    static final String USERNAME = "root";
    static final String PASSWORD = "";
    static final String DATABASE = "dungeons";
    static final String HOST = "localhost";

    public static final String TABLE_PREFIX = "dungeon_";

    private Connection connection = null;

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws SQLException {
        if(!isConnected()) connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?useSSL=false", USERNAME, PASSWORD);
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

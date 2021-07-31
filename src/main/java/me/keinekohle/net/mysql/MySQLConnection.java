package me.keinekohle.net.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private final String username = "root";
    private final String password = "";
    private final String database = "dungeons";
    private final String host = "localhost";

    private Connection conn = null;

    public boolean isConnected() {
        return (conn != null);
    }

    public void connect() throws SQLException {
        if(!isConnected()) {
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?useSSL=false", username, password);
        }
    }


    public Connection getConnection() {
        try {
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

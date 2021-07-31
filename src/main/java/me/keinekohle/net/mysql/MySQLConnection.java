package me.keinekohle.net.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private final String name = "db_251891";
    private final String password = "5d15434c5f";
    private final String database = "db_251891";
    private final String host = "45.93.248.120";

    private Connection conn = null;

    public boolean isConnected() {
        return (conn != null);
    }

    public void connect() throws SQLException {
        if(!isConnected()) {
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?useSSL=false", name, password);
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

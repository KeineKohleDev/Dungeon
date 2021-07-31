package me.keinekohle.net.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLMethods {

    MySQLConnection mySQLConnection = new MySQLConnection();
    private final Connection connection = mySQLConnection.getConnection();

    public String selectString(String select, String from, String where, String is) {
        String toSelect = null;
        try {
            String sql = "SELECT " + select + " FROM " + from + " WHERE " + where + "='" + is + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                toSelect = resultSet.getString(select);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSelect;
    }

}

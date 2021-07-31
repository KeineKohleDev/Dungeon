package me.keinekohle.net.mysql;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class MySQLMethods {

    MySQLConnection mySQLConnection = new MySQLConnection();
    private final Connection connection = mySQLConnection.getConnection();

    public void createDungeonPlayerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS dungeon_player (" +
                "name VARCHAR(50) null," +
                "uuid VARCHAR(50) null," +
                "coins INTEGER(11) null," +
                "last_class VARCHAR(50) null" +
                ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String selectString(String select, String table, String where, String is) {
        try {
            String sql = "SELECT " + select + " FROM " + table + " WHERE " + where + "='" + is + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString(select);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateString(String table, String set, String setIs, String where, String is) {
        try {
            String sql = "UPDATE " + table + " set " + set + "='" + setIs + "' WHERE " + where + "='" + is + "'";
            Statement statem = connection.createStatement();
            statem.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public boolean checkIsPlayerInDataBase(UUID uuid) {
        try {
            String sql = "SELECT uuid from dungeon_player WHERE uuid='" + uuid + "'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next() == true) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public void addPlayerToDataBase(Player player) {
        try {
            String sql = "INSERT into dungeon_player (name, uuid) values ('" + player.getName() + "', '" + player.getUniqueId() + "')";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

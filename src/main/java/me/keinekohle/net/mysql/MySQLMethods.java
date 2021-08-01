package me.keinekohle.net.mysql;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class MySQLMethods {

    private MySQLConnection mySQLConnection = new MySQLConnection();
    private Connection connection = mySQLConnection.getConnection();

    public void createDungeonPlayerTable() {
        Statement statement = null;
        String sql = "CREATE TABLE IF NOT EXISTS dungeon_player (" +
                "username VARCHAR(50) null," +
                "uuid VARCHAR(50) null," +
                "coins INTEGER(11) null," +
                "lastClass VARCHAR(50) null" +
                ")";
        try {
            statement = connection.createStatement();
            statement.executeQuery(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String selectString(String select, String table, String where, String is) {
        Statement statement = null;
        try {
            String sql = "SELECT " + select + " FROM " + table + " WHERE " + where + "='" + is + "'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString(select);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Integer selectInteger(String select, String table, String where, String is) {
        Statement statement = null;
        try {
            String sql = "SELECT " + select + " FROM " + table + " WHERE " + where + "='" + is + "'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt(select);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void updateString(String table, String set, String setIs, String where, String is) {
        Statement statement = null;
        try {
            String sql = "UPDATE " + table + " set " + set + "='" + setIs + "' WHERE " + where + "='" + is + "'";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public boolean checkIsPlayerInDataBase(UUID uuid) {
        Statement statement = null;
        try {
            String sql = "SELECT uuid from dungeon_player WHERE uuid='" + uuid + "'";
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;

    }

    public void addPlayerToDataBase(Player player) {
        Statement statement = null;
        try {
            String sql = "INSERT into dungeon_player (name, uuid) values ('" + player.getName() + "', '" + player.getUniqueId() + "')";
            statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

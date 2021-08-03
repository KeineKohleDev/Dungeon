package me.keinekohle.net.mysql;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class MySQLMethods {

    private final MySQLConnection mySQLConnection = new MySQLConnection();
    private final Connection connection = mySQLConnection.getConnection();

    private String whereClassNameAndSlotAndClasslevel(String classname, int classlevel, int slot) {
        return "classname='" + classname + "' AND slot='" + slot + "' AND classlevel='" + classlevel + "'";
    }

    private void closeStatementAndResultSet(Statement statement, ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }


    public String selectString(String table, String select, String where, String is) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT " + select + " FROM " + table + " WHERE " + where + "='" + is + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString(select);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return null;
    }

    public Integer selectInteger(String table, String select, String where, String is) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT " + select + " FROM " + table + " WHERE " + where + "='" + is + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt(select);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
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
            closeStatement(statement);
        }
    }


    public boolean checkIsPlayerInDataBase(UUID uuid) {
        Statement statement = null;
        try {
            String sql = "SELECT uuid from dungeon_player WHERE uuid='" + uuid + "'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                resultSet.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }

        return false;

    }

    public void addPlayerToDataBase(Player player) {
        Statement statement = null;
        try {
            String sql = "INSERT into dungeon_player (username, uuid) values ('" + player.getName() + "', '" + player.getUniqueId() + "')";
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    public void insertClassItemstack(String className, int classLevel, int slot, String itemstackyaml) {
        Statement statement = null;
        try {
            String sql = "INSERT into dungeon_classes (classname, classlevel, slot, itemstackyaml) values ('" + className + "', '" + classLevel + "', '" + slot + "', '" + itemstackyaml + "')";
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    public void updateClassItemstack(String className, int classLevel, int slot, String itemstackyaml) {
        Statement statement = null;
        try {
            String sql = "UPDATE dungeon_classes SET itemstackyaml='" + itemstackyaml + "' WHERE " + whereClassNameAndSlotAndClasslevel(className, classLevel, slot);
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    public ItemStack selectItemFromClass(String className, int classLevel, int slot) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT itemstackyaml FROM dungeon_classes WHERE " + whereClassNameAndSlotAndClasslevel(className, classLevel, slot);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                YamlConfiguration configuration = new YamlConfiguration();
                String cfg = resultSet.getString("itemstackyaml").replace("*", "'");
                configuration.loadFromString(cfg);
                return configuration.getItemStack("i", null);
            }
        } catch (SQLException | InvalidConfigurationException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return null;
    }

    public void giveClassItems(Player player, String classname, int classlevel) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT slot FROM dungeon_classes WHERE classname='" + classname + "' AND classlevel='" + classlevel + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int slot = resultSet.getInt("slot");
                ItemStack itemStack = selectItemFromClass(classname, classlevel, slot);
                player.getInventory().setItem(slot, itemStack);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
    }

    public boolean checkIfClassExists(String className, int classLevel, int slot) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT itemstackyaml FROM dungeon_classes WHERE " + whereClassNameAndSlotAndClasslevel(className, classLevel, slot);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                resultSet.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return false;
    }
}

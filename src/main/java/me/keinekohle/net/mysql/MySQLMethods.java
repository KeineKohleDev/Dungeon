package me.keinekohle.net.mysql;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MySQLMethods {

    private final MySQLConnection mySQLConnection = new MySQLConnection();
    private final Connection connection = mySQLConnection.getConnection();

    private String whereClassNameAndSlotAndClasslevel(String classname, int classlevel, int slot) {
        return "classname='" + classname + "' AND slot='" + slot + "' AND classlevel='" + classlevel + "'";
    }

    private void closeStatementAndResultSet(Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
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
            String sql = "INSERT into dungeon_classlevels (classname, classlevel, slot, itemstackyaml) values ('" + className + "', '" + classLevel + "', '" + slot + "', '" + itemstackyaml + "')";
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    public void insertClass(String className, int classLevel, int classCoast, String classColor, String icon, String abilities) {
        Statement statement = null;
        try {
            String sql = "INSERT into dungeon_classes (classname, classlevel, classcoast, classcolor, icon, abilities) values ('" + className + "', '" + classLevel + "', '" + classCoast + "', '" + classColor + "', '" + icon + "', '" + abilities + "')";
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    public boolean checkIfClassNameExists(String className) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT classname FROM dungeon_classes WHERE classname='" + className + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return false;
    }

    public boolean checkIfClassLevelExists(String className, int classLevel) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT classlevel FROM dungeon_classes WHERE classname='" + className + "' AND classlevel='" + classLevel + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return false;
    }

    public ItemStack selectItemFromClass(String className, int classLevel, int slot) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT itemstackyaml FROM dungeon_classlevels WHERE " + whereClassNameAndSlotAndClasslevel(className, classLevel, slot);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                YamlConfiguration configuration = new YamlConfiguration();
                String cfg = resultSet.getString("itemstackyaml").replace("-|-", "'");
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
            String sql = "SELECT slot FROM dungeon_classlevels WHERE classname='" + classname + "' AND classlevel='" + classlevel + "'";
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

    public boolean checkIfClassItemStackExists(String className, int classLevel, int slot) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT itemstackyaml FROM dungeon_classlevels WHERE " + whereClassNameAndSlotAndClasslevel(className, classLevel, slot);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return false;
    }

    public List<String> selectAllClasses() {
        List<String> classes = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT classname FROM dungeon_classes ORDER  BY classcoast ASC";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (!classes.contains(resultSet.getString("classname"))) {
                    classes.add(resultSet.getString("classname"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return classes;
    }

    public List<String> selectAllBoughtClasses(Player player) {
        List<String> boughtClasses = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT classname FROM dungeon_player_classes WHERE uuid='" + player.getUniqueId() + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                boughtClasses.add(resultSet.getString("classname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return boughtClasses;
    }

    public Material selectIconFromClasses(String className) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT icon FROM dungeon_classes WHERE classname='" + className + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return Material.getMaterial(resultSet.getString("icon"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return null;
    }

    public Integer selectClassCoastFromClasses(String className, int classLevel) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT classcoast FROM dungeon_classes WHERE classname='" + className + "' AND classlevel='" + classLevel + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("classcoast");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return null;
    }

    public ChatColor selectClassColorFromClasses(String className, int classLevel) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT classcolor FROM dungeon_classes WHERE classname='" + className + "' AND classlevel='" + classLevel + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return ChatColor.of(resultSet.getString("classcolor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return null;
    }

    public String selectAbilitiesFromClasses(String className, int classLevel) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT abilities FROM dungeon_classes WHERE classname='" + className + "' AND classlevel='" + classLevel + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("abilities");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return null;
    }

    public Integer selectCoinsFromClassLevel(String className, int classLevel) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT classcoast FROM dungeon_classes WHERE classname='" + className + "' AND classlevel='" + classLevel + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("classcoast");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return null;
    }

    public Integer selectCoinsFromPlayerUUID(Player player) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT coins FROM dungeon_player WHERE uuid='" + player.getUniqueId() + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatementAndResultSet(statement, resultSet);
        }
        return null;
    }

    public void updatePlayerCoins(Player player, int coins) {
        Statement statement = null;
        try {
            String sql = "UPDATE dungeon_player SET coins='" + coins +"' WHERE uuid='" +player.getUniqueId() + "'";
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    public void giveClassAccessToPlayer(Player player, String className) {
        Statement statement = null;
        try {
            String sql = "INSERT into dungeon_player_classes (uuid, classname) values ('" + player.getUniqueId() + "', '" + className + "')";
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    public void updateClassLevelAccessToPlayer(Player player, String className, int classLevel) {
        Statement statement = null;
        try {
            String sql = "UPDATE dungeon_player_classes SET classlevel='" + classLevel +"' WHERE uuid='" +player.getUniqueId() + "' AND classname='" + className + "'";
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }




}

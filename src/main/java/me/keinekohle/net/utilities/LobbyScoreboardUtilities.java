package me.keinekohle.net.utilities;

import me.keinekohle.net.mysql.MySQLConnection;
import me.keinekohle.net.mysql.MySQLMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public final class LobbyScoreboardUtilities {

    private LobbyScoreboardUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static Integer getCoins(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        return mySQLMethods.selectInteger(MySQLConnection.TABLE_PREFIX + "player","coins", "uuid", player.getUniqueId().toString());
    }

    public static String getLastUsedClass(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        String classname = mySQLMethods.selectLastUsedClassFromPlayer(player);
        return classname;
    }
}

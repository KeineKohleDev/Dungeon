package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLConnection;
import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.entity.Player;

public class LobbyScoreboardUtilities {

    private LobbyScoreboardUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static String getLastUsedClass(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        String lastClass = mySQLMethods.selectString("lastClass", MySQLConnection.TABLE_PREFIX + "player", "uuid", player.getUniqueId().toString());
        if(lastClass != null) {
            return lastClass.toUpperCase();
        }
        return Classes.NONECLASS;
    }

    public static Integer getCoins(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        return mySQLMethods.selectInteger("coins", MySQLConnection.TABLE_PREFIX + "player", "uuid", player.getUniqueId().toString());
    }
}

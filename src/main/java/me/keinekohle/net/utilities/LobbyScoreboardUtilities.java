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
        String lastClass = mySQLMethods.selectString(MySQLConnection.TABLE_PREFIX + "player","lastClass", "uuid", player.getUniqueId().toString());
        if(lastClass != null) {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.classGotAutoSelected);
            return lastClass.toUpperCase();
          }
        return Classes.NONECLASS;
    }

    public static Integer getCoins(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        return mySQLMethods.selectInteger(MySQLConnection.TABLE_PREFIX + "player","coins", "uuid", player.getUniqueId().toString());
    }
}

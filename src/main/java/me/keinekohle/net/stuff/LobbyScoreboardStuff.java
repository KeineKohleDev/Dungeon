package me.keinekohle.net.stuff;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLConnection;
import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.entity.Player;

public class LobbyScoreboardStuff {

    public static String getLastUsedClass(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        String last_class = mySQLMethods.selectString("last_class", MySQLConnection.TABLE_PREFIX + "player", "uuid", player.getUniqueId().toString());
        if(KeineKohle.class_prefix.containsKey(last_class)) {
            return KeineKohle.class_prefix.get(last_class);
        } else {
            return "§8None";
        }
    }

    public static Integer getCoins(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        return mySQLMethods.selectInteger("coins", MySQLConnection.TABLE_PREFIX + "player", "uuid", player.getUniqueId().toString());
    }
}

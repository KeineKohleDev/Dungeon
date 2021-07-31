package me.keinekohle.net.stuff;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.entity.Player;

public class LobbyScoreboardStuff {

    public static String getLastUsedClass(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        String last_class = mySQLMethods.selectString("last_class", "dungeon_player", "uuid", player.getUniqueId().toString());
        if(KeineKohle.class_prefix.containsKey(last_class)) {
            return KeineKohle.class_prefix.get(last_class);
        } else {
            return "§8None";
        }
    }
}

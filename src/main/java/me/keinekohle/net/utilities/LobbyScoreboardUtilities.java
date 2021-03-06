package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLConnection;
import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.entity.Player;

public final class LobbyScoreboardUtilities {

    private LobbyScoreboardUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static Integer getCoins(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        return mySQLMethods.selectInteger(MySQLConnection.TABLE_PREFIX + "player", "coins", "uuid", player.getUniqueId().toString());
    }

    public static String getClassAndColor(Player player) {
        if (KeineKohle.SELECTEDCLASS.containsKey(player)) {
            ClassFabric classFabric = KeineKohle.SELECTEDCLASS.get(player);
            return classFabric.getClassColor() + classFabric.getClassName();
        }
        return GlobalUtilities.getColorByName(Classes.NONECLASS) + Classes.NONECLASS;
    }
}

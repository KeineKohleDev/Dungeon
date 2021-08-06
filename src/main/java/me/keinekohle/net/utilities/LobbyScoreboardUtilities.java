package me.keinekohle.net.utilities;

import me.keinekohle.net.mysql.MySQLConnection;
import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public final class LobbyScoreboardUtilities {

    private LobbyScoreboardUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static Integer getCoins(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        return mySQLMethods.selectInteger(MySQLConnection.TABLE_PREFIX + "player", "coins", "uuid", player.getUniqueId().toString());
    }

    public static String getLastUsedClassAndColor(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        String lastUsedClass = Classes.getLastUsedClass(player);
        if (!lastUsedClass.equals(Classes.NONECLASS)) {
            System.getLogger(Logger.GLOBAL_LOGGER_NAME).log(System.Logger.Level.INFO, lastUsedClass);
            String classColor = mySQLMethods.selectClassColorFromClasses(lastUsedClass).toString();
            if (classColor != null) {
                System.getLogger(Logger.GLOBAL_LOGGER_NAME).log(System.Logger.Level.INFO, classColor);
                return classColor + lastUsedClass;
            }
        }
        return GlobalUtilities.getColorByName(Classes.NONECLASS) + Classes.NONECLASS;
    }
}

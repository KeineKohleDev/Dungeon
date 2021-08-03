package me.keinekohle.net.utilities;

import me.keinekohle.net.mysql.MySQLConnection;
import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class Classes {

    public static final String NONECLASS = "NONE";
    public static final String TANKCLASS = "TANK";
    public static final String HEALERCLASS = "HEALER";
    public static final String SURVIVORCLASS = "SURVIVOR";
    public static final String ARCHERCLASS = "ARCHER";
    public static final String BOMBERCLASS = "BOMBER";

    public static final String SHOPCLASSES = "Classes";

    private Classes() {
        throw new IllegalStateException("Utility class");
    }

    public static String getLastUsedClass(Player player) {
        String lastClass = getLastUsedClassFromDataBase(player);
        if(lastClass != null) {
            return lastClass.toUpperCase();
        }
        return Classes.NONECLASS;
    }

    public static String getLastUsedClassFromDataBase(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        return mySQLMethods.selectString(MySQLConnection.TABLE_PREFIX + "player","lastClass", "uuid", player.getUniqueId().toString());
    }

}

package me.keinekohle.net.utilities;

import me.keinekohle.net.mysql.MySQLConnection;
import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.entity.Player;

public final class Classes {

    public static final String NONECLASS = "NONE";

    public static final String SHOPCLASSES = "Classes";

    private Classes() {
        throw new IllegalStateException("Utility class");
    }

    public static String getLastUsedClass(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        String lastClass = mySQLMethods.selectLastUsedClassFromPlayer(player);
        if(lastClass != null) {
            return lastClass;
        }
        return Classes.NONECLASS;
    }


}

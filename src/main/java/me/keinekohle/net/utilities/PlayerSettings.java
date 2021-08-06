package me.keinekohle.net.utilities;

import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class PlayerSettings {

    public static final String AUTOSELECT = "Auto select";
    public static final Boolean AUTOSELECTVALUE = true;
    private static final Material AUTOSELECTICON = Material.CHEST_MINECART;

    private PlayerSettings() {
        throw new IllegalStateException("Utility class");
    }


    public static void insertAllPlayerSettings(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        if (!mySQLMethods.checkIfSettingAlreadyExists(player, AUTOSELECT)) {
            mySQLMethods.addPlayerSettingToDataBase(player, AUTOSELECT, AUTOSELECTVALUE);
        }
    }

    public static Material getMaterialBySettingsName(String setting) {
        return switch (setting) {
            case AUTOSELECT -> AUTOSELECTICON;
            default -> Material.AIR;
        };
    }

    public static String getSettingsNameByLanguage(String setting) {
        if(setting.equals(Language.AUTOSELECTDISPLAYNAME)) return AUTOSELECT;
        return "Error translating Displayname into Name";
    }

    public static String getLanguageNameBySettingsName(String setting) {
        return switch (setting) {
            case AUTOSELECT -> Language.AUTOSELECTDISPLAYNAME;
            default -> "Error";
        };
    }


}

package me.keinekohle.net.utilities;

import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class PlayerSettings {

    public static final String AUTOSELECTCLASS = "Auto select class";
    public static final Boolean AUTOSELECTCLASSVALUE = true;
    private static final Material AUTOSELECTCLASSICON = Material.CHEST_MINECART;

    private PlayerSettings() {
        throw new IllegalStateException("Utility class");
    }


    public static void insertAllPlayerSettings(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        if (!mySQLMethods.checkIfSettingAlreadyExists(player, AUTOSELECTCLASS)) {
            mySQLMethods.addPlayerSettingToDataBase(player, AUTOSELECTCLASS, AUTOSELECTCLASSVALUE);
        }
    }

    public static Material getMaterialBySettingsName(String setting) {
        return switch (setting) {
            case AUTOSELECTCLASS -> AUTOSELECTCLASSICON;
            default -> Material.AIR;
        };
    }

    public static String getSettingsNameByLanguage(String setting) {
        if(setting.equals(Language.AUTOSELECTCLASSDISPLAYNAME)) return AUTOSELECTCLASS;
        return "Error translating Displayname into Name";
    }

    public static String getLanguageNameBySettingsName(String setting) {
        return switch (setting) {
            case AUTOSELECTCLASS -> Language.AUTOSELECTCLASSDISPLAYNAME;
            default -> "Error";
        };
    }


}

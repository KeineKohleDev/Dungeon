package me.keinekohle.net.utilities;

import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class PlayerSettings {

    private PlayerSettings() {
        throw new IllegalStateException("Utility class");
    }


    public static void insertAllPlayerSettings(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        if (!mySQLMethods.checkIfSettingAlreadyExists(player, Variables.AUTOSELECTCLASS)) {
            mySQLMethods.addPlayerSettingToDataBase(player, Variables.AUTOSELECTCLASS, Variables.AUTOSELECTCLASSVALUE);
        }
    }

    public static Material getMaterialBySettingsName(String setting) {
        return switch (setting) {
            case Variables.AUTOSELECTCLASS -> Variables.AUTOSELECTCLASSICON;
            default -> Material.AIR;
        };
    }

    public static String getSettingsNameByLanguage(String setting) {
        if(setting.equals(Language.autoSelectClassDisplayname)) return Variables.AUTOSELECTCLASS;
        return "Error translating Displayname into Name";
    }

    public static String getLanguageNameBySettingsName(String setting) {
        return switch (setting) {
            case Variables.AUTOSELECTCLASS -> Language.autoSelectClassDisplayname;
            default -> "Error";
        };
    }


}

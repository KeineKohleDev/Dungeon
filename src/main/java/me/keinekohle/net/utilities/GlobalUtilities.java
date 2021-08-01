package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class GlobalUtilities {

    private GlobalUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static String getColorByName(String name) {

        switch (name) {
            case KeineKohle.ANVILDISPLAYNAME:
                return ChatColor.of("#4815ED").toString();
            case KeineKohle.COMPARATORDISPLAYNAME:
                return ChatColor.of("#F70C24").toString();
            case KeineKohle.CHESTDISPLAYNAME:
                return ChatColor.of("#F75CCB").toString();
            case KeineKohle.DISPLAYNAME:
                return ChatColor.GOLD.toString();
            case KeineKohle.COINS:
                return ChatColor.of("#F29C1B").toString();
            case KeineKohle.BRACKETSCOLOR, Classes.NONECLASS:
                return ChatColor.DARK_GRAY.toString();
            case KeineKohle.CHATCOLOR:
                return ChatColor.of("#FCE23A").toString();
            case Classes.TANKCLASS:
                return ChatColor.of("#38373B").toString();
            case Classes.HEALERCLASS:
                return ChatColor.of("#FA6A66").toString();
            case Classes.SURVIVORCLASS:
                return ChatColor.of("#3FE334").toString();
            case Classes.ARCHERCLASS:
                return ChatColor.of("#E3914B").toString();
            case Classes.BOMBERCLASS:
                return ChatColor.of("#C73B1C").toString();
            default:
                return "getColorByName: default! for input " + name;
        }
    }

    public static String surroundWithBracketsAndColorCodes(String tosurround) {
        return getColorByName(KeineKohle.BRACKETSCOLOR) + "[" + tosurround + getColorByName(KeineKohle.BRACKETSCOLOR) + "]";
    }

    public static void debbugMode(String code) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + code);
    }

    public static String capitalizeFirstLetter(String tocapitalize) {
        return tocapitalize.substring(0, 1).toUpperCase() + tocapitalize.substring(1).toLowerCase();
    }
}

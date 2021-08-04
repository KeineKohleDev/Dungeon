package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public final class GlobalUtilities {

    private GlobalUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static String getColorByName(String name) {

        return switch (name) {
            case KeineKohle.ANVILDISPLAYNAME -> ChatColor.of("#4815ED").toString();
            case KeineKohle.COMPARATORDISPLAYNAME -> ChatColor.of("#F70C24").toString();
            case KeineKohle.CHESTDISPLAYNAME -> ChatColor.of("#F75CCB").toString();
            case KeineKohle.BOOKDISPLAYNAME -> ChatColor.of("#76B7F0").toString();
            case KeineKohle.DISPLAYNAME -> ChatColor.GOLD.toString();
            case KeineKohle.COINS -> ChatColor.of("#F29C1B").toString();
            case KeineKohle.BRACKETSCOLOR, Classes.NONECLASS -> ChatColor.DARK_GRAY.toString();
            case KeineKohle.CHATCOLOR -> ChatColor.of("#FCE23A").toString();
            case Classes.TANKCLASS -> ChatColor.of("#38373B").toString();
            case Classes.HEALERCLASS -> ChatColor.of("#FA6A66").toString();
            case Classes.SURVIVORCLASS -> ChatColor.of("#3FE334").toString();
            case Classes.ARCHERCLASS -> ChatColor.of("#E3914B").toString();
            case Classes.BOMBERCLASS -> ChatColor.of("#C73B1C").toString();
            case Classes.SHOPCLASSES -> ChatColor.of("#39F6D3").toString();
            case KeineKohle.DIFICULTYEASY -> ChatColor.of("#9DFC58").toString();
            case KeineKohle.DIFICULTYNORMAL -> ChatColor.of("#1EE62F").toString();
            case KeineKohle.DIFICULTYHARD -> ChatColor.of("#F6814C").toString();
            case KeineKohle.DIFICULTYVERYHARD -> ChatColor.of("#EB140B").toString();
            case KeineKohle.ABILITIESDISPLAYNAME -> ChatColor.of("#BEF679").toString();
            case Abilites.FIRE -> ChatColor.of("#D9211A").toString();
            case Abilites.FIREGRENADE -> ChatColor.of("#DB4944").toString();
            case Abilites.EARTH -> ChatColor.of("#6AF22E").toString();
            case Abilites.HELL -> ChatColor.of("#E8322A").toString();
            case Abilites.C4 -> ChatColor.of("#DB1401").toString();
            case Abilites.GRENADE -> ChatColor.of("#CACBEB").toString();
            case Abilites.FREEZEGRANDE -> ChatColor.of("#55B7FA").toString();
            case Abilites.FREEZE -> ChatColor.of("#4B7CDE").toString();
            case Abilites.HEALBEACON -> ChatColor.of("#DE615D").toString();
            case Abilites.HEALGRENADE -> ChatColor.of("#F58573").toString();
            case Abilites.WEAKNESS -> ChatColor.of("#B6BCBF").toString();
            case Abilites.WWEAKNESSGRANDE -> ChatColor.of("#DDE5E9").toString();
            default -> "getColorByName: default! for input " + name;
        };
    }

    public static String getNameWithoutColorCode(String colorName) {
        return colorName.substring(14);
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

    public static boolean isNumeric(String toBeChecked) {
        try {
            Integer.parseInt(toBeChecked);
        } catch (NumberFormatException exception) {
            return false;
        }

        return true;
    }
}

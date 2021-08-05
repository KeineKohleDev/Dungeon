package me.keinekohle.net.utilities;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Replacements {

    private Replacements() {
        throw new IllegalStateException("Utility class");
    }

    public static String replaceHexColors(String toReplace) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(toReplace);
        while (match.find()) {
            String color = toReplace.substring(match.start(), match.end());
            toReplace = toReplace.replace(color, ChatColor.of(color).toString());
            match = pattern.matcher(toReplace);
        }
        return toReplace;
    }

    public static String replacePlayerName(String toReplace, Player player) {
        return toReplace.replace("%player%", player.getName());
    }

    public static String replaceVoteDifficulty(String toReplace, String difficulty) {
        return toReplace.replace("%vote%", difficulty);
    }

    public static String replaceClassName(String toReplace, String className) {
        return toReplace.replace("%classname%", className);
    }

    public static String replaceFromLevel(String toReplace, int fromLevel) {
        return toReplace.replace("%fromlevel%", String.valueOf(fromLevel));
    }

    public static String replaceToLevel(String toReplace, int toLevel) {
        return toReplace.replace("%tolevel%", String.valueOf(toLevel));
    }

    public static String replaceCoins(String toReplace, int coins) {
        return toReplace.replace("%coins%", String.valueOf(coins));
    }

    public static String replaceClassLevel(String toReplace, int classLevel) {
        return toReplace.replace("%classlevel", String.valueOf(classLevel));
    }

    public static String replaceMessage(String toReplace, String msg) {
        return toReplace.replace("%msg%", msg);
    }
}

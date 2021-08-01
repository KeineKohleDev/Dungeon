package me.keinekohle.net.stuff;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replacements {

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
        return toReplace.replaceAll("%player%", player.getName());
    }

    public static String replaceMessage(String toReplace, String msg) {
        return toReplace.replaceAll("%msg%", msg);
    }
}

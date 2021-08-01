package me.keinekohle.net.stuff;

import me.keinekohle.net.main.KeineKohle;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;

public class Stuff {

    public static String getColorByName(String name) {

        switch (name) {
            case KeineKohle.ANVIL_DISPLAYNAME:
                return ChatColor.of("#4815ED").toString();
            case KeineKohle.COMPARATOR_DISPLAYNAME:
                return ChatColor.of("#F70C24").toString();
            case KeineKohle.CHEST_DISPLAYNAME:
                return ChatColor.of("#F75CCB").toString();
            case KeineKohle.DISPLAYNAME:
                return ChatColor.GOLD.toString();
            case KeineKohle.COINS:
                return ChatColor.of("#F29C1B").toString();
            case "BRACKETS":
                return ChatColor.DARK_GRAY.toString();
            case "CHAT":
                return ChatColor.of("#FCE23A").toString();
            default:
                return "getColorByName: default! for input " + name;
        }
    }

    public static String surroundWithBracketsAndColorCodes(String tosurround) {
        return getColorByName("BRACKETS") + "[" + tosurround + getColorByName("BRACKETS") + "]";
    }

    public static void debbugMode(String code) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + code);
    }
}

package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.cmd.CmdDungeonUtilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            case Classes.SHOPCLASSES-> ChatColor.of("#39F6D3").toString();
            case KeineKohle.DIFFICULTYEASY -> ChatColor.of("#9DFC58").toString();
            case KeineKohle.DIFFICULTYNORMAL -> ChatColor.of("#1EE62F").toString();
            case KeineKohle.DIFFICULTYHARD -> ChatColor.of("#F6814C").toString();
            case KeineKohle.DIFFICULTYVERYHARD -> ChatColor.of("#EB140B").toString();
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
            case KeineKohle.SHOPBOOSTER -> ChatColor.of("#E58AEB").toString();
            case KeineKohle.SHOPTITLES -> ChatColor.of("#96DDF5").toString();
            case CmdDungeonUtilities.BUILDMODE ->  ChatColor.of("#9EFF40").toString();
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

    public static Integer calculateInventorySize(int size) {
        return (int) (9 * (Math.ceil(size / 9) + 1));
    }

    public static boolean isNumeric(String toBeChecked) {
        try {
            Integer.parseInt(toBeChecked);
        } catch (NumberFormatException exception) {
            return false;
        }

        return true;
    }

    public static void inventoryClickSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
    }

    public static void previewClass(Player player, ItemStack clickedItem, int classLevel) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        KeineKohle.INPRIVIEW.add(player);
        PlayerUtilities.clearPlayerInventory(player);
        mySQLMethods.giveClassItems(player, GlobalUtilities.getNameWithoutColorCode(clickedItem.getItemMeta().getDisplayName()), classLevel);
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.PREVIEWSTART);
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, 1);
    }
}

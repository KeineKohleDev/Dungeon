package me.keinekohle.net.utilities.setup;

import com.connorlinfoot.titleapi.TitleAPI;
import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.ClassFabric;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.InventoryUtilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CreateNewClassStages {

    private CreateNewClassStages() {
        throw new IllegalStateException("Utility class");
    }

    public static void handleStageClassName(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        MySQLMethods mySQLMethods = new MySQLMethods();
        if (mySQLMethods.checkIfClassNameExists(message)) {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: This class name is already used!");
        } else {
            classFabric.setClassName(message);
            classFabric.setClassLevel(1);
            GlobalStages.prepareNextStage(player, classFabric);
            TitleAPI.sendTitle(player, 20*1, 20*2, 20*1, GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + "Please enter the name of the", "§l§aServer group§r");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please enter the name of the §l§aserver group§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "that should be able to purchase this class.");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: Type 'next'  to make this class available to everyone!");
        }
    }

    public static void handleStageClassGroup(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        if (!message.equalsIgnoreCase("next")) {
            classFabric.setServerGroup(message);
        }
        GlobalStages.prepareNextStage(player,classFabric);
        TitleAPI.sendTitle(player, 20*1, 20*2, 20*1, GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + "Please enter the class", "§l§aCoast§r");
        player.sendMessage(KeineKohle.PREFIX +GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR)+" "+"Please type the §l§acoast§r"+GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR)+" "+"of the class.");
}

    public static void handleStageClassLevelCoast(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        if (GlobalUtilities.isNumeric(message)) {
            classFabric.setClassCoast(Integer.parseInt(message));
            GlobalStages.prepareNextStage(player, classFabric);
            TitleAPI.sendTitle(player, 20*1, 20*2, 20*1, GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + "Please enter the class", "§l§aColor hex code§r");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§acolor hex code§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: Please use the right format: #123456");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Here is a good website to pick hex color codes! https://color.adobe.com/de/create/color-wheel");
        } else {
            GlobalStages.messageOnlyNumbersHere(player);
        }
    }

    public static void handleStageClassColorHexCode(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(message);
        if (match.find()) {
            String color = message.substring(match.start(), match.end());
            classFabric.setClassColor(color);
            GlobalStages.prepareNextStage(player, classFabric);
            TitleAPI.sendTitle(player, 20*1, 20*2, 20*1, GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + "Please enter the class", "§l§aIcon§r");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You have selected the color: " + ChatColor.of(color) + color);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please select the §l§aicon§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "for this class.");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: Put the item into your main hand and type 'next', the item is removed afterwards!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please use the right format: #123456");

        }
    }

    public static void handleStageIcon(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        if (message.equalsIgnoreCase("next")) {
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                classFabric.setIcon(player.getInventory().getItemInMainHand().getType().toString());
                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                GlobalStages.prepareNextStage(player, classFabric);
                TitleAPI.sendTitle(player, 20*1, 20*2, 20*1, GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + "Please equip the class", "§l§aClass items§r");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please equip the §l§aclass items" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + ".");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: when you are finished, type 'next'!");
            } else {
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please select an item!");
            }
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'next'");

        }
    }

    public static void handleStageInventory(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        if (message.equalsIgnoreCase("next")) {
            classFabric.setInventory(player.getInventory());
            GlobalStages.prepareNextStage(player, classFabric);
            TitleAPI.sendTitle(player, 20*1, 20*2, 20*1, GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + "Please equip the class", "§l§aAbilities§r");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please select the §l§aclass abilities§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + ".");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: Type 'next' to open the inventory!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'next'");

        }
    }

    public static void handleStageOpenAbilitiesInventory(Player player, String message) {
        if (GlobalStages.messageBlackList(message)) return;
        if (message.equalsIgnoreCase("next")) {
            // run in main thread
            Bukkit.getScheduler().runTask(KeineKohle.getPlugin(KeineKohle.class), () -> player.openInventory(InventoryUtilities.createAbilitiesInventory(player)));
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'next'");

        }
    }
}

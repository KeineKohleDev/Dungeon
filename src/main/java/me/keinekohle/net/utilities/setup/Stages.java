package me.keinekohle.net.utilities.setup;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.CreateNewClass;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.InventoryUtilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Stages {

    private Stages() {
        throw new IllegalStateException("Utility class");
    }

    public static void handleBlacklist(Player player, String message, CreateNewClass createNewClass) {
        if (message.equalsIgnoreCase("back")) {
            goBock(player, createNewClass);
        } else if (message.equalsIgnoreCase("cancel")) {
            KeineKohle.PLAYERCREATENEWCLASS.remove(player);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You left the setup!");
        }
    }

    public static void handleStageClassName(Player player, String message, CreateNewClass createNewClass) {
        if (messageBlackList(message)) return;
        MySQLMethods mySQLMethods = new MySQLMethods();
        if (mySQLMethods.checkIfClassNameExists(message)) {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: This class name is already used!");
        } else {
            createNewClass.setClassName(message);
            prepareNextStage(player, createNewClass);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§alevel§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
            messageOnlyNumbersHere(player);
        }
    }

    public static void handleStageClassLevel(Player player, String message, CreateNewClass createNewClass) {
        if (messageBlackList(message)) return;
        if (GlobalUtilities.isNumeric(message)) {
            int classLevel = Integer.parseInt(message);
            MySQLMethods mySQLMethods = new MySQLMethods();
            if (mySQLMethods.checkIfClassLevelExists(createNewClass.getClassName(), classLevel)) {
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "This level exists already!");
            } else {
                createNewClass.setClassLevel(classLevel);
                prepareNextStage(player, createNewClass);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§acoast§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
            }
        } else {
            messageOnlyNumbersHere(player);
        }
    }


    public static void handleStageClassLevelCoast(Player player, String message, CreateNewClass createNewClass) {
        if (messageBlackList(message)) return;
        if (GlobalUtilities.isNumeric(message)) {
            createNewClass.setClassCoast(Integer.parseInt(message));
            prepareNextStage(player, createNewClass);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§acolor hex code§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: Please use the right format: #123456");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Here is a good website to pick hex color codes! https://color.adobe.com/de/create/color-wheel");
        } else {
            messageOnlyNumbersHere(player);
        }
    }

    public static void handleStageClassColorHexCode(Player player, String message, CreateNewClass createNewClass) {
        if (messageBlackList(message)) return;
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(message);
        if (match.find()) {
            String color = message.substring(match.start(), match.end());
            createNewClass.setClassColor(color);
            prepareNextStage(player, createNewClass);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You have selected the color: " + ChatColor.of(color) + color);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please select the §l§aicon§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "for this class.");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: put the item into your main hand and type 'next', the item is removed afterwards!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please use the right format: #123456");

        }
    }

    public static void handleStageIcon(Player player, String message, CreateNewClass createNewClass) {
        if (messageBlackList(message)) return;
        if (message.equalsIgnoreCase("next")) {
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                createNewClass.setIcon(player.getInventory().getItemInMainHand().getType().toString());
                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                prepareNextStage(player, createNewClass);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please equip the §l§aclass items" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + ".");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: when you are finisched, typr 'next'!");
            } else {
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please select an item!");
            }
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'next'");

        }
    }

    public static void handleStageInventory(Player player, String message, CreateNewClass createNewClass) {
        if (messageBlackList(message)) return;
        if (message.equalsIgnoreCase("next")) {
            createNewClass.setInventory(player.getInventory());
            prepareNextStage(player, createNewClass);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please select the §l§aclass abilities§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + ".");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: type 'next' to open the inventory!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'next'");

        }
    }

    public static void handleStageOpenAbilitiesInventory(Player player, String message) {
        if (messageBlackList(message)) return;
        if (message.equalsIgnoreCase("next")) {
            // run in main thread
            Bukkit.getScheduler().runTask(KeineKohle.getPlugin(KeineKohle.class), () -> player.openInventory(InventoryUtilities.createAbilitiesInventory(player)));
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'next'");

        }
    }

    public static void handleStageSave(Player player, String message, CreateNewClass createNewClass) {
        if (messageBlackList(message)) return;
        if (message.equalsIgnoreCase("finish")) {
            createNewClass.SaveClass();
            KeineKohle.PLAYERCREATENEWCLASS.remove(player);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "The class got saved!");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You will left the setup!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'finish'");
        }
    }

    public static void prepareNextStage(Player player, CreateNewClass createNewClass) {
        nextStage(createNewClass);
        sendSpace(player);
        messageStageInfo(player, createNewClass);
    }

    public static void messageStageInfo(Player player, CreateNewClass createNewClass) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Setup stage " + (createNewClass.getStage() + 1) + " of " + createNewClass.getStageMax());
    }

    public static void nextStage(CreateNewClass createNewClass) {
        createNewClass.setStage(createNewClass.getStage() + 1);
    }

    public static void sendSpace(Player player) {
        player.sendMessage("");
    }
    public static void goBock(Player player, CreateNewClass createNewClass) {
        if (createNewClass.getStage() >= 1) {
            createNewClass.setStage(createNewClass.getStage() - 1);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You went back!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You can't go back!");
        }
    }

    public static void messageOnlyNumbersHere(Player player) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can only use numbers here!");
    }

    public static boolean messageBlackList(String message) {
        return switch (message) {
            case "back", "Back", "cancel", "Cancel" -> true;
            default -> false;
        };
    }
}

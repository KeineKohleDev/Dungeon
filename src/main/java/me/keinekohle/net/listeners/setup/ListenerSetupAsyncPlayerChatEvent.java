package me.keinekohle.net.listeners.setup;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.CreateNewClass;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.InventoryUtilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerSetupAsyncPlayerChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (KeineKohle.PLAYERCREATENEWCLASS.containsKey(player)) {
            String message = event.getMessage();
            if (message.contains(" ")) {
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You can't use white spaces!");
            } else {
                CreateNewClass createNewClass = KeineKohle.PLAYERCREATENEWCLASS.get(player);
                System.out.println("Befor: " + createNewClass.getStage());
                if (message.equalsIgnoreCase("back")) {
                    goBock(player, createNewClass);
                    System.out.println(messageBlackList(message));
                }
                int stage = createNewClass.getStage();
                System.out.println("After: " + stage);
                switch (stage) {
                    case 0:
                        handleStageClassName(player, message, createNewClass);
                        break;
                    case 1:
                        handleStageClassLevel(player, message, createNewClass);
                        break;
                    case 2:
                        handleStageClassLevelCoast(player, message, createNewClass);
                        break;
                    case 3:
                        handleStageClassColorHexCode(player, message, createNewClass);
                        break;
                    case 4:
                        handleStageRepresentativeItem(player, message, createNewClass);
                        break;
                    case 5:
                        handleStageInventory(player, message, createNewClass);
                        break;
                    case 6:
                        handleStageOpenAbilitiesInventory(player, message);
                        break;
                    default:
                        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Error: please restart the setup!");
                        break;
                }
            }
            event.setCancelled(true);
        }
    }

    private void handleStageClassName(Player player, String message, CreateNewClass createNewClass) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        if (!messageBlackList(message)) {
            if (mySQLMethods.checkIfClassNameExists(message)) {
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: This class name is already used!");
            } else {
                createNewClass.setClassName(message);
                prepareNextStage(player, createNewClass);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§alevel§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
                messageOnlyNumbersHere(player);
            }
        }
    }

    private void handleStageClassLevel(Player player, String message, CreateNewClass createNewClass) {
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


    private void handleStageClassLevelCoast(Player player, String message, CreateNewClass createNewClass) {
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

    private void handleStageClassColorHexCode(Player player, String message, CreateNewClass createNewClass) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(message);
        if (match.find()) {
            createNewClass.setClassColor(message);
            prepareNextStage(player, createNewClass);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You have selected the color: " + ChatColor.of(message) + message);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please select the §l§arepresentative item§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "for this class.");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: put the item into your main hand and type 'next', the item is removed afterwards!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please use the right format: #123456");

        }
    }

    private void handleStageRepresentativeItem(Player player, String message, CreateNewClass createNewClass) {
        if (message.equalsIgnoreCase("next")) {
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                createNewClass.setRepresentativeItem(player.getInventory().getItemInMainHand().getType().toString());
                player.getInventory().remove(player.getInventory().getItemInMainHand());
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

    private void handleStageInventory(Player player, String message, CreateNewClass createNewClass) {
        if (message.equalsIgnoreCase("next")) {
            createNewClass.setInventory(player.getInventory());
            prepareNextStage(player, createNewClass);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please select the §l§aclass abilities§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + ".");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: type 'next' to open the inventory!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'next'");

        }
    }

    private void handleStageOpenAbilitiesInventory(Player player, String message) {
        if (message.equalsIgnoreCase("next")) {
            // run in main thread
            Bukkit.getScheduler().runTask(KeineKohle.main, () -> {
                player.openInventory(InventoryUtilities.createAbilitiesInventory(player));
            });
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'next'");

        }
    }

    private void prepareNextStage(Player player, CreateNewClass createNewClass) {
        nextStage(createNewClass);
        sendSpace(player);
        messageStageInfo(player, createNewClass);
    }

    public static void messageStageInfo(Player player, CreateNewClass createNewClass) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Setup stage " + (createNewClass.getStage() + 1) + " of " + createNewClass.getStageMax());
    }

    private void nextStage(CreateNewClass createNewClass) {
        createNewClass.setStage(createNewClass.getStage() + 1);
    }

    private void sendSpace(Player player) {
        player.sendMessage("");
    }

    private void goBock(Player player, CreateNewClass createNewClass) {
        if (createNewClass.getStage() >= 1) {
            createNewClass.setStage(createNewClass.getStage() - 1);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You went back!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You can't go back!");
        }
    }

    private void messageOnlyNumbersHere(Player player) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can only use numbers here!");
    }

    private boolean messageBlackList(String message) {
        return switch (message) {
            case "back", "Back" -> true;
            default -> false;
        };
    }
}

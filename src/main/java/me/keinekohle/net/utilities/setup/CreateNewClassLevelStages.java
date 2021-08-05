package me.keinekohle.net.utilities.setup;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.ClassFabric;
import me.keinekohle.net.utilities.GlobalUtilities;
import org.bukkit.entity.Player;

public class CreateNewClassLevelStages {

    private CreateNewClassLevelStages() {
        throw new IllegalStateException("Utility class");
    }

    public static void handleStageCheckClassName(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        MySQLMethods mySQLMethods = new MySQLMethods();
        if (mySQLMethods.checkIfClassNameExists(message)) {
            classFabric.setClassName(message);
            classFabric.setClassLevel(mySQLMethods.selectHighestClassLevelFromClasses(message)+1);
            GlobalStages.prepareNextStage(player, classFabric);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§acoast§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "This class doesn't exists!");
        }
    }

    public static void handleStageClassCoast(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        if (GlobalUtilities.isNumeric(message)) {
            classFabric.setClassCoast(Integer.parseInt(message));
            GlobalStages.prepareNextStage(player, classFabric);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please equip the §l§aclass items" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + ".");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: To get the items of the previous class type 'last'!");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: when you are finished, type 'next'!");
        } else {
            GlobalStages.messageOnlyNumbersHere(player);
        }
    }

    public static void handleStageInventory(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        switch (message) {
            case "next":
                classFabric.setInventory(player.getInventory());
                GlobalStages.prepareNextStage(player, classFabric);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "To save the class, type 'finish'!");
                break;
            case "last":
                MySQLMethods mySQLMethods = new MySQLMethods();
                mySQLMethods.giveClassItems(player, classFabric.getClassName(), mySQLMethods.selectHighestClassLevelFromClasses(classFabric.getClassName()));
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You have received the items of the last class level!");
                break;
            default:
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'next' or 'last'");
                break;
        }
    }

}

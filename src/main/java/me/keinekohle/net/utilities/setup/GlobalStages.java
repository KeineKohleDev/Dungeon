package me.keinekohle.net.utilities.setup;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.ClassFabric;
import me.keinekohle.net.utilities.GlobalUtilities;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GlobalStages {

    private GlobalStages() {
        throw new IllegalStateException("Utility class");
    }

    public static void prepareNextStage(Player player, ClassFabric classFabric) {
        nextStage(classFabric);
        sendSpace(player);
        messageStageInfo(player, classFabric);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
    }

    public static void messageStageInfo(Player player, ClassFabric classFabric) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Setup stage " + (classFabric.getStage() + 1) + " of " + classFabric.getStageMax());
    }

    public static void nextStage(ClassFabric classFabric) {
        classFabric.setStage(classFabric.getStage() + 1);
    }

    public static void sendSpace(Player player) {
        player.sendMessage("");
    }
    public static void goBock(Player player, ClassFabric classFabric) {
        if (classFabric.getStage() >= 1) {
            classFabric.setStage(classFabric.getStage() - 1);
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

    public static void handleStageSave(Player player, String message, ClassFabric classFabric) {
        if (GlobalStages.messageBlackList(message)) return;
        if (message.equalsIgnoreCase("finish")) {
            classFabric.SaveClass();
            KeineKohle.SETUPMODE.remove(player);
            player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1, classFabric.getStage());
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "The class got saved!");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You left the setup mode!");
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type 'finish'");
        }
    }

    public static void handleBlacklist(Player player, String message, ClassFabric classFabric) {
        if (message.equalsIgnoreCase("back")) {
            GlobalStages.goBock(player, classFabric);
        } else if (message.equalsIgnoreCase("cancel")) {
            KeineKohle.SETUPMODE.remove(player);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You left the setup!");
        }
    }
}

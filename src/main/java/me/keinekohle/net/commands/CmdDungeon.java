package me.keinekohle.net.commands;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.ClassFabric;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.PlayerUtilities;
import me.keinekohle.net.utilities.setup.GlobalStages;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdDungeon implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission(KeineKohle.PERMISSIONPREFIX + "*")) {
                switch (args.length) {
                    case 1 -> buildmode(args, player);
                    case 2 -> startCreateNewClassSetup(args, player);
                    case 3 -> startCreateNewClassLevelSetup(args, player);
                    default -> sendHelp(player);
                }
            } else {
                noPermissions(player);
            }
        } else {
            sender.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "This command is only for players!");
        }
        return false;
    }

    private void sendHelp(Player player) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "---- Dungeon - Help ----");
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "/dungeon <create | update> <class>");
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "/dungeon <delete> <class> <class name> <class leve | all>");
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "/dungeon <select> <class> <class name> <class leve>");
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "/dungeon buildmode (on/off)");
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "---- Dungeon - Help ----");
    }


    private void startCreateNewClassSetup(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("create") && args[1].equalsIgnoreCase("class")) {
            if (KeineKohle.SETUPMODE.containsKey(player)) {
                howToQuitTheSetupMode(player);
            } else {
                ClassFabric classFabric = new ClassFabric(player);
                classFabric.setMode(classFabric.getMODECREATENEWCLASS());
                classFabric.setStageMax(7);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§aname§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can't use white spaces!");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can type 'back', to go to the previous stage!");
            }
        }
    }

    private void startCreateNewClassLevelSetup(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("create") && args[1].equalsIgnoreCase("class") && args[2].equalsIgnoreCase("level")) {
            if (KeineKohle.SETUPMODE.containsKey(player)) {
                howToQuitTheSetupMode(player);
            } else {
                ClassFabric classFabric = new ClassFabric(player);
                classFabric.setMode(classFabric.getMODECREATENEWCLASSLEVEL());
                classFabric.setStageMax(4);
                GlobalStages.messageStageInfo(player, classFabric);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§aname§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can't use white spaces!");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can type 'back', to go to the previous stage!");
            }
        }
    }

    private void howToQuitTheSetupMode(Player player) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "If you want to quit the setup please type 'cancel' in the chat!");
    }

    private void noPermissions(Player player) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.noPermissions);
    }

    private void buildmode(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("buildmode")) {
            if (KeineKohle.BUILDMODE.contains(player)) {
                KeineKohle.BUILDMODE.remove(player);
                PlayerUtilities.clearPlayerInventory(player);
                PlayerUtilities.giveLobbyItemsToPlayer(player);
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.cmdDungeonBuildnodeOff);
            } else {
                KeineKohle.BUILDMODE.add(player);
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.cmdDungeonBuildnodeOn);
            }
        }
    }
}

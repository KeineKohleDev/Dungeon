package me.keinekohle.net.commands;

import me.keinekohle.net.listeners.setup.ListenerSetupAsyncPlayerChatEvent;
import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.CreateNewClass;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdDungeon implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission(KeineKohle.PERMISSIONPREFIX + "*")) {
                switch (args.length) {
                    case 1 -> buildmode(args, player);
                    case 2 -> startCreateNewClassSetup(args, player);
                    case 6 -> getClass(args, player);
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

    private void getClass(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("select") && args[1].equalsIgnoreCase("class") && args[2] != null && GlobalUtilities.isNumeric(args[3])) {
            MySQLMethods mySQLMethods = new MySQLMethods();
            mySQLMethods.giveClassItems(player, args[2], Integer.parseInt(args[3]));
        }
    }

    private void startCreateNewClassSetup(String[] args, Player player) {
        if(args[0].equalsIgnoreCase("create") && args[1].equalsIgnoreCase("class")) {
            if(KeineKohle.PLAYERCREATENEWCLASS.containsKey(player)) {
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "If you want to quit the setup please type 'cancel' in the chat!");
            } else {
                CreateNewClass createNewClass = new CreateNewClass();
                KeineKohle.PLAYERCREATENEWCLASS.put(player, createNewClass);
                ListenerSetupAsyncPlayerChatEvent.messageStageInfo(player, createNewClass);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§aname§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can't use white spaces!");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can type 'back', to go to the previous stage!");
            }
        }
    }

    private void savePlayerInventoryToClassLevel(String[] args, Player player, int slot, int classlevel, MySQLMethods mySQLMethods) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                YamlConfiguration configuration = new YamlConfiguration();
                configuration.set("i", itemStack);
                String itemstackYAML = configuration.saveToString().replace("'", "-|-");
                if (!mySQLMethods.checkIfClassItemStackExists(args[2], classlevel, slot)) {
                    mySQLMethods.insertClassItemstack(args[2], classlevel, slot, itemstackYAML);
                }
            }
            slot++;
        }
    }

    private void noPermissions(Player player) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.noPermissions);
    }

    private void buildmode(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("buildmode")) {
            if (KeineKohle.buildmode.contains(player)) {
                KeineKohle.buildmode.remove(player);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.cmdDungeonBuildnodeOff);
            } else {
                KeineKohle.buildmode.add(player);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.cmdDungeonBuildnodeOn);
            }
        }
    }
}

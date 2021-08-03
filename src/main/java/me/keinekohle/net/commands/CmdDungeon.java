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
                    case 1:
                        buildmode(args, player);
                        break;
                    case 2:
                        startCreateNewClassSetup(args, player);
                        break;
                    case 6:
                        createNewClass(args, player);
                        getClass(args, player);
                        break;
                    default:
                        sendHelp(player);
                        break;
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

    private void createNewClass(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("create") && args[1].equalsIgnoreCase("class") && args[2] != null && GlobalUtilities.isNumeric(args[3]) && GlobalUtilities.isNumeric(args[4]) && args[5] != null && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
            player.sendMessage("1");
            int slot = 0;
            int coast = Integer.parseInt(args[4]);
            int classlevel = Integer.parseInt(args[3]);
            String representativeItem = player.getInventory().getItemInMainHand().getType().toString();
            MySQLMethods mySQLMethods = new MySQLMethods();
            if (!mySQLMethods.checkIfClassNameExists(args[2])) {
                mySQLMethods.insertClass(args[2], classlevel, coast, args[5], representativeItem);
                player.getInventory().remove(player.getInventory().getItemInMainHand());
                savePlayerInventoryToClassLevel(args, player, slot, classlevel, mySQLMethods);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You have §acreated" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " the class §l" + ChatColor.of(args[5]) + args[2] + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " +
                 "level §l§b" + args[3] + "!");
            } else {
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "This class allready exists! Please use '/dungeon update class' insted!");
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

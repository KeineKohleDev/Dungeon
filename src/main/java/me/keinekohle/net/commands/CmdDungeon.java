package me.keinekohle.net.commands;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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
                    case 4:
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
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "---- Help ----");
    }

    private void getClass(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("select") && args[1].equalsIgnoreCase("class") && args[2] != null && GlobalUtilities.isNumeric(args[3])) {
            MySQLMethods mySQLMethods = new MySQLMethods();
            mySQLMethods.giveClassItems(player, args[2], Integer.parseInt(args[3]));
        }
    }

    private void createNewClass(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("create") && args[1].equalsIgnoreCase("class") && args[2] != null && GlobalUtilities.isNumeric(args[3])) {
            int slot = 0;
            int classlevel = Integer.parseInt(args[3]);
            MySQLMethods mySQLMethods = new MySQLMethods();
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null) {
                    updateOrInsertItemStack(args, player, slot, classlevel, mySQLMethods, itemStack);
                }
                slot++;
            }
        }
    }

    private void updateOrInsertItemStack(String[] args, Player player, int slot, int classlevel, MySQLMethods mySQLMethods, ItemStack itemStack) {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.set("i", itemStack);
        String itemstackYAML = configuration.saveToString().replace("'", "*");
        if(mySQLMethods.checkIfClassExists(args[2], classlevel, slot)) {
            player.sendMessage("ture");
            mySQLMethods.updateClassItemstack(args[2], classlevel, slot, itemstackYAML);
        } else {
            player.sendMessage("false");
            mySQLMethods.insertClassItemstack(args[2], classlevel, slot, itemstackYAML);
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

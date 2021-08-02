package me.keinekohle.net.commands;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

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
        }
    }

    private void createNewClass(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("create") && args[1].equalsIgnoreCase("class") && args[2] != null && GlobalUtilities.isNumeric(args[3])) {
            Inventory inv = player.getInventory();
            long time_all = System.currentTimeMillis();
            int slot = 0;
            MySQLMethods mySQLMethods = new MySQLMethods();
            for (ItemStack itemStack : inv.getContents()) {
                if (itemStack != null) {
                    long time = System.currentTimeMillis();
                    player.sendMessage("Befor: " + itemStack.getType());
                    Map<String, Object> serializedMap = itemStack.serialize();
                    player.sendMessage("" + serializedMap.toString());
                    Map<String, Object> deserialization = serializedMap;
                    ItemStack itemStack1 = ItemStack.deserialize(deserialization);
                    player.sendMessage("After: " + itemStack1.getType());
                    player.sendMessage("ms: " + (System.currentTimeMillis() - time));
                    mySQLMethods.insertClassItemstack(args[2], Integer.parseInt(args[3]), slot, serializedMap);
                }
                slot++;
            }
            player.sendMessage("ms all: " + (System.currentTimeMillis() - time_all));
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

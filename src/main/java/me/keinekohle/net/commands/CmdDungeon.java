package me.keinekohle.net.commands;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.StandardCharsets;

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
        if (args[0].equalsIgnoreCase("create") && args[1].equalsIgnoreCase("class") && args[2] != null && GlobalUtilities.isNumeric(args[3]) && GlobalUtilities.isNumeric(args[4]) && player.getInventory().getItem(17) != null && args[5] != null) {
            int slot = 0;
            int cost = Integer.parseInt(args[4]);
            int classlevel = Integer.parseInt(args[3]);
            String representativeItem = player.getInventory().getItem(17).getType().toString();
            MySQLMethods mySQLMethods = new MySQLMethods();
            if(!mySQLMethods.checkIfClassExists(args[2])) {
                mySQLMethods.insertClass(args[2], args[5], representativeItem);

                for (ItemStack itemStack : player.getInventory().getContents()) {
                    if(slot != 17) {
                        if (itemStack != null) {
                            YamlConfiguration configuration = new YamlConfiguration();
                            configuration.set("i", itemStack);
                            String itemstackYAML = configuration.saveToString().replace("'", "-|-");
                            if(mySQLMethods.checkIfClassItemStackExists(args[2], classlevel, slot)) {
                                mySQLMethods.insertClassItemstack(args[2], classlevel, slot, itemstackYAML);
                            }
                        }
                    }
                    slot++;
                }
            }
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

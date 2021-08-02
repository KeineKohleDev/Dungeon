package me.keinekohle.net.commands;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.GlobalUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;
import java.util.Map;

public class CmdDungeon implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            if (player.hasPermission(KeineKohle.PERMISSIONPREFIX + "*")) {
                if (args.length == 1) {
                    buildmode(args, player);

                } else if (args.length == 4) {
                    if(args[0].equalsIgnoreCase("create") && args[1].equalsIgnoreCase("class") && args[2] != null && GlobalUtilities.isNumeric(args[3])) {
                        Inventory inv = player.getInventory();
                        for(ItemStack itemStack : inv.getContents()) {
                            if(itemStack != null) {
                                player.sendMessage("Befor: " + itemStack.getType());
                                Map<String, Object> serializedMap = itemStack.serialize();
                                player.sendMessage("" + serializedMap);
                                Map<String, Object> deserialization = serializedMap;
                                ItemStack itemStack1 = ItemStack.deserialize(deserialization);
                                player.sendMessage("After: " + itemStack1.getType());
                            }
                        }
                    }
                }
            }  else {
                noPermissions(player);
            }
        } else {
            sender.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " +  "This command is only for players!");
        }
        return false;
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

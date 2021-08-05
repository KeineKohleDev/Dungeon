package me.keinekohle.net.commands;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.cmd.CmdDungeonUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdDungeon implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission(KeineKohle.PERMISSIONPREFIX + "*")) {
                CmdDungeonUtilities.createCMDDungeonInventory(player);
            } else {
                noPermissions(player);
            }
        } else {
            sender.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "This command is only for players!");
        }
        return false;
    }

    private void noPermissions(Player player) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.noPermissions);
    }

}

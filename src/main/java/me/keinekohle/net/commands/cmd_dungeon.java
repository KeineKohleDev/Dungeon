package me.keinekohle.net.commands;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.stuff.Language;
import me.keinekohle.net.stuff.Stuff;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmd_dungeon implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                if (player.hasPermission(KeineKohle.permission_prefix + "*")) {
                    buildmode(args, player);
                } else {
                    noPermissions(player);
                }
            }
        } else {
            sender.sendMessage(KeineKohle.prefix + Stuff.getColorByName("CHAT") + " " +  "This command is only for players!");
        }
        return false;
    }

    private void noPermissions(Player player) {
        player.sendMessage(KeineKohle.prefix + Stuff.getColorByName("CHAT") + " " + Language.no_permissions);
    }

    private void buildmode(String[] args, Player player) {
        if (args[0].equalsIgnoreCase("buildmode")) {
            if (KeineKohle.buildmode.contains(player)) {
                KeineKohle.buildmode.remove(player);
                player.sendMessage(KeineKohle.prefix + Stuff.getColorByName("CHAT") + " " + Language.cmd_dungeon_buildmode_off);
            } else {
                KeineKohle.buildmode.add(player);
                player.sendMessage(KeineKohle.prefix + Stuff.getColorByName("CHAT") + " " + Language.cmd_dungeon_buildmode_on);
            }
        }
    }
}

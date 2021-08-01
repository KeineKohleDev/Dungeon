package me.keinekohle.net.commands.TabCompleter;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabComplet_cmd_dungeon implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabComplete = new ArrayList<String>();
        if(command.getName().equalsIgnoreCase("dungeon")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    if (player.hasPermission(KeineKohle.permission_prefix + "*")) {
                        tabComplete.add("buildmode");
                    }
                }
            }
        }
        return tabComplete;
    }
}

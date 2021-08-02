package me.keinekohle.net.commands.tabcompleter;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompletCmdDungeon implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        if(command.getName().equalsIgnoreCase("dungeon")) {
            if(sender instanceof Player player && player.hasPermission(KeineKohle.PERMISSIONPREFIX + "*")) {
                if (args.length == 1) {
                    tabComplete.add("buildmode");
                    tabComplete.add("create");
                } else if(args.length == 2) {
                    tabComplete.add("class");
                } else if(args.length == 3) {

                } else if(args.length == 4) {
                    for(int i = 0; i < 6; i++) {
                        tabComplete.add(String.valueOf(i));
                    }
                }
            }
        }
        return tabComplete;
    }
}

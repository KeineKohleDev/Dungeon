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
        if (command.getName().equalsIgnoreCase("dungeon") && sender instanceof Player player && player.hasPermission(KeineKohle.PERMISSIONPREFIX + "*")) {
            switch (args.length) {
                case 1:
                    tabComplete.add("buildmode");
                    tabComplete.add("create");
                    tabComplete.add("select");
                    break;
                case 2:
                    tabComplete.add("class");
                    break;
                case 4:
                    outPutPossibleClassLevels(tabComplete);
                    break;
                default:
                    break;
            }
        }
        return tabComplete;
    }

    private void outPutPossibleClassLevels(List<String> tabComplete) {
        for (int i = 1; i < 7; i++) {
            tabComplete.add(String.valueOf(i));
        }
    }
}

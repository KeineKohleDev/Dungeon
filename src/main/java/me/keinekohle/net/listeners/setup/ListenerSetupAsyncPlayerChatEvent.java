package me.keinekohle.net.listeners.setup;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.CreateNewClass;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.InventoryUtilities;
import me.keinekohle.net.utilities.setup.Stages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerSetupAsyncPlayerChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (KeineKohle.PLAYERCREATENEWCLASS.containsKey(player)) {
            String message = event.getMessage();
            if (message.contains(" ")) {
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You can't use white spaces!");
            } else {
                CreateNewClass createNewClass = KeineKohle.PLAYERCREATENEWCLASS.get(player);
                Stages.handleBlacklist(player, message, createNewClass);
                int stage = createNewClass.getStage();
                switch (stage) {
                    case 0 -> Stages.handleStageClassName(player, message, createNewClass);
                    case 1 -> Stages.handleStageClassLevel(player, message, createNewClass);
                    case 2 -> Stages.handleStageClassLevelCoast(player, message, createNewClass);
                    case 3 -> Stages.handleStageClassColorHexCode(player, message, createNewClass);
                    case 4 -> Stages.handleStageIcon(player, message, createNewClass);
                    case 5 -> Stages.handleStageInventory(player, message, createNewClass);
                    case 6 -> Stages.handleStageOpenAbilitiesInventory(player, message);
                    case 7 -> Stages.handleStageSave(player, message, createNewClass);
                    default -> player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Error: please restart the setup!");
                }
            }
            event.setCancelled(true);
        }
    }
}

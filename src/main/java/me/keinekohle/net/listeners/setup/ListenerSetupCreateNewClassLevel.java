package me.keinekohle.net.listeners.setup;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.ClassFabric;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.setup.CreateNewClassLevelStages;
import me.keinekohle.net.utilities.setup.CreateNewClassStages;
import me.keinekohle.net.utilities.setup.GlobalStages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerSetupCreateNewClassLevel implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (KeineKohle.SETUPMODE.containsKey(player)) {
            ClassFabric classFabric = KeineKohle.SETUPMODE.get(player);
            if (classFabric.getMode().equals(classFabric.getMODECREATENEWCLASSLEVEL())) {
                String message = event.getMessage();
                if (message.contains(" ")) {
                    player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You can't use white spaces!");
                } else {
                    GlobalStages.handleBlacklist(player, message, classFabric);
                    int stage = classFabric.getStage();
                    switch (stage) {
                        case 0 -> CreateNewClassLevelStages.handleStageClassCoast(player, message, classFabric);
                        case 1 -> CreateNewClassLevelStages.handleStageInventory(player, message, classFabric);
                        case 2 -> GlobalStages.handleStageSave(player, message, classFabric);
                        default -> player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Error: please restart the setup!");
                    }
                }
                event.setCancelled(true);
            }
        }
    }
}


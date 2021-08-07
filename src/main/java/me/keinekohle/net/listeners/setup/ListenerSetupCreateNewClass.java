package me.keinekohle.net.listeners.setup;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.ClassFabric;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.setup.CreateNewClassStages;
import me.keinekohle.net.utilities.setup.GlobalStages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerSetupCreateNewClass implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (KeineKohle.SETUPMODE.containsKey(player)) {
            ClassFabric classFabric = KeineKohle.SETUPMODE.get(player);
            if (classFabric.getMode().equals(classFabric.getMODECREATENEWCLASS())) {
                String message = event.getMessage();
                if (message.contains(" ")) {
                    player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "You can't use white spaces!");
                } else {
                    GlobalStages.handleBlacklist(player, message, classFabric);
                    int stage = classFabric.getStage();
                    switch (stage) {
                        case 0 -> CreateNewClassStages.handleStageClassName(player, message, classFabric);
                        case 1 -> CreateNewClassStages.handleStageClassGroup(player, message, classFabric);
                        case 2 -> CreateNewClassStages.handleStageClassLevelCoast(player, message, classFabric);
                        case 3 -> CreateNewClassStages.handleStageClassColorHexCode(player, message, classFabric);
                        case 4 -> CreateNewClassStages.handleStageIcon(player, message, classFabric);
                        case 5 -> CreateNewClassStages.handleStageInventory(player, message, classFabric);
                        case 6 -> CreateNewClassStages.handleStageOpenAbilitiesInventory(player, message);
                        case 7 -> GlobalStages.handleStageSave(player, message, classFabric);
                        default -> player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "Error: please restart the setup!");
                    }
                }
                event.setCancelled(true);
            }
        }
    }
}

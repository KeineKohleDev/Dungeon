package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.PlayerUtilities;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
public class ListenerLobbyPlayerMoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!KeineKohle.INGAME && KeineKohle.INPRIVIEW.contains(event.getPlayer())) {
            if (movedX(event) || movedZ(event)) {
                Player player = event.getPlayer();
                PlayerUtilities.clearPlayerInventory(player);
                PlayerUtilities.giveLobbyItemsToPlayer(player);
                KeineKohle.INPRIVIEW.remove(player);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.PREVIEWEND);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
            }
        }
    }

    private boolean movedZ(PlayerMoveEvent event) {
        return event.getFrom().getBlockZ() != event.getTo().getBlockZ();
    }

    private boolean movedX(PlayerMoveEvent event) {
        return event.getFrom().getBlockX() != event.getTo().getBlockX();
    }
}

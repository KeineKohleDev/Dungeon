package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.PlayerUtilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerLobbyPlayerMoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(!KeineKohle.INGAME) {
            Player player = event.getPlayer();
            if (KeineKohle.INPRIVIEW.contains(player)) {
                if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
                    PlayerUtilities.clearPlayerInventory(player);
                    PlayerUtilities.giveLobbyItemsToPlayer(player);
                    KeineKohle.INPRIVIEW.remove(player);
                    player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Preview ended because you moved!");
                }
            }
        }
    }
}

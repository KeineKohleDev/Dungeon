package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.Classes;
import me.keinekohle.net.utilities.GlobalUtilities;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ListenerLobbyInventoryCloseEvent implements Listener {

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if(!KeineKohle.INGAME && event.getPlayer() instanceof Player player) {
            if(event.getView().getTitle().equals(GlobalUtilities.getColorByName(KeineKohle.CHESTDISPLAYNAME) + KeineKohle.CHESTDISPLAYNAME) || event.getView().getTitle().equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES)) {
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1, 1);
            }
        }
    }
}

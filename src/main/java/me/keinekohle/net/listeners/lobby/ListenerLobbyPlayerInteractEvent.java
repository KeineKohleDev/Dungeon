package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.InventoryUtilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerLobbyPlayerInteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if(!KeineKohle.INGAME) {
            Player player = event.getPlayer();
            if(event.getItem() != null && checkAction(event) && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName()) {
                String itemDisplayName = event.getItem().getItemMeta().getDisplayName();

                if(itemDisplayName.equals(GlobalUtilities.getColorByName(KeineKohle.CHESTDISPLAYNAME) + KeineKohle.CHESTDISPLAYNAME)) {
                    player.openInventory(InventoryUtilities.createShopInventroy(player));
                } else if(itemDisplayName.equals(GlobalUtilities.getColorByName(KeineKohle.COMPARATORDISPLAYNAME) + KeineKohle.COMPARATORDISPLAYNAME)) {
                    player.openInventory(InventoryUtilities.createDifficultyInventroy(player));
                }
            }
        }
    }

    private boolean checkAction(PlayerInteractEvent event) {
        return event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR;
    }
}

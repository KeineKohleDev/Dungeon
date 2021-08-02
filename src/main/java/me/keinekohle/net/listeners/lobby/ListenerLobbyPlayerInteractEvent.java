package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.InventoryUtilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerLobbyPlayerInteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if(!KeineKohle.inGame) {
            Player player = event.getPlayer();
            player.sendMessage("1");
            if(event.getItem() != null && checkAction(event) && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName()) {
                ItemStack itemInHand = event.getItem();
                player.sendMessage("2");
                if(itemInHand.getItemMeta().getDisplayName().equals(GlobalUtilities.getColorByName(KeineKohle.CHESTDISPLAYNAME) + KeineKohle.CHESTDISPLAYNAME)) {
                    player.sendMessage("3");
                    player.openInventory(InventoryUtilities.createShopInventroy(player));
                }
            }
        }
    }

    private boolean checkAction(PlayerInteractEvent event) {
        return event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR;
    }
}

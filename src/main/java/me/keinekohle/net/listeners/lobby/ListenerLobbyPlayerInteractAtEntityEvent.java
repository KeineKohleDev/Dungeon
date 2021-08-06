package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.*;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ListenerLobbyPlayerInteractAtEntityEvent implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand armorStand) {
            Player player = event.getPlayer();
            if(armorStand.getCustomName().equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Language.ARMORSTANDNAME)) {
                event.setCancelled(true);
                ClassSeletionArmorStand.openInventory(player);
            }
        }
    }

}

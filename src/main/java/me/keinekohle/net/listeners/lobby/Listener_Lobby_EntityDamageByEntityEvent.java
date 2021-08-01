package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Listener_Lobby_EntityDamageByEntityEvent implements Listener {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player || event.getEntity() instanceof ArmorStand) {
            if(!KeineKohle.inGame) {
                event.setCancelled(true);
            }
        }
    }
}

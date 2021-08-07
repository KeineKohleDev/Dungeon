package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerLobbyEntityDamageEvent implements Listener {

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (!KeineKohle.inGame && event.getEntity() instanceof Player || event.getEntity() instanceof ArmorStand)
            event.setCancelled(true);
    }
}

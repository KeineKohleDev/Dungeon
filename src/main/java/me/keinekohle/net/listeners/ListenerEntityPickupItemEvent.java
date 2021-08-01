package me.keinekohle.net.listeners;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ListenerEntityPickupItemEvent implements Listener {

    @EventHandler
    public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player && !KeineKohle.buildmode.contains(player)) event.setCancelled(true);

    }

}

package me.keinekohle.net.listeners;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.stuff.Stuff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class Listener_EntityPickupItemEvent implements Listener {

    @EventHandler
    public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
        Stuff.debbugMode("0");
        if(event.getEntity() instanceof Player) {
            Stuff.debbugMode("1");
            Player player = (Player) event.getEntity();
            if(!KeineKohle.buildmode.contains(player)) {
                Stuff.debbugMode("2");
                event.setCancelled(true);
            }
        }
    }

}

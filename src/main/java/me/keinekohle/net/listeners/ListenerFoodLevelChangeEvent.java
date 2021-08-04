package me.keinekohle.net.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class ListenerFoodLevelChangeEvent implements Listener {

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        if(event.getEntity() instanceof Player) event.setCancelled(true);
    }
}

package me.keinekohle.net.listeners;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ListenerBlockPlaceEvent implements Listener {

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(!KeineKohle.buildmode.contains(player)) {
            event.setCancelled(true);
        }
    }
}

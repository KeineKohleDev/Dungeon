package me.keinekohle.net.listeners;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ListenerPlayerDropItemEvent implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(!KeineKohle.buildmode.contains(player)) event.setCancelled(true);
    }
}

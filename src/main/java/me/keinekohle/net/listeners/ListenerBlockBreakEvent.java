package me.keinekohle.net.listeners;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ListenerBlockBreakEvent implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(!KeineKohle.buildmode.contains(player)) {
            event.setCancelled(true);
        }
    }
}

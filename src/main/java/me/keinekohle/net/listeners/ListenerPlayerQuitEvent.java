package me.keinekohle.net.listeners;

import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.Replacements;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerQuitEvent implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(Replacements.replacePlayerName(Language.playerLeaveMessage, event.getPlayer()));
    }
}

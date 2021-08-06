package me.keinekohle.net.listeners;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.Replacements;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerQuitEvent implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        if(KeineKohle.VOTEDIFFICULTY.containsKey(event.getPlayer())) KeineKohle.VOTEDIFFICULTY.remove(event.getPlayer());
        if(KeineKohle.SELECTEDCLASS.containsKey(event.getPlayer())) KeineKohle.SELECTEDCLASS.remove(event.getPlayer());
        event.setQuitMessage(Replacements.replacePlayerName(Language.playerLeaveMessage, event.getPlayer()));
    }
}

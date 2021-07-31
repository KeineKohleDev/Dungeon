package me.keinekohle.net.listeners;

import me.keinekohle.net.scoreboard.LobbyScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener_PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        LobbyScoreboard.sendLobbyScoreboard(e.getPlayer());
    }
}

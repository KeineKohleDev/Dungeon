package me.keinekohle.net.listeners;

import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.scoreboard.LobbyScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener_PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        doDataBaseChecks(player);
        LobbyScoreboard.sendLobbyScoreboard(event.getPlayer());
    }

    private void doDataBaseChecks(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        //-- check if player uuid is in database --
        if(!mySQLMethods.checkIsPlayerInDataBase(player.getUniqueId())) {
            mySQLMethods.addPlayerToDataBase(player);

        //-- check if the player changed his name --
        } else if(!mySQLMethods.selectString("name", "dungeon_player", "uuid", player.getUniqueId().toString()).equals(player.getName())) {
            mySQLMethods.updateString("dungeon_player", "name", player.getName(), "uuid", player.getUniqueId().toString());
        }
    }
}

package me.keinekohle.net.listeners;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLConnection;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerPlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        doDataBaseChecks(player);
        PlayerUtilities.onJoin(player);
        PlayerSettings.insertAllPlayerSettings(player);
        event.setJoinMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + Replacements.replacePlayerName(Language.playerJoinMessage, player));
    }

    private void doDataBaseChecks(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        //-- check if player uuid is in database --
        if (!mySQLMethods.checkIsPlayerInDataBase(player)) {
            mySQLMethods.addPlayerToDataBase(player);

            //-- check if the player changed his name --
        } else if (!mySQLMethods.selectString(MySQLConnection.TABLE_PREFIX + "player", "username", "uuid", player.getUniqueId().toString()).equals(player.getName())) {
            mySQLMethods.updateString(MySQLConnection.TABLE_PREFIX + "player", "username", player.getName(), "uuid", player.getUniqueId().toString());
        }

        // -- check if Lobby spawn is set --
        if (!mySQLMethods.checkIfLocationAlreadyExists("Lobby")) {
            player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "Please set the Lobby spawn! use /dungeon and click on the nether start!");
        } else {
            player.teleport(mySQLMethods.selectLocationByName("Lobby"));
        }
    }
}

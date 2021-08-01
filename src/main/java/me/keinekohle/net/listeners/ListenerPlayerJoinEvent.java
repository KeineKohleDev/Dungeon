package me.keinekohle.net.listeners;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLConnection;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.scoreboard.LobbyScoreboard;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.PlayerUtilities;
import me.keinekohle.net.utilities.Replacements;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerPlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        doDataBaseChecks(player);
        LobbyScoreboard.sendLobbyScoreboard(player);
        PlayerUtilities.clearPlayerInventory(player);
        PlayerUtilities.giveLobbyItemsToPlayer(player);
        event.setJoinMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Replacements.replacePlayerName(Language.playerJoinMessage, player));
    }

    private void doDataBaseChecks(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        //-- check if player uuid is in database --
        if (!mySQLMethods.checkIsPlayerInDataBase(player.getUniqueId())) {
            mySQLMethods.addPlayerToDataBase(player);

            //-- check if the player changed his name --
        } else if (!mySQLMethods.selectString(MySQLConnection.TABLE_PREFIX + "player", "username", "uuid", player.getUniqueId().toString()).equals(player.getName())) {
            mySQLMethods.updateString(MySQLConnection.TABLE_PREFIX + "player", "username", player.getName(), "uuid", player.getUniqueId().toString());
        }
    }
}

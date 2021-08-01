package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.scoreboard.LobbyScoreboard;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class PlayerUtilities {

    private PlayerUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static void onJoin(Player player) {
        clearPlayerInventory(player);
        giveLobbyItemsToPlayer(player);
        sendLastUsedClassGotSelectedMessage(player);
        LobbyScoreboard.sendLobbyScoreboard(player);
    }

    public static void clearPlayerInventory(Player player) {
        player.getInventory().clear();
    }

    public static void giveLobbyItemsToPlayer(Player player) {
        player.getInventory().setItem(0, ItemBuilder.createItemStack(Material.CHEST, 1, GlobalUtilities.getColorByName(KeineKohle.CHESTDISPLAYNAME) + KeineKohle.CHESTDISPLAYNAME));
        player.getInventory().setItem(1, ItemBuilder.createItemStack(Material.COMPARATOR, 1, GlobalUtilities.getColorByName(KeineKohle.COMPARATORDISPLAYNAME)  + KeineKohle.COMPARATORDISPLAYNAME));
        player.getInventory().setItem(4, ItemBuilder.createItemStack(Material.ANVIL, 1, GlobalUtilities.getColorByName(KeineKohle.ANVILDISPLAYNAME) + KeineKohle.ANVILDISPLAYNAME));
        player.getInventory().setItem(9, ItemBuilder.createItemStack(Material.BOOK, 1, GlobalUtilities.getColorByName(KeineKohle.BOOKDISPLAYNAME) + KeineKohle.BOOKDISPLAYNAME));
    }

    public static void sendLastUsedClassGotSelectedMessage(Player player) {
        String lastclass = Classes.getLastUsedClass(player);
        if(lastclass != null) {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.classGotAutoSelected);
        }
    }
}

package me.keinekohle.net.stuff;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerStuff {

    Player player;

    public PlayerStuff(Player player) {
        this.player = player;
    }

    public void clearPlayerInventory() {
        player.getInventory().clear();
    }

    public void giveLobbyItemsToPlayer() {
        player.getInventory().setItem(0, ItemBuilder.createItemStack(Material.CHEST, 1, Stuff.getColorByName(KeineKohle.CHEST_DISPLAYNAME) + KeineKohle.CHEST_DISPLAYNAME));
        player.getInventory().setItem(1, ItemBuilder.createItemStack(Material.COMPARATOR, 1, Stuff.getColorByName(KeineKohle.COMPARATOR_DISPLAYNAME)  + KeineKohle.COMPARATOR_DISPLAYNAME));
        player.getInventory().setItem(4, ItemBuilder.createItemStack(Material.ANVIL, 1, Stuff.getColorByName(KeineKohle.ANVIL_DISPLAYNAME) + KeineKohle.ANVIL_DISPLAYNAME));

    }

    public void addPlayerToLobby() {

    }
}

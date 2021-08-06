package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.InventoryUtilities;
import me.keinekohle.net.utilities.PlayerSettings;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerLobbySettingsClick implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && !KeineKohle.INGAME && !KeineKohle.BUILDMODE.contains(player)) {
            if (event.getCurrentItem() != null) {
                ItemStack clickedItem = event.getCurrentItem();
                if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(KeineKohle.BOOKDISPLAYNAME) + KeineKohle.BOOKDISPLAYNAME)) {
                    event.setCancelled(true);
                    toggleSetting(player, clickedItem);
                }
            }
        }
    }

    public void toggleSetting(Player player, ItemStack clickedItem) {
        if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().hasLore()) {
            MySQLMethods mySQLMethods = new MySQLMethods();
            String settingsName = PlayerSettings.getSettingsNameByLanguage(clickedItem.getItemMeta().getDisplayName());
            if (clickedItem.getItemMeta().getLore().contains(InventoryUtilities.ON)) {
                mySQLMethods.updatePlayerSetting(player, settingsName, false);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            }
            if (clickedItem.getItemMeta().getLore().contains(InventoryUtilities.OFF)) {
                mySQLMethods.updatePlayerSetting(player, settingsName, true);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }
            InventoryUtilities.createSettingsInventory(player);
        }
    }
}

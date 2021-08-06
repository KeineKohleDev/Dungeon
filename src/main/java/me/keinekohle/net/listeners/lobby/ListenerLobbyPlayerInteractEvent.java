package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.InventoryUtilities;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerLobbyPlayerInteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (!KeineKohle.INGAME && event.getItem() != null && checkAction(event) && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName()) {
            Player player = event.getPlayer();
            String itemDisplayName = event.getItem().getItemMeta().getDisplayName();

            if (itemDisplayName.equals(GlobalUtilities.getColorByName(KeineKohle.CHESTDISPLAYNAME) + KeineKohle.CHESTDISPLAYNAME)) {
                InventoryUtilities.createShopInventroy(player);
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
            } else if (itemDisplayName.equals(GlobalUtilities.getColorByName(KeineKohle.COMPARATORDISPLAYNAME) + KeineKohle.COMPARATORDISPLAYNAME)) {
                InventoryUtilities.createDifficultyInventroy(player);
                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 1, 1);
            } else if (itemDisplayName.equals(GlobalUtilities.getColorByName(KeineKohle.ANVILDISPLAYNAME) + KeineKohle.ANVILDISPLAYNAME)) {
                InventoryUtilities.createUpgradeInventroy(player);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            } else if (itemDisplayName.equals(GlobalUtilities.getColorByName(KeineKohle.BOOKDISPLAYNAME) + KeineKohle.BOOKDISPLAYNAME)) {
                InventoryUtilities.createSettingsInventory(player);
                player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
            }
        }
    }

    private boolean checkAction(PlayerInteractEvent event) {
        return event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR;
    }
}

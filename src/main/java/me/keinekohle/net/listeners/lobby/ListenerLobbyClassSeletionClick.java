package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.scoreboard.LobbyScoreboard;
import me.keinekohle.net.utilities.ClassFabric;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.PlayerUtilities;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerLobbyClassSeletionClick implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && !KeineKohle.INGAME && !KeineKohle.BUILDMODE.contains(player)) {
            if (event.getCurrentItem() != null) {
                ItemStack clickedItem = event.getCurrentItem();
                if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(Language.ARMORSTANDNAME) + Language.ARMORSTANDNAME)) {
                    event.setCancelled(true);
                    selectClass(player, clickedItem);
                }
            }
        }
    }

    private void selectClass(Player player, ItemStack clickedItem) {
        if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().hasLore()) {
            String className = GlobalUtilities.getNameWithoutColorCode(clickedItem.getItemMeta().getDisplayName());
            if (KeineKohle.SELECTEDCLASS.containsKey(player)) {
                KeineKohle.SELECTEDCLASS.remove(player);
            }
            ClassFabric classFabric = new ClassFabric();
            classFabric.setPlayer(player);
            classFabric.setClassName(className);
            classFabric.autoFill();
            KeineKohle.SELECTEDCLASS.put(player, classFabric);
            LobbyScoreboard.sendLobbyScoreboard(player);
            PlayerUtilities.setLastSelectedClass(player, className);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You have selected the class " + clickedItem.getItemMeta().getDisplayName());
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);

        }
    }
}

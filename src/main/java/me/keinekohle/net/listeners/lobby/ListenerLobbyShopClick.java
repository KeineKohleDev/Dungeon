package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.scoreboard.LobbyScoreboard;
import me.keinekohle.net.utilities.*;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ListenerLobbyShopClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && !KeineKohle.inGame && !KeineKohle.BUILDMODE.contains(player)) {
            if (event.getCurrentItem() != null) {
                ItemStack clickedItem = event.getCurrentItem();
                if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(Variables.CHESTDISPLAYNAME) + Variables.CHESTDISPLAYNAME)) {
                    event.setCancelled(true);
                    handleShopInventorActions(player, clickedItem);
                } else if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES)) {
                    event.setCancelled(true);
                    handleClassesInventoryActions(event, player, clickedItem);
                }
            }
        }
    }

    private void handleShopInventorActions(Player player, ItemStack clickedItem) {
        if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            String itemDisplayname = clickedItemMeta.getDisplayName();
            if (itemDisplayname.equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES)) {
                InventoryUtilities.createClassesInventory(player);
                GlobalUtilities.inventoryClickSound(player);
            }
        }
    }

    private void handleClassesInventoryActions(InventoryClickEvent event, Player player, ItemStack clickedItem) {
        if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
            if (event.getClick() == ClickType.LEFT && clickedItem.getItemMeta().hasLore() && clickedItem.getItemMeta().getLore().contains(InventoryUtilities.PREVIEW)) {
                GlobalUtilities.previewClass(player, clickedItem, 1);
            } else if (event.getClick() == ClickType.RIGHT) {
                purchaseClass(player, clickedItem);
            }
        }
    }

    private void purchaseClass(Player player, ItemStack clickedItem) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        String className = GlobalUtilities.getNameWithoutColorCode(clickedItem.getItemMeta().getDisplayName());
        if (mySQLMethods.checkIfPlayerAlreadypurchasedClass(player, className)) return;
        String classServerGroup = mySQLMethods.selectClassServerGroup(className);
        if (classServerGroup != null && !player.hasPermission(KeineKohle.PERMISSIONPREFIX + classServerGroup)) {
            player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + Replacements.replaceClassServerGroup(Language.rankNeeded, classServerGroup));
            return;
        }
        int playerCoins = mySQLMethods.selectCoinsFromPlayerUUID(player);
        int classCoast = mySQLMethods.selectClassCoastFromClasses(className, 1);
        if (playerCoins >= classCoast) {
            mySQLMethods.updatePlayerCoins(player, (playerCoins - classCoast));
            mySQLMethods.giveClassAccessToPlayer(player, className);
            player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + Replacements.replaceCoins(Replacements.replaceClassName(Language.purchasedClass, className), classCoast));
            InventoryUtilities.createClassesInventory(player);
            LobbyScoreboard.sendLobbyScoreboard();
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
        } else {
            player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + Language.notEnoughCoins);
        }
    }
}

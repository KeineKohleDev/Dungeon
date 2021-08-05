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

public class ListenerLobbyUpgradeClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && !KeineKohle.INGAME && !KeineKohle.BUILDMODE.contains(player)) {
            if (event.getCurrentItem() != null) {
                ItemStack clickedItem = event.getCurrentItem();
                if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(KeineKohle.ANVILDISPLAYNAME) + KeineKohle.ANVILDISPLAYNAME)) {
                    event.setCancelled(true);
                    handleUpgradeInventorActions(event, player, clickedItem);
                }
            }
        }
    }

    private void handleUpgradeInventorActions(InventoryClickEvent event, Player player, ItemStack clickedItem) {
        if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().hasLore() && !clickedItem.getItemMeta().getLore().contains(InventoryUtilities.HIGHESTCLASS)) {
            int classLevel = Integer.parseInt(clickedItem.getItemMeta().getLore().get(0).substring(11));
            if (event.getClick() == ClickType.LEFT && clickedItem.getItemMeta().getLore().contains(InventoryUtilities.PREVIEW)) {
                GlobalUtilities.previewClass(player, clickedItem, classLevel);
            } else if (event.getClick() == ClickType.RIGHT) {
                upgradeClass(player, clickedItem, classLevel);
            }
        }
    }

    private void upgradeClass(Player player, ItemStack clickedItem, int classLevel) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        String className = GlobalUtilities.getNameWithoutColorCode(clickedItem.getItemMeta().getDisplayName());
        int playerCoins = mySQLMethods.selectCoinsFromPlayerUUID(player);
        int classCoast = mySQLMethods.selectClassCoastFromClasses(className, 1);
        if (playerCoins >= classCoast) {
            mySQLMethods.updatePlayerCoins(player, (playerCoins - classCoast));
            mySQLMethods.updateClassLevelAccessToPlayer(player, className, classLevel);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Replacements.replaceToLevel(Replacements.replaceFromLevel(Replacements.replaceCoins(Replacements.replaceClassName(Language.UPGRADEDCLASS, className), classCoast), classLevel-1), classLevel));
            InventoryUtilities.createUpgradeInventroy(player);
            LobbyScoreboard.sendLobbyScoreboard(player);
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Replacements.replaceClassName(Language.NOTENOUGHTCOINS, className));
        }
    }
}

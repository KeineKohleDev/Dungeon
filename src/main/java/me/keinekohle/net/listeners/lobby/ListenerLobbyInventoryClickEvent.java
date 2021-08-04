package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.scoreboard.LobbyScoreboard;
import me.keinekohle.net.utilities.Classes;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.InventoryUtilities;
import me.keinekohle.net.utilities.PlayerUtilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ListenerLobbyInventoryClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && !KeineKohle.INGAME && !KeineKohle.BUILDMODE.contains(player)) {
            event.setCancelled(true);
            if(event.getCurrentItem() != null) {
                ItemStack clickedItem = event.getCurrentItem();
                if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(KeineKohle.CHESTDISPLAYNAME) + KeineKohle.CHESTDISPLAYNAME)) {
                    if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
                        String itemDisplayname = clickedItemMeta.getDisplayName();
                        if (itemDisplayname.equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES)) {
                            player.openInventory(InventoryUtilities.createClassesInventory(player));
                        }
                    }
                } else if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES)) {
                    if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                        if (event.getClick() == ClickType.LEFT && clickedItem.getItemMeta().hasLore() && clickedItem.getItemMeta().getLore().contains(InventoryUtilities.PREVIEW)) {
                            MySQLMethods mySQLMethods = new MySQLMethods();
                            KeineKohle.INPRIVIEW.add(player);
                            PlayerUtilities.clearPlayerInventory(player);
                            mySQLMethods.giveClassItems(player, GlobalUtilities.getNameWithoutColorCode(clickedItem.getItemMeta().getDisplayName()), 1);
                            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "If you move, the preview will stop.");
                            player.closeInventory();
                        } else if (event.getClick() == ClickType.RIGHT) {
                            MySQLMethods mySQLMethods = new MySQLMethods();
                            String className = GlobalUtilities.getNameWithoutColorCode(clickedItem.getItemMeta().getDisplayName());
                            int playerCoins = mySQLMethods.selectCoinsFromPlayerUUID(player);
                            int classCoast = mySQLMethods.selectClassCoastFromClasses(className, 1);
                            if(playerCoins >= classCoast) {
                                mySQLMethods.updatePlayerCoins(player, (playerCoins-classCoast));
                                mySQLMethods.giveClassAccessToPlayer(player, className);
                                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You purchased the class " + clickedItem.getItemMeta().getDisplayName() + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "for " + classCoast + " coins!");
                                player.openInventory(InventoryUtilities.createClassesInventory(player));
                                LobbyScoreboard.sendLobbyScoreboard(player);
                            }
                        }
                    }
                }
            }
        }
    }
}

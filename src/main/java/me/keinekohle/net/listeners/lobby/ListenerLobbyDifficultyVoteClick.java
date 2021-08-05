package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.Replacements;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerLobbyDifficultyVoteClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && !KeineKohle.INGAME && !KeineKohle.BUILDMODE.contains(player)) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                ItemStack clickedItem = event.getCurrentItem();
                if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(KeineKohle.COMPARATORDISPLAYNAME) + KeineKohle.COMPARATORDISPLAYNAME)) {
                    if (clickedItem.hasItemMeta()) {
                        if (clickedItem.getItemMeta().getDisplayName().equals(GlobalUtilities.getColorByName(KeineKohle.DIFICULTYEASY) + KeineKohle.DIFICULTYEASY)) {
                            if (KeineKohle.VOTEDIFICULTY.containsKey(player) && !KeineKohle.VOTEDIFICULTY.get(player).equals(KeineKohle.DIFICULTYEASY)) {
                                KeineKohle.VOTEDIFICULTY.remove(player);
                                KeineKohle.VOTEDIFICULTY.put(player, GlobalUtilities.getNameWithoutColorCode(clickedItem.getItemMeta().getDisplayName()));
                                votedForMessage(player, clickedItem);
                            }
                        }
                    }
                }
            }
        }
    }

    private void votedForMessage(Player player, ItemStack clickedItem) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Replacements.replaceVoteDifficulty(Language.VOTEDFORDIFFICULTY, clickedItem.getItemMeta().getDisplayName()));
    }
}

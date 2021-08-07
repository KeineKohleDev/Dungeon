package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.*;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerLobbyDifficultyVoteClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && !KeineKohle.inGame && !KeineKohle.BUILDMODE.contains(player)) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                ItemStack clickedItem = event.getCurrentItem();
                if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(Variables.COMPARATORDISPLAYNAME) + Variables.COMPARATORDISPLAYNAME) & clickedItem.hasItemMeta() && clickedItem.hasItemMeta()) {
                    vote(player, clickedItem, Variables.DIFFICULTYEASY);
                    vote(player, clickedItem, Variables.DIFFICULTYNORMAL);
                    vote(player, clickedItem, Variables.DIFFICULTYHARD);
                    vote(player, clickedItem, Variables.DIFFICULTYVERYHARD);
                }
            }
        }
    }

    private void vote(Player player, ItemStack clickedItem, String difficulty) {
        if (clickedItem.getItemMeta().getDisplayName().equals(GlobalUtilities.getColorByName(difficulty) + difficulty)) {
            if (KeineKohle.VOTEDIFFICULTY.containsKey(player)) {
                if (!KeineKohle.VOTEDIFFICULTY.get(player).equals(difficulty)) {
                    KeineKohle.VOTEDIFFICULTY.remove(player);
                    addVoteAndSendVotedMessage(player, clickedItem, difficulty);
                    refreshInventoryAndPlaySound(player);
                }
            } else {
                addVoteAndSendVotedMessage(player, clickedItem, difficulty);
                refreshInventoryAndPlaySound(player);
            }
        }
    }

    private void refreshInventoryAndPlaySound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_SWEET_BERRY_BUSH_PLACE, 1, 1);
        InventoryUtilities.createDifficultyInventroy(player);
    }

    private void addVoteAndSendVotedMessage(Player player, ItemStack clickedItem, String difficulty) {
        KeineKohle.VOTEDIFFICULTY.put(player, difficulty);
        votedForMessage(player, clickedItem);
    }

    private void votedForMessage(Player player, ItemStack clickedItem) {
        player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + Replacements.replaceVoteDifficulty(Language.voteForDifficulty, clickedItem.getItemMeta().getDisplayName()));
    }
}

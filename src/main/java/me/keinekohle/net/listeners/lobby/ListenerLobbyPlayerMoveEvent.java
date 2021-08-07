package me.keinekohle.net.listeners.lobby;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.PlayerUtilities;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class ListenerLobbyPlayerMoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!KeineKohle.inGame) {
            Player player = event.getPlayer();
            if (KeineKohle.INPRIVIEW.contains(player)) {
                if (movedX(event) || movedZ(event)) {
                    PlayerUtilities.clearPlayerInventory(player);
                    PlayerUtilities.giveLobbyItemsToPlayer(player);
                    PlayerUtilities.clearPlayerPotionEffects(player);
                    KeineKohle.INPRIVIEW.remove(player);
                    player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + Language.previewEnd);
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                }
            }
            if (!KeineKohle.BUILDMODE.contains(player)) {
                for (Entity entity : player.getNearbyEntities(0.3, 0, 0.3)) {
                    if (entity instanceof ArmorStand armorStand) {
                        Vector vector = new Vector(player.getLocation().getX() - armorStand.getLocation().getX(), 0.5, player.getLocation().getZ() - armorStand.getLocation().getZ());
                        player.setVelocity(vector);
                        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                    }
                }
            }
        }
    }

    private boolean movedZ(PlayerMoveEvent event) {
        return event.getFrom().getBlockZ() != event.getTo().getBlockZ();
    }

    private boolean movedX(PlayerMoveEvent event) {
        return event.getFrom().getBlockX() != event.getTo().getBlockX();
    }
}

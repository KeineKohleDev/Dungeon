package me.keinekohle.net.listeners.setup;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.Abilites;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListenerSetupInventoryClickEvent implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (KeineKohle.PLAYERCREATENEWCLASS.containsKey(player)) {
                ItemStack itemStack = event.getCurrentItem();
                if (event.getClick() == ClickType.RIGHT && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore() && itemStack.getItemMeta().getLore().contains(Abilites.SELECT)) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setLore(Arrays.asList(itemMeta.getLore().get(0), Abilites.DESELECT));
                    itemStack.setItemMeta(itemMeta);
                    event.setCancelled(true);
                } else if (event.getClick() == ClickType.LEFT && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore() && itemStack.getItemMeta().getLore().contains(Abilites.DESELECT)) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setLore(Arrays.asList(itemMeta.getLore().get(0), Abilites.SELECT));
                    itemStack.setItemMeta(itemMeta);
                    event.setCancelled(true);
                }
            }
        }
    }
}

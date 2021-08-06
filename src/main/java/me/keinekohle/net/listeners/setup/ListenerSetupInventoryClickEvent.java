package me.keinekohle.net.listeners.setup;

import com.connorlinfoot.titleapi.TitleAPI;
import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.Abilites;
import me.keinekohle.net.utilities.ClassFabric;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.setup.CreateNewClassStages;
import me.keinekohle.net.utilities.setup.GlobalStages;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListenerSetupInventoryClickEvent implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && KeineKohle.SETUPMODE.containsKey(player)) {
            ClassFabric classFabric = KeineKohle.SETUPMODE.get(player);
            ItemStack itemStack = event.getCurrentItem();
            if(classFabric.getMode().equals(classFabric.getMODECREATENEWCLASS()) && classFabric.getStage() == 6 && itemStack != null) {
                if (event.getClick() == ClickType.LEFT && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore() && itemStack.getItemMeta().getLore().contains(Abilites.SELECT)) {
                    int selected = countSelectedAbilities(event);
                    addClickedAbilityMaxTwo(player, itemStack, selected);
                } else if (event.getClick() == ClickType.RIGHT && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore() && itemStack.getItemMeta().getLore().contains(Abilites.DESELECT)) {
                    deselectAbility(itemStack, player);
                }
                if(itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().equals("§aNext")) {
                    saveSelectedAbilities(event, player);
                }
                event.setCancelled(true);
            }
        }
    }

    private void saveSelectedAbilities(InventoryClickEvent event, Player player) {
        int selectedAbilities = countSelectedAbilities(event);
        List<String> classAbilities = new ArrayList<>();
        if(selectedAbilities == 2) {
            for(ItemStack itemStack : event.getInventory()) {
                if(itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore() && itemStack.getItemMeta().getLore().contains(Abilites.DESELECT)) {
                    classAbilities.add(GlobalUtilities.getNameWithoutColorCode(itemStack.getItemMeta().getDisplayName()));
                }
            }
            player.closeInventory();
            ClassFabric classFabric = KeineKohle.SETUPMODE.get(player);
            classFabric.setAbilities(classAbilities);
            player.sendMessage(classAbilities.toString());
            GlobalStages.prepareNextStage(player, classFabric);
            TitleAPI.sendTitle(player, 20*1, 20*2, 20*1, GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + "To save the class, type", "§l§a'finish'§r");
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "To save the class, type 'finish'!");
        }
    }

    private void deselectAbility(ItemStack itemStack, Player player) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(itemMeta.getLore().get(0), Abilites.SELECT));
        itemMeta.removeEnchant(Enchantment.LUCK);
        itemStack.setItemMeta(itemMeta);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
    }

    private void addClickedAbilityMaxTwo(Player player, ItemStack itemStack, int selected) {
        if (selected <= 1) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(Arrays.asList(itemMeta.getLore().get(0), Abilites.DESELECT));
            itemMeta.addEnchant(Enchantment.LUCK, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(itemMeta);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "You can only select two abilities!");
        }
    }

    private int countSelectedAbilities(InventoryClickEvent event) {
        int selected = 0;
        for (ItemStack itemStackTemp : event.getInventory().getContents()) {
            if(itemStackTemp != null) {
                ItemMeta itemMetaTemp = itemStackTemp.getItemMeta();
                if (itemMetaTemp.hasLore() && itemMetaTemp.getLore().contains(Abilites.DESELECT)) {
                    selected++;
                }
            }
        }
        return selected;
    }
}

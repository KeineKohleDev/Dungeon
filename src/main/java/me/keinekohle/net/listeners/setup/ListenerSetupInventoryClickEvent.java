package me.keinekohle.net.listeners.setup;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.utilities.Abilites;
import me.keinekohle.net.utilities.CreateNewClass;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.setup.Stages;
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
        if (event.getWhoClicked() instanceof Player player && KeineKohle.PLAYERCREATENEWCLASS.containsKey(player)) {
            CreateNewClass createNewClass = KeineKohle.PLAYERCREATENEWCLASS.get(player);
            ItemStack itemStack = event.getCurrentItem();
            if(createNewClass.getStage() == 6 && itemStack != null) {
                if (event.getClick() == ClickType.LEFT && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore() && itemStack.getItemMeta().getLore().contains(Abilites.SELECT)) {
                    int selected = countSelectedAbilities(event);
                    addClickedAbilityMaxTwo(player, itemStack, selected);
                } else if (event.getClick() == ClickType.RIGHT && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore() && itemStack.getItemMeta().getLore().contains(Abilites.DESELECT)) {
                    deselectAbility(itemStack);
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
            CreateNewClass createNewClass = KeineKohle.PLAYERCREATENEWCLASS.get(player);
            createNewClass.setAbilities(classAbilities);
            player.sendMessage(classAbilities.toString());
            Stages.prepareNextStage(player, createNewClass);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "To save the class type 'finish'!");
        }
    }

    private void deselectAbility(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(itemMeta.getLore().get(0), Abilites.SELECT));
        itemMeta.removeEnchant(Enchantment.LUCK);
        itemStack.setItemMeta(itemMeta);
    }

    private void addClickedAbilityMaxTwo(Player player, ItemStack itemStack, int selected) {
        if (selected <= 1) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(Arrays.asList(itemMeta.getLore().get(0), Abilites.DESELECT));
            itemMeta.addEnchant(Enchantment.LUCK, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(itemMeta);
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

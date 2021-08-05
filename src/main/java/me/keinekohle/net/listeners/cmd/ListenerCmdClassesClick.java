package me.keinekohle.net.listeners.cmd;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.*;
import me.keinekohle.net.utilities.cmd.CmdDungeonUtilities;
import me.keinekohle.net.utilities.setup.GlobalStages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ListenerCmdClassesClick implements Listener {

    public static final String EDIT = "§bEdit (Left click)";
    ;
    public static final String CREATENEWCLASSLEVEL = "§bCreate new class level (Right click)";
    ;
    public static final String DELETE = "§cDelete (Strg + Drop)";
    ;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && event.getCurrentItem() != null) {
            ItemStack clickedItem = event.getCurrentItem();
            player.sendMessage("3");
            if (event.getView().getTitle().equals(KeineKohle.PREFIX) && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                event.setCancelled(true);
                player.sendMessage("1");
                String displayName = clickedItem.getItemMeta().getDisplayName();
                if (displayName.equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES)) {
                    player.sendMessage("2");
                    startCreateNewClassSetup(event, player);
                    listClasses(event, player);
                } else if (displayName.equals(GlobalUtilities.getColorByName(CmdDungeonUtilities.BUILDMODE) + CmdDungeonUtilities.BUILDMODE)) {
                    toggleBuildmode(player);
                }
            } else if (event.getView().getTitle().equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES) && !clickedItem.getItemMeta().getDisplayName().equals("§a")) {
                startCreateNewClassLevelSetup(event, player);
            }
        }
    }

    private void toggleBuildmode(Player player) {
        if (KeineKohle.BUILDMODE.contains(player)) {
            KeineKohle.BUILDMODE.remove(player);
            PlayerUtilities.clearPlayerInventory(player);
            PlayerUtilities.giveLobbyItemsToPlayer(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.cmdDungeonBuildnodeOff);
        } else {
            KeineKohle.BUILDMODE.add(player);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.cmdDungeonBuildnodeOn);
        }
    }

    private void listClasses(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.RIGHT) {
            MySQLMethods mySQLMethods = new MySQLMethods();
            List<String> classes = mySQLMethods.selectAllClasses();
            if (!classes.isEmpty()) {
                Inventory inventory = Bukkit.createInventory(player, GlobalUtilities.calculateInventorySize(classes.size()), GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES);
                for (String className : classes) {
                    Material classIcon = mySQLMethods.selectIconFromClasses(className);
                    int classCoast = mySQLMethods.selectClassCoastFromClasses(className, 1);
                    ChatColor color = mySQLMethods.selectClassColorFromClasses(className);
                    List<String> abilities = Abilites.selectClassAbilities(className);
                    List<String> lore = Arrays.asList("§aPrice:§c " + classCoast, "", GlobalUtilities.getColorByName(KeineKohle.ABILITIESDISPLAYNAME) + KeineKohle.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilities.get(0)) + "  " + abilities.get(0), GlobalUtilities.getColorByName(abilities.get(1)) + "  " + abilities.get(1), "", EDIT, CREATENEWCLASSLEVEL, DELETE);
                    inventory.addItem(ItemBuilder.createItemStackWithLore(classIcon, 1, color + className, lore));
                }
                player.openInventory(inventory);
            }
        }
    }

    private void startCreateNewClassLevelSetup(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.RIGHT) {
            if (KeineKohle.SETUPMODE.containsKey(player)) {
                howToQuitTheSetupMode(player);
            } else {
                ClassFabric classFabric = new ClassFabric(player);
                classFabric.setMode(classFabric.getMODECREATENEWCLASSLEVEL());
                classFabric.setClassName(GlobalUtilities.getNameWithoutColorCode(event.getCurrentItem().getItemMeta().getDisplayName()));
                classFabric.setStageMax(4);
                GlobalStages.messageStageInfo(player, classFabric);
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§acoast§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can't use white spaces!");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can type 'back', to go to the previous stage!");
            }
        }

    }


    private void startCreateNewClassSetup(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.LEFT) {
            if (KeineKohle.SETUPMODE.containsKey(player)) {
                howToQuitTheSetupMode(player);
            } else {
                ClassFabric classFabric = new ClassFabric(player);
                classFabric.setMode(classFabric.getMODECREATENEWCLASS());
                classFabric.setStageMax(7);
                player.closeInventory();
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Please type the §l§aname§r" + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "of the class.");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can't use white spaces!");
                player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "Note: You can type 'back', to go to the previous stage!");
            }
        }
    }

    private void howToQuitTheSetupMode(Player player) {
        player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "If you want to quit the setup please type 'cancel' in the chat!");
    }
}

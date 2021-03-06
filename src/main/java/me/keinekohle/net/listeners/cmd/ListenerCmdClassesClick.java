package me.keinekohle.net.listeners.cmd;

import com.connorlinfoot.titleapi.TitleAPI;
import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.*;
import me.keinekohle.net.utilities.cmd.CmdDungeonUtilities;
import me.keinekohle.net.utilities.setup.GlobalStages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
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
    public static final String CREATENEWCLASSLEVEL = "§bCreate new class level (Right click)";
    public static final String DELETE = "§cDelete (Control + Drop)";

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && event.getCurrentItem() != null) {
            ItemStack clickedItem = event.getCurrentItem();
            String title = event.getView().getTitle();
            if (title.equals(GlobalUtilities.getColorByName(KeineKohle.DISPLAYNAME) + KeineKohle.DISPLAYNAME) && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                event.setCancelled(true);
                String displayName = clickedItem.getItemMeta().getDisplayName();
                if (displayName.equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES)) {
                    startCreateNewClassSetup(event, player);
                    listClassesWhenRightClick(event, player);
                } else if (displayName.equals(GlobalUtilities.getColorByName(CmdDungeonUtilities.BUILDMODE) + CmdDungeonUtilities.BUILDMODE)) {
                    toggleBuildmode(player);
                } else if (displayName.equals(GlobalUtilities.getColorByName(CmdDungeonUtilities.LOBBY) + CmdDungeonUtilities.LOBBY)) {
                    setLobbySpawn(event, player);
                    setClassSelectionSpawn(event, player);
                }
            } else if (title.equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + "List " + Classes.SHOPCLASSES) && !clickedItem.getItemMeta().getDisplayName().equals("§a")) {
                startCreateNewClassLevelSetup(event, player);
                deleteClass(event, player);

            }
        }

    }

    private void setLobbySpawn(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.LEFT) {
            YamlConfiguration configuration = new YamlConfiguration();
            configuration.set("loc", player.getLocation());
            String location = configuration.saveToString();
            MySQLMethods mySQLMethods = new MySQLMethods();
            if (mySQLMethods.checkIfLocationAlreadyExists("Lobby")) {
                mySQLMethods.updateLocationToDataBase("Lobby", location);
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "You have updated the Lobby spawn!");
            } else {
                mySQLMethods.addLocationToDataBase("Lobby", location);
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "You have set the Lobby spawn!");
            }
        }
    }

    private void setClassSelectionSpawn(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.RIGHT) {
            YamlConfiguration configuration = new YamlConfiguration();
            configuration.set("loc", player.getLocation());
            String location = configuration.saveToString();
            MySQLMethods mySQLMethods = new MySQLMethods();
            if (mySQLMethods.checkIfLocationAlreadyExists("ClassSelection")) {
                mySQLMethods.updateLocationToDataBase("ClassSelection", location);
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "You have updated the class selection spawn!");
            } else {
                mySQLMethods.addLocationToDataBase("ClassSelection", location);
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "You have set the class selection spawn!");
            }
            ClassSeletionArmorStand.spawnClassSelectionArmorStand();
        }
    }


    private void deleteClass(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.CONTROL_DROP) {
            MySQLMethods mySQLMethods = new MySQLMethods();
            mySQLMethods.deleteClass(GlobalUtilities.getNameWithoutColorCode(event.getCurrentItem().getItemMeta().getDisplayName()));
            player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "You have successfully deleted the class " + event.getCurrentItem().getItemMeta().getDisplayName());
            player.closeInventory();
            listClasses(player);
        }
    }

    private void toggleBuildmode(Player player) {
        if (KeineKohle.BUILDMODE.contains(player)) {
            KeineKohle.BUILDMODE.remove(player);
            PlayerUtilities.clearPlayerInventory(player);
            PlayerUtilities.giveLobbyItemsToPlayer(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "The Buildmode is now §cdisenabled!");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            player.closeInventory();
        } else {
            KeineKohle.BUILDMODE.add(player);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "The Buildmode is now §aenabled!");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            player.closeInventory();
        }
    }

    private void listClassesWhenRightClick(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.RIGHT) {
            listClasses(player);
        }
    }

    private void listClasses(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        List<String> classes = mySQLMethods.selectAllClasses();
        if (!classes.isEmpty()) {
            Inventory inventory = Bukkit.createInventory(player, GlobalUtilities.calculateInventorySize(classes.size()), GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + "List " + Classes.SHOPCLASSES);
            for (String className : classes) {
                Material classIcon = mySQLMethods.selectIconFromClasses(className);
                int classCoast = mySQLMethods.selectClassCoastFromClasses(className, 1);
                ChatColor color = mySQLMethods.selectClassColorFromClasses(className);
                List<String> abilities = Abilites.selectClassAbilities(className);
                String classServerGroup = mySQLMethods.selectClassServerGroup(className);
                List<String> lore;
                if (classServerGroup.equals("null")) {
                    lore = Arrays.asList(InventoryUtilities.printClassPrice(classCoast), "", GlobalUtilities.getColorByName(Variables.ABILITIESDISPLAYNAME) + Variables.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilities.get(0)) + "  " + abilities.get(0), GlobalUtilities.getColorByName(abilities.get(1)) + "  " + abilities.get(1), "", EDIT, CREATENEWCLASSLEVEL, DELETE);
                } else {
                    lore = Arrays.asList(InventoryUtilities.printClassPrice(classCoast), "", InventoryUtilities.printNeededRank(classServerGroup), "", GlobalUtilities.getColorByName(Variables.ABILITIESDISPLAYNAME) + Variables.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilities.get(0)) + "  " + abilities.get(0), GlobalUtilities.getColorByName(abilities.get(1)) + "  " + abilities.get(1), "", EDIT, CREATENEWCLASSLEVEL, DELETE);
                }
                inventory.addItem(ItemBuilder.createItemStackWithLore(classIcon, 1, color + className, lore));
            }
            player.openInventory(inventory);
        }
    }

    private void startCreateNewClassLevelSetup(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.RIGHT) {
            if (KeineKohle.SETUPMODE.containsKey(player)) {
                howToQuitTheSetupMode(player);
            } else {
                String className = GlobalUtilities.getNameWithoutColorCode(event.getCurrentItem().getItemMeta().getDisplayName());
                ClassFabric classFabric = new ClassFabric();
                classFabric.putPlayerIntoSetupMode(player);
                classFabric.setMode(classFabric.getMODECREATENEWCLASSLEVEL());
                classFabric.setClassName(className);
                classFabric.setStageMax(3);
                MySQLMethods mySQLMethods = new MySQLMethods();
                classFabric.setClassLevel(mySQLMethods.selectHighestClassLevelFromClasses(className) + 1);
                player.closeInventory();
                GlobalStages.messageStageInfo(player, classFabric);
                TitleAPI.sendTitle(player, 20 * 1, 20 * 2, 20 * 1, KeineKohle.CHATCOLOR + "Please enter the class", "§l§aCoast§r");
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "Please type the §l§acoast§r" + KeineKohle.CHATCOLOR + " " + "of the class.");
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "Note: You can't use white spaces!");
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "Note: You can type 'back', to go to the previous stage!");
            }
        }

    }


    private void startCreateNewClassSetup(InventoryClickEvent event, Player player) {
        if (event.getClick() == ClickType.LEFT) {
            if (KeineKohle.SETUPMODE.containsKey(player)) {
                howToQuitTheSetupMode(player);
            } else {
                ClassFabric classFabric = new ClassFabric();
                classFabric.putPlayerIntoSetupMode(player);
                classFabric.setMode(classFabric.getMODECREATENEWCLASS());
                classFabric.setStageMax(8);
                player.closeInventory();
                TitleAPI.sendTitle(player, 20 * 1, 20 * 2, 20 * 1, KeineKohle.CHATCOLOR + "Please enter the class", "§l§aName§r");
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "Please type the §l§aname§r" + KeineKohle.CHATCOLOR + " " + "of the class.");
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "Note: You can't use white spaces!");
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "Note: You can type 'back', to go to the previous stage!");
            }
        }
    }

    private void howToQuitTheSetupMode(Player player) {
        player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "If you want to quit the setup please type 'cancel' in the chat!");
    }
}

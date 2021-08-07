package me.keinekohle.net.utilities.cmd;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.Classes;
import me.keinekohle.net.utilities.GlobalUtilities;
import me.keinekohle.net.utilities.ItemBuilder;
import me.keinekohle.net.utilities.Variables;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

public class CmdDungeonUtilities {

    private static final List<String> CLASSINFO = Arrays.asList("§bCreate new class (Left click)", "§bList all classes (Right click)");
    private static final List<String> TITLEINFO = Arrays.asList("§bCreate new title (Left click)", "§bList all titles (Right click)");
    private static final List<String> BOOSTERINFO = Arrays.asList("§bCreate new booster (Left click)", "§bList all booster (Right click)");
    private static final List<String> LOBBYINFO = Arrays.asList("§bSet spawn (Left click)", "§bSet class selection Armorstand (Right click)");

    public static final String BUILDMODE = "Buildmode";
    private static final String BUILDMODEON = "§aon§b (Left click)";
    private static final String BUILDMODEOFF = "§coff§b (Left click)";

    public static final String LOBBY = "Lobby";

    private CmdDungeonUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static void createCMDDungeonInventory(Player player) {
        int invSize = 9 * 5;
        Inventory inventory = Bukkit.createInventory(player, invSize, GlobalUtilities.getColorByName(KeineKohle.DISPLAYNAME) + KeineKohle.DISPLAYNAME);
        fillInventoryBorder(inventory, invSize);
        inventory.setItem(10, ItemBuilder.createItemStackWithLore(Material.IRON_CHESTPLATE, 1, GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES, CLASSINFO));
        inventory.setItem(11, ItemBuilder.createItemStackWithLore(Material.NAME_TAG, 1, GlobalUtilities.getColorByName(Variables.SHOPTITLES) + Variables.SHOPTITLES, TITLEINFO));
        addBuildmode(player, inventory);
        inventory.setItem(12, ItemBuilder.createItemStackWithLore(Material.BEACON, 1, GlobalUtilities.getColorByName(Variables.SHOPBOOSTER) + Variables.SHOPBOOSTER, BOOSTERINFO));
        inventory.setItem(14, ItemBuilder.createItemStackWithLore(Material.NETHER_STAR, 1, GlobalUtilities.getColorByName(LOBBY) + LOBBY, LOBBYINFO));
        player.openInventory(inventory);
    }

    private static void addBuildmode(Player player, Inventory inventory) {
        if (KeineKohle.BUILDMODE.contains(player)) {
            inventory.setItem(28, ItemBuilder.createItemStackEnchantedWithLore(Material.IRON_PICKAXE, 1, GlobalUtilities.getColorByName(BUILDMODE) + BUILDMODE, Arrays.asList(BUILDMODEOFF)));
        } else {
            inventory.setItem(28, ItemBuilder.createItemStackWithLore(Material.IRON_PICKAXE, 1, GlobalUtilities.getColorByName(BUILDMODE) + BUILDMODE, Arrays.asList(BUILDMODEON)));
        }
    }

    private static Integer countClasses() {
        MySQLMethods mySQLMethods = new MySQLMethods();
        int amount = mySQLMethods.selectAllClasses().size();
        if (amount == 0) return 1;
        return amount;
    }

    private static void fillInventoryBorder(Inventory inventory, int invSize) {
        for (int i = 0; i < invSize; i++) {
            if (i <= 9) {
                inventory.setItem(i, ItemBuilder.createItemStack(Material.GREEN_STAINED_GLASS_PANE, 1, "§a"));
            } else if (i == 17 || i == 18) {
                inventory.setItem(i, ItemBuilder.createItemStack(Material.GREEN_STAINED_GLASS_PANE, 1, "§a"));
            } else if (i == 26 || i == 27) {
                inventory.setItem(i, ItemBuilder.createItemStack(Material.GREEN_STAINED_GLASS_PANE, 1, "§a"));
            } else if (i <= invSize && i >= (invSize - 10)) {
                inventory.setItem(i, ItemBuilder.createItemStack(Material.GREEN_STAINED_GLASS_PANE, 1, "§a"));
            }
        }
    }

}

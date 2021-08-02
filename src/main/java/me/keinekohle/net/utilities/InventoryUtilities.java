package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryUtilities {

    private InventoryUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FILLDIESPLAYNAME = "§a";

    public static Inventory createShopInventroy(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9 * 3, GlobalUtilities.getColorByName(KeineKohle.CHESTDISPLAYNAME) + KeineKohle.CHESTDISPLAYNAME);
        fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        inventory.setItem(10, ItemBuilder.createItemStack(Material.IRON_CHESTPLATE, 1, GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES));
        return inventory;
    }

    public static void fillInventory(Inventory inventory, Material fillMaterial) {
        for(int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, ItemBuilder.createItemStack(fillMaterial, 1, FILLDIESPLAYNAME));
        }
    }

}

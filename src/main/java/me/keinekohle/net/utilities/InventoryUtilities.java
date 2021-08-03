package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public final class InventoryUtilities {

    public static final String FILLDIESPLAYNAME = "§a";

    private InventoryUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static Inventory createShopInventroy(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9 * 3, GlobalUtilities.getColorByName(KeineKohle.CHESTDISPLAYNAME) + KeineKohle.CHESTDISPLAYNAME);
        fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        inventory.setItem(10, ItemBuilder.createItemStack(Material.IRON_CHESTPLATE, 1, GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES));
        return inventory;
    }

    public static Inventory createDifficultyInventroy(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9 * 3, GlobalUtilities.getColorByName(KeineKohle.COMPARATORDISPLAYNAME) + KeineKohle.COMPARATORDISPLAYNAME);
        fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        inventory.setItem(10, ItemBuilder.createItemStack(Material.LIME_DYE, 1, GlobalUtilities.getColorByName(KeineKohle.DIFICULTYEASY) + KeineKohle.DIFICULTYEASY));
        inventory.setItem(12, ItemBuilder.createItemStack(Material.GREEN_DYE, 1, GlobalUtilities.getColorByName(KeineKohle.DIFICULTYNORMAL) + KeineKohle.DIFICULTYNORMAL));
        inventory.setItem(14, ItemBuilder.createItemStack(Material.ORANGE_DYE, 1, GlobalUtilities.getColorByName(KeineKohle.DIFICULTYNORMAL) + KeineKohle.DIFICULTYNORMAL));
        inventory.setItem(16, ItemBuilder.createItemStack(Material.RED_DYE, 1, GlobalUtilities.getColorByName(KeineKohle.DIFICULTYNORMAL) + KeineKohle.DIFICULTYNORMAL));
        return inventory;
    }

    public static Inventory createUpgradeInventroy(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9 * 3, GlobalUtilities.getColorByName(KeineKohle.ANVILDISPLAYNAME) + KeineKohle.ANVILDISPLAYNAME);
        fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        inventory.setItem(0, ItemBuilder.createItemStack(Material.IRON_CHESTPLATE, 1, GlobalUtilities.getColorByName(Classes.TANKCLASS) + Classes.TANKCLASS));
        inventory.setItem(1, ItemBuilder.createItemStack(Material.GREEN_DYE, 1, GlobalUtilities.getColorByName(Classes.HEALERCLASS) + Classes.HEALERCLASS));
        inventory.setItem(2, ItemBuilder.createItemStack(Material.ORANGE_DYE, 1, GlobalUtilities.getColorByName(Classes.ARCHERCLASS) + Classes.ARCHERCLASS));
        inventory.setItem(3, ItemBuilder.createItemStack(Material.RED_DYE, 1, GlobalUtilities.getColorByName(Classes.SURVIVORCLASS) + Classes.SURVIVORCLASS));
        return inventory;
    }

    public static Inventory createAbilitiesInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9 * 3, GlobalUtilities.getColorByName(KeineKohle.ABILITIESDISPLAYNAME) + KeineKohle.ABILITIESDISPLAYNAME);

        addAbilityToInventory(inventory, Material.FIRE_CHARGE, Abilites.FIRE, Abilites.FIRELOREDESCRIPTION);
        addAbilityToInventory(inventory, Material.BLAZE_POWDER, Abilites.FIREGRENADE, Abilites.FIREGRENADELOREDESCRIPTION);

        addAbilityToInventory(inventory, Material.DIRT, Abilites.EARTH, Abilites.EARTHLOREDESCRIPTION);
        addAbilityToInventory(inventory, Material.NETHERRACK, Abilites.HELL, Abilites.HELLLOREDESCRIPTION);

        addAbilityToInventory(inventory, Material.BLUE_ICE, Abilites.FREEZE, Abilites.FREEZELOREDESCRIPTION);
        addAbilityToInventory(inventory, Material.SPLASH_POTION, Abilites.FREEZEGRANDE, Abilites.FREEZEGRANDELOREDESCRIPTION);

        addAbilityToInventory(inventory, Material.TNT, Abilites.C4, Abilites.C4LOREDESCRIPTION);
        addAbilityToInventory(inventory, Material.FIREWORK_STAR, Abilites.GRENADE, Abilites.GRENADELOREDESCRIPTION);

        addAbilityToInventory(inventory, Material.BEACON, Abilites.HEALBEACON, Abilites.HEALBEACONLOREDESCRIPTION);
        addAbilityToInventory(inventory, Material.MAGMA_CREAM, Abilites.HEALGRENADE, Abilites.HEALGRENADELOREDESCRIPTION);

        addAbilityToInventory(inventory, Material.POTION, Abilites.WEAKNESS, Abilites.WEAKNESSLOREDESCRIPTION);
        addAbilityToInventory(inventory, Material.SPLASH_POTION, Abilites.WWEAKNESSGRANDE, Abilites.WWEAKNESSGRANDELOREDESCRIPTION);

        fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        return inventory;
    }

    private static void addAbilityToInventory(Inventory inventory, Material material, String ability, List<String> abilityDescription) {
        inventory.addItem(ItemBuilder.createItemStackWithLore(material, 1, GlobalUtilities.getColorByName(ability) + ability, abilityDescription));
    }

    public static void fillInventory(Inventory inventory, Material fillMaterial) {
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, ItemBuilder.createItemStack(fillMaterial, 1, FILLDIESPLAYNAME));
            }
        }
    }

}

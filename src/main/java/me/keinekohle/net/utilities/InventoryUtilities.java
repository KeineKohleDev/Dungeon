package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class InventoryUtilities {

    public static final String FILLDIESPLAYNAME = "§a";
    public static final String BUY = "§bBuy (Double left click)";
    public static final String PREVIEW = "§bPreview (Right click)";
    public static final String BOUGHT = "§e§lBought";

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
        inventory.setItem(14, ItemBuilder.createItemStack(Material.ORANGE_DYE, 1, GlobalUtilities.getColorByName(KeineKohle.DIFICULTYHARD) + KeineKohle.DIFICULTYHARD));
        inventory.setItem(16, ItemBuilder.createItemStack(Material.RED_DYE, 1, GlobalUtilities.getColorByName(KeineKohle.DIFICULTYVERYHARD) + KeineKohle.DIFICULTYVERYHARD));
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
        addAbilityToInventoryNotShowingPotionEffects(inventory, Material.SPLASH_POTION, Abilites.FREEZEGRANDE, Abilites.FREEZEGRANDELOREDESCRIPTION, Color.BLUE);

        addAbilityToInventory(inventory, Material.TNT, Abilites.C4, Abilites.C4LOREDESCRIPTION);
        addAbilityToInventory(inventory, Material.FIREWORK_STAR, Abilites.GRENADE, Abilites.GRENADELOREDESCRIPTION);

        addAbilityToInventory(inventory, Material.BEACON, Abilites.HEALBEACON, Abilites.HEALBEACONLOREDESCRIPTION);
        addAbilityToInventory(inventory, Material.MAGMA_CREAM, Abilites.HEALGRENADE, Abilites.HEALGRENADELOREDESCRIPTION);

        addAbilityToInventoryNotShowingPotionEffects(inventory, Material.POTION, Abilites.WEAKNESS, Abilites.WEAKNESSLOREDESCRIPTION, Color.GRAY);
        addAbilityToInventoryNotShowingPotionEffects(inventory, Material.SPLASH_POTION, Abilites.WWEAKNESSGRANDE, Abilites.WWEAKNESSGRANDELOREDESCRIPTION, Color.GRAY);

        inventory.setItem(26, ItemBuilder.createItemStack(Material.GREEN_STAINED_GLASS_PANE, 1, "§aNext"));
        fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        return inventory;
    }

    public static Inventory createClassesInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9*6, GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES);
        fillInventoryWithClassesLevelOne(inventory, player);
        fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        return inventory;
    }

    public static void fillInventoryWithClassesLevelOne(Inventory inventory, Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        List<String> classes = mySQLMethods.selectAllClasses();
        List<String> boughtClasses = mySQLMethods.selectAllBoughtClasses(player);
        if(classes != null) {
            for (String className : classes) {

                Material representativeItem = mySQLMethods.selectIconFromClasses(className);
                int classCoast = mySQLMethods.selectClassCoastFromClasses(className, 1);
                ChatColor color = mySQLMethods.selectClassColorFromClasses(className, 1);
                String abilities = mySQLMethods.selectAbilitiesFromClasses(className, 1).replace("[", "").replace("]", "");
                String abilityOne = abilities.substring(0, abilities.indexOf(","));
                String abilityTwo = abilities.substring(abilities.indexOf(",")+2);
                if(boughtClasses.contains(className)) {
                    ItemStack itemStack = ItemBuilder.createItemStackWithLore(representativeItem, 1, color + className, Arrays.asList(BOUGHT));
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.addEnchant(Enchantment.LUCK, 1, false);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemStack.setItemMeta(itemMeta);
                    inventory.addItem(itemStack);
                } else {
                    List<String> lore = Arrays.asList("§aPrice:§c " + classCoast, "",GlobalUtilities.getColorByName(KeineKohle.ABILITIESDISPLAYNAME) + KeineKohle.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilityOne) + "  " + abilityOne, GlobalUtilities.getColorByName(abilityTwo) + "  " + abilityTwo, "", BUY, PREVIEW);
                    inventory.addItem(ItemBuilder.createItemStackWithLore(representativeItem, 1, color + className, lore));
                }
            }
        }
    }

    private static void addAbilityToInventory(Inventory inventory, Material material, String ability, List<String> abilityDescription) {
        inventory.addItem(ItemBuilder.createItemStackWithLore(material, 1, GlobalUtilities.getColorByName(ability) + ability, abilityDescription));
    }

    private static void addAbilityToInventoryNotShowingPotionEffects(Inventory inventory, Material material, String ability, List<String> abilityDescription, Color color) {
        inventory.addItem(ItemBuilder.createItemStackWithLoreWithOutShowingPotionEffects(material, 1, GlobalUtilities.getColorByName(ability) + ability, abilityDescription, color));
    }

    public static void fillInventory(Inventory inventory, Material fillMaterial) {
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, ItemBuilder.createItemStack(fillMaterial, 1, FILLDIESPLAYNAME));
            }
        }
    }

}

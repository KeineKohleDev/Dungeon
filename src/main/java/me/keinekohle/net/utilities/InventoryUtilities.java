package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

public final class InventoryUtilities {

    public static final String FILLDIESPLAYNAME = "§a";
    public static final String BUY = "§bBuy (Right click)";
    public static final String UPGRADE = "§bUpgrade (Right click)";
    public static final String PREVIEW = "§bPreview (Left click)";
    public static final String BOUGHT = "§e§lBought";
    public static final String HIGHESTCLASS = "§e§lHighest level reached!";

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

    public static void createUpgradeInventroy(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        List<String> boughtClasses = mySQLMethods.selectAllBoughtClasses(player);
        Inventory inventory = Bukkit.createInventory(player, GlobalUtilities.calculateInventorySize(boughtClasses.size()), GlobalUtilities.getColorByName(KeineKohle.ANVILDISPLAYNAME) + KeineKohle.ANVILDISPLAYNAME);

        if (boughtClasses != null) {
            fillInventoryWithUpgradableClasses(inventory, player, boughtClasses, mySQLMethods);
            fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
            player.openInventory(inventory);
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + Language.NOCLASSESBOUGHTYET);
        }
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

    public static void createClassesInventory(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        List<String> classes = mySQLMethods.selectAllClasses();
        if (classes != null) {
            Inventory inventory = Bukkit.createInventory(player, GlobalUtilities.calculateInventorySize(classes.size()), GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES);
            fillInventoryWithClassesLevelOne(inventory, player, classes, mySQLMethods);
            fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
            player.openInventory(inventory);
        } else {
            player.sendMessage(KeineKohle.PREFIX + GlobalUtilities.getColorByName(KeineKohle.CHATCOLOR) + " " + "There are no classes!");
        }
    }

    public static void fillInventoryWithUpgradableClasses(Inventory inventory, Player player, List<String> boughtClasses, MySQLMethods mySQLMethods) {
        for (String className : boughtClasses) {
            int highestClassLevel = mySQLMethods.selectHighestClassLevelFromClasses(className);
            int classLevel = mySQLMethods.selectClassLevelFromPlayerByClassName(player, className);
            ChatColor color = mySQLMethods.selectClassColorFromClasses(className);
            List<String> abilities = Abilites.selectClassAbilities(className);
            Material classIcon = mySQLMethods.selectIconFromClasses(className);
            if (classLevel == highestClassLevel) {
                inventory.addItem(ItemBuilder.createItemStackEnchantedWithLore(classIcon, 1, color + className, Arrays.asList(HIGHESTCLASS, "§aLevel:§c " + highestClassLevel)));
            } else {
                int nextClassLevel = classLevel+1;
                int classCoast = mySQLMethods.selectClassCoastFromClasses(className, nextClassLevel);
                List<String> lore = Arrays.asList("§aLevel:§c " + nextClassLevel, "§aPrice:§c " + classCoast, "", GlobalUtilities.getColorByName(KeineKohle.ABILITIESDISPLAYNAME) + KeineKohle.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilities.get(0)) + "  " + abilities.get(0), GlobalUtilities.getColorByName(abilities.get(1)) + "  " + abilities.get(1), "", UPGRADE, PREVIEW);
                inventory.addItem(ItemBuilder.createItemStackWithLore(classIcon, 1, color + className, lore));
            }
        }
    }


    public static void fillInventoryWithClassesLevelOne(Inventory inventory, Player player, List<String> classes, MySQLMethods mySQLMethods) {
        List<String> boughtClasses = mySQLMethods.selectAllBoughtClasses(player);
        if (classes != null) {
            for (String className : classes) {
                Material classIcon = mySQLMethods.selectIconFromClasses(className);
                int classCoast = mySQLMethods.selectClassCoastFromClasses(className, 1);
                ChatColor color = mySQLMethods.selectClassColorFromClasses(className);
                List<String> abilities = Abilites.selectClassAbilities(className);
                if (boughtClasses != null && boughtClasses.contains(className)) {
                    inventory.addItem(ItemBuilder.createItemStackEnchantedWithLore(classIcon, 1, color + className, Arrays.asList(BOUGHT)));
                } else {
                    List<String> lore = Arrays.asList("§aPrice:§c " + classCoast, "", GlobalUtilities.getColorByName(KeineKohle.ABILITIESDISPLAYNAME) + KeineKohle.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilities.get(0)) + "  " + abilities.get(0), GlobalUtilities.getColorByName(abilities.get(1)) + "  " + abilities.get(1), "", BUY, PREVIEW);
                    inventory.addItem(ItemBuilder.createItemStackWithLore(classIcon, 1, color + className, lore));
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
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, ItemBuilder.createItemStack(fillMaterial, 1, FILLDIESPLAYNAME));
            }
        }
    }

}

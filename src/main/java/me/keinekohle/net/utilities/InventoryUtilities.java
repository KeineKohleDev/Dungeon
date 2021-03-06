package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.logging.Logger;

public final class InventoryUtilities {

    public static final String FILLDIESPLAYNAME = "§a";
    public static final String BUY = "§bBuy (Right click)";
    public static final String UPGRADE = "§bUpgrade (Right click)";
    public static final String PREVIEW = "§bPreview (Left click)";
    public static final String BOUGHT = "§e§lBought";
    public static final String ON = "§aOn";
    public static final String OFF = "§cOff";
    public static final String HIGHESTCLASS = "§e§lHighest level reached!";

    private InventoryUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static String printClassPrice(int classCoast) {
        return "§aPrice:§c " + classCoast;
    }

    public static String printNeededRank(String classServerGroup) {
        return "§6Rank:§9§l " + classServerGroup;
    }

    public static void createShopInventroy(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9 * 3, GlobalUtilities.getColorByName(Variables.CHESTDISPLAYNAME) + Variables.CHESTDISPLAYNAME);
        fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        inventory.setItem(10, ItemBuilder.createItemStack(Material.IRON_CHESTPLATE, 1, GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES));
        inventory.setItem(13, ItemBuilder.createItemStack(Material.BEACON, 1, GlobalUtilities.getColorByName(Variables.SHOPBOOSTER) + Variables.SHOPBOOSTER));
        inventory.setItem(16, ItemBuilder.createItemStack(Material.NAME_TAG, 1, GlobalUtilities.getColorByName(Variables.SHOPTITLES) + Variables.SHOPTITLES));
        player.openInventory(inventory);
    }

    public static void createDifficultyInventroy(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9 * 3, GlobalUtilities.getColorByName(Variables.COMPARATORDISPLAYNAME) + Variables.COMPARATORDISPLAYNAME);
        fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        createDifficultyVote(inventory, player);
        player.openInventory(inventory);
    }

    //-- Difficulty --

    private static void createDifficultyVote(Inventory inventory, Player player) {
        String youVoted = "§aYou";
        List<String> difficulties = new ArrayList<>();
        difficulties.add(Variables.DIFFICULTYEASY);
        difficulties.add(Variables.DIFFICULTYNORMAL);
        difficulties.add(Variables.DIFFICULTYHARD);
        difficulties.add(Variables.DIFFICULTYVERYHARD);
        int easy = 0;
        int normal = 0;
        int hard = 0;
        int veryhard = 0;
        for (String vote : KeineKohle.VOTEDIFFICULTY.values()) {
            switch (vote) {
                case Variables.DIFFICULTYEASY:
                    easy++;
                    break;
                case Variables.DIFFICULTYNORMAL:
                    normal++;
                    break;
                case Variables.DIFFICULTYHARD:
                    hard++;
                    break;
                case Variables.DIFFICULTYVERYHARD:
                    veryhard++;
                    break;
                default:
                    System.getLogger(Logger.GLOBAL_LOGGER_NAME).log(System.Logger.Level.ERROR, "Vote count error");
                    break;
            }
        }
        if (KeineKohle.VOTEDIFFICULTY.containsKey(player)) {
            markWhatThePlayerVotedFor(inventory, player, youVoted, difficulties, easy, normal, hard, veryhard);
        }
        addVoteMissingVoteItems(inventory, difficulties, easy, normal, hard, veryhard);
    }

    private static void addVoteMissingVoteItems(Inventory inventory, List<String> difficulties, int easy, int normal, int hard, int veryhard) {
        for (String difficulty : difficulties) {
            switch (difficulty) {
                case Variables.DIFFICULTYEASY:
                    inventory.setItem(10, ItemBuilder.createItemStackWithLore(Material.LIME_DYE, 1, GlobalUtilities.getColorByName(Variables.DIFFICULTYEASY) + Variables.DIFFICULTYEASY, Arrays.asList(getVotes(easy))));
                    break;
                case Variables.DIFFICULTYNORMAL:
                    inventory.setItem(12, ItemBuilder.createItemStackWithLore(Material.GREEN_DYE, 1, GlobalUtilities.getColorByName(Variables.DIFFICULTYNORMAL) + Variables.DIFFICULTYNORMAL, Arrays.asList(getVotes(normal))));
                    break;
                case Variables.DIFFICULTYHARD:
                    inventory.setItem(14, ItemBuilder.createItemStackWithLore(Material.ORANGE_DYE, 1, GlobalUtilities.getColorByName(Variables.DIFFICULTYHARD) + Variables.DIFFICULTYHARD, Arrays.asList(getVotes(hard))));
                    break;
                case Variables.DIFFICULTYVERYHARD:
                    inventory.setItem(16, ItemBuilder.createItemStackWithLore(Material.RED_DYE, 1, GlobalUtilities.getColorByName(Variables.DIFFICULTYVERYHARD) + Variables.DIFFICULTYVERYHARD, Arrays.asList(getVotes(veryhard))));
                    break;
                default:
                    System.getLogger(Logger.GLOBAL_LOGGER_NAME).log(System.Logger.Level.ERROR, "Add vote items error");
                    break;
            }
        }
    }

    private static void markWhatThePlayerVotedFor(Inventory inventory, Player player, String youVoted, List<String> difficulties, int easy, int normal, int hard, int veryhard) {
        switch (KeineKohle.VOTEDIFFICULTY.get(player)) {
            case Variables.DIFFICULTYEASY:
                inventory.setItem(10, ItemBuilder.createItemStackEnchantedWithLore(Material.LIME_DYE, 1, GlobalUtilities.getColorByName(Variables.DIFFICULTYEASY) + Variables.DIFFICULTYEASY, Arrays.asList(youVoted, getVotes(easy))));
                difficulties.remove(Variables.DIFFICULTYEASY);
                break;
            case Variables.DIFFICULTYNORMAL:
                inventory.setItem(12, ItemBuilder.createItemStackEnchantedWithLore(Material.GREEN_DYE, 1, GlobalUtilities.getColorByName(Variables.DIFFICULTYNORMAL) + Variables.DIFFICULTYNORMAL, Arrays.asList(youVoted, getVotes(normal))));
                difficulties.remove(Variables.DIFFICULTYNORMAL);
                break;
            case Variables.DIFFICULTYHARD:
                inventory.setItem(14, ItemBuilder.createItemStackEnchantedWithLore(Material.ORANGE_DYE, 1, GlobalUtilities.getColorByName(Variables.DIFFICULTYHARD) + Variables.DIFFICULTYHARD, Arrays.asList(youVoted, getVotes(hard))));
                difficulties.remove(Variables.DIFFICULTYHARD);
                break;
            case Variables.DIFFICULTYVERYHARD:
                inventory.setItem(16, ItemBuilder.createItemStackEnchantedWithLore(Material.RED_DYE, 1, GlobalUtilities.getColorByName(Variables.DIFFICULTYVERYHARD) + Variables.DIFFICULTYVERYHARD, Arrays.asList(youVoted, getVotes(veryhard))));
                difficulties.remove(Variables.DIFFICULTYVERYHARD);
                break;
            default:
                System.getLogger(Logger.GLOBAL_LOGGER_NAME).log(System.Logger.Level.ERROR, "Select vote error");
                break;
        }
    }

    private static String getVotes(int votes) {
        return "§aVotes:§c " + votes;
    }

    //-- Abilities --

    public static Inventory createAbilitiesInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9 * 3, GlobalUtilities.getColorByName(Variables.ABILITIESDISPLAYNAME) + Variables.ABILITIESDISPLAYNAME);

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

    private static void addAbilityToInventory(Inventory inventory, Material material, String ability, List<String> abilityDescription) {
        inventory.addItem(ItemBuilder.createItemStackWithLore(material, 1, GlobalUtilities.getColorByName(ability) + ability, abilityDescription));
    }

    private static void addAbilityToInventoryNotShowingPotionEffects(Inventory inventory, Material material, String ability, List<String> abilityDescription, Color color) {
        inventory.addItem(ItemBuilder.createItemStackWithLoreWithOutShowingPotionEffects(material, 1, GlobalUtilities.getColorByName(ability) + ability, abilityDescription, color));
    }

    //-- Upgrade Inventroy --

    public static void createUpgradeInventroy(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        List<String> boughtClasses = mySQLMethods.selectAllBoughtClasses(player);
        Inventory inventory = Bukkit.createInventory(player, GlobalUtilities.calculateInventorySize(boughtClasses.size()), GlobalUtilities.getColorByName(Variables.ANVILDISPLAYNAME) + Variables.ANVILDISPLAYNAME);

        if (boughtClasses.isEmpty()) {
            player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + Language.noClassesBoughtYet);
        } else {
            fillInventoryWithUpgradableClasses(inventory, player, boughtClasses, mySQLMethods);
            fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
            player.openInventory(inventory);
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
                int nextClassLevel = classLevel + 1;
                int classCoast = mySQLMethods.selectClassCoastFromClasses(className, nextClassLevel);
                List<String> lore = Arrays.asList("§aLevel:§c " + nextClassLevel, printClassPrice(classCoast), "", GlobalUtilities.getColorByName(Variables.ABILITIESDISPLAYNAME) + Variables.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilities.get(0)) + "  " + abilities.get(0), GlobalUtilities.getColorByName(abilities.get(1)) + "  " + abilities.get(1), "", UPGRADE, PREVIEW);
                inventory.addItem(ItemBuilder.createItemStackWithLore(classIcon, 1, color + className, lore));
            }
        }
    }

    //-- Settings Inventory --

    public static void createSettingsInventory(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        Inventory inventory = Bukkit.createInventory(player, 9 * 1, GlobalUtilities.getColorByName(Variables.BOOKDISPLAYNAME) + Variables.BOOKDISPLAYNAME);
        HashMap<String, Boolean> playerSettings = mySQLMethods.selectAllPlayerSetting(player);
        for (Map.Entry<String, Boolean> entry : playerSettings.entrySet()) {
            if (Boolean.TRUE.equals(entry.getValue())) {
                inventory.addItem(ItemBuilder.createItemStackEnchantedWithLore(PlayerSettings.getMaterialBySettingsName(entry.getKey()), 1, PlayerSettings.getLanguageNameBySettingsName(entry.getKey()), Arrays.asList(ON)));
            } else {
                inventory.addItem(ItemBuilder.createItemStackWithLore(PlayerSettings.getMaterialBySettingsName(entry.getKey()), 1, PlayerSettings.getLanguageNameBySettingsName(entry.getKey()), Arrays.asList(OFF)));
            }
        }
        player.openInventory(inventory);
    }

    //-- Classes Inventory --

    public static void createClassesInventory(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        List<String> classes = mySQLMethods.selectAllClasses();
        if (classes != null) {
            Inventory inventory = Bukkit.createInventory(player, GlobalUtilities.calculateInventorySize(classes.size()), GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Classes.SHOPCLASSES);
            fillInventoryWithClassesLevelOne(inventory, player, classes, mySQLMethods);
            fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
            player.openInventory(inventory);
        } else {
            player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + "There are no classes!");
        }
    }


    public static void fillInventoryWithClassesLevelOne(Inventory inventory, Player player, List<String> classes, MySQLMethods mySQLMethods) {
        List<String> boughtClasses = mySQLMethods.selectAllBoughtClasses(player);
        if (!classes.isEmpty()) {
            for (String className : classes) {
                Material classIcon = mySQLMethods.selectIconFromClasses(className);
                int classCoast = mySQLMethods.selectClassCoastFromClasses(className, 1);
                ChatColor color = mySQLMethods.selectClassColorFromClasses(className);
                List<String> abilities = Abilites.selectClassAbilities(className);
                if (!boughtClasses.isEmpty() && boughtClasses.contains(className)) {
                    inventory.addItem(ItemBuilder.createItemStackEnchantedWithLore(classIcon, 1, color + className, Arrays.asList(BOUGHT)));
                } else {
                    String classServerGroup = mySQLMethods.selectClassServerGroup(className);
                    List<String> lore;
                    if (classServerGroup.equals("null")) {
                        lore = Arrays.asList(printClassPrice(classCoast), "", GlobalUtilities.getColorByName(Variables.ABILITIESDISPLAYNAME) + Variables.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilities.get(0)) + "  " + abilities.get(0), GlobalUtilities.getColorByName(abilities.get(1)) + "  " + abilities.get(1), "", BUY, PREVIEW);
                    } else {
                        lore = Arrays.asList(printClassPrice(classCoast), "", printNeededRank(classServerGroup), "", GlobalUtilities.getColorByName(Variables.ABILITIESDISPLAYNAME) + Variables.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilities.get(0)) + "  " + abilities.get(0), GlobalUtilities.getColorByName(abilities.get(1)) + "  " + abilities.get(1), "", BUY, PREVIEW);
                    }
                    inventory.addItem(ItemBuilder.createItemStackWithLore(classIcon, 1, color + className, lore));
                }
            }
        }
    }

    public static void fillInventory(Inventory inventory, Material fillMaterial) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, ItemBuilder.createItemStack(fillMaterial, 1, FILLDIESPLAYNAME));
            }
        }
    }

}

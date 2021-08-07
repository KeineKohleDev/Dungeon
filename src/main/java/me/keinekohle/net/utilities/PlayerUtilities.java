package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.scoreboard.LobbyScoreboard;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public final class PlayerUtilities {

    private PlayerUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static void onJoin(Player player) {
        clearPlayerInventory(player);
        giveLobbyItemsToPlayer(player);
        PlayerUtilities.clearPlayerPotionEffects(player);
        sendLastUsedClassGotSelectedMessage(player);
        LobbyScoreboard.sendLobbyScoreboard();
        healAndFeed(player);
    }

    public static void healAndFeed(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    public static void clearPlayerInventory(Player player) {
        player.getInventory().clear();
    }

    public static void giveLobbyItemsToPlayer(Player player) {
        player.getInventory().setItem(0, ItemBuilder.createItemStack(Material.CHEST, 1, GlobalUtilities.getColorByName(Variables.CHESTDISPLAYNAME) + Variables.CHESTDISPLAYNAME));
        player.getInventory().setItem(1, ItemBuilder.createItemStack(Material.ANVIL, 1, GlobalUtilities.getColorByName(Variables.ANVILDISPLAYNAME) + Variables.ANVILDISPLAYNAME));
        player.getInventory().setItem(4, ItemBuilder.createItemStack(Material.ENDER_CHEST, 1, GlobalUtilities.getColorByName(Variables.ENDERCHESTNAME) + Variables.ENDERCHESTNAME));
        player.getInventory().setItem(7, ItemBuilder.createItemStack(Material.COMPARATOR, 1, GlobalUtilities.getColorByName(Variables.COMPARATORDISPLAYNAME) + Variables.COMPARATORDISPLAYNAME));
        player.getInventory().setItem(8, ItemBuilder.createItemStack(Material.BOOK, 1, GlobalUtilities.getColorByName(Variables.BOOKDISPLAYNAME) + Variables.BOOKDISPLAYNAME));
    }

    public static void clearPlayerPotionEffects(Player player) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public static void sendLastUsedClassGotSelectedMessage(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        if (mySQLMethods.selectPlayerSetting(player, Variables.AUTOSELECTCLASS)) {
            String lastclass = Classes.getLastUsedClass(player);
            if (!lastclass.equals(Classes.NONECLASS)) {
                ClassFabric classFabric = new ClassFabric();
                classFabric.setPlayer(player);
                classFabric.setClassName(lastclass);
                classFabric.autoFill();
                KeineKohle.SELECTEDCLASS.put(player, classFabric);
                player.sendMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + " " + Language.classGotAutoSelected);
                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
            }
        }
    }

    public static void setLastSelectedClass(Player player, String className) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        mySQLMethods.updateLastUsedClass(player, className);
    }
}

package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ClassSeletionArmorStand {

    private ClassSeletionArmorStand() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SELECTCLASS = "§bSelect class (Left click)";

    public static void spawnClassSelectionArmorStand() {
        MySQLMethods mySQLMethods = new MySQLMethods();
        if (mySQLMethods.checkIfLocationAlreadyExists("ClassSelection")) {
            Location location = mySQLMethods.selectLocationByName("ClassSelection");
            removeOldArmorStand(location);
            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            EntityEquipment entityEquipment = armorStand.getEquipment();
            armorStand.setCustomName(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Language.ARMORSTANDNAME);
            armorStand.setCustomNameVisible(true);
            armorStand.setBasePlate(false);
            armorStand.setVisible(false);
            entityEquipment.setHelmet(new ItemStack(Material.NETHERITE_HELMET));
            entityEquipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            entityEquipment.setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
            entityEquipment.setBoots(new ItemStack(Material.NETHERITE_BOOTS));
            entityEquipment.setItem(EquipmentSlot.HAND, new ItemStack(Material.IRON_AXE));
            entityEquipment.setItem(EquipmentSlot.OFF_HAND, new ItemStack(Material.SHIELD));
        }
    }

    private static void removeOldArmorStand(Location location) {
        for (Entity entity : location.getWorld().getEntities()) {
            if (entity instanceof ArmorStand armorStand) {
                if (armorStand.getCustomName().equals(GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Language.ARMORSTANDNAME)) {
                    armorStand.remove();
                }
            }
        }
    }

    public static void openInventory(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        List<String> boughtClasses = mySQLMethods.selectAllBoughtClasses(player);
        Inventory inventory = Bukkit.createInventory(player, GlobalUtilities.calculateInventorySize(boughtClasses.size()), GlobalUtilities.getColorByName(Classes.SHOPCLASSES) + Language.ARMORSTANDNAME);
        for (String className : boughtClasses) {
            int classLevel = mySQLMethods.selectClassLevelFromPlayerByClassName(player, className);
            ChatColor color = mySQLMethods.selectClassColorFromClasses(className);
            List<String> abilities = Abilites.selectClassAbilities(className);
            Material classIcon = mySQLMethods.selectIconFromClasses(className);
            List<String> lore = Arrays.asList("§aLevel:§c " + classLevel, "", GlobalUtilities.getColorByName(KeineKohle.ABILITIESDISPLAYNAME) + KeineKohle.ABILITIESDISPLAYNAME + ":", GlobalUtilities.getColorByName(abilities.get(0)) + "  " + abilities.get(0), GlobalUtilities.getColorByName(abilities.get(1)) + "  " + abilities.get(1), "", SELECTCLASS);
            inventory.addItem(ItemBuilder.createItemStackWithLore(classIcon, 1, color + className, lore));
        }
        InventoryUtilities.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        player.openInventory(inventory);
    }
}

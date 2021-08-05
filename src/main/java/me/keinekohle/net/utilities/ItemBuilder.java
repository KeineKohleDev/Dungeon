package me.keinekohle.net.utilities;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.List;

public final class ItemBuilder {

    private ItemBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static ItemStack createItemStack(Material material, int amount, String displayName) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStackWithLore(Material material, int amount, String displayName, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStackWithLoreWithOutShowingPotionEffects(Material material, int amount, String displayName, List<String> lore, Color color) {
        ItemStack itemStack = new ItemStack(material, amount);
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        potionMeta.setDisplayName(displayName);
        potionMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        potionMeta.setColor(color);
        potionMeta.setLore(lore);
        itemStack.setItemMeta(potionMeta);
        return itemStack;
    }

    public static ItemStack createItemStackEnchantedWithLore(Material material, int amount, String displayName, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemMeta.addEnchant(Enchantment.LUCK, 1, false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

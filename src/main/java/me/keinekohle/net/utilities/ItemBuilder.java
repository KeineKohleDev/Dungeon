package me.keinekohle.net.utilities;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
}

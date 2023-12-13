package com.lkmc.lkmcplugin.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {
    public static ItemStack buildItem(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buildItem(Material material, String name, List<String> lore, List<Enchantment> enchantments) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        for (Enchantment enchantment : enchantments) {
            itemMeta.addEnchant(enchantment, 10, true);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buildItem(Material material, String name) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buildItem(Material material, String name, int num) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(num);
        return itemStack;
    }

    public static ItemStack buildItem(Material material, List<String> lore, List<Enchantment> enchantments) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        for (Enchantment enchantment : enchantments) {
            itemMeta.addEnchant(enchantment, 10, true);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buildItem(Material material, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buildItem(Material material) {
        return new ItemStack(material);
    }

    public static ItemStack buildItem(String material) {
        Material m = Material.getMaterial(material);
        if (m == null)
            return buildItem(Material.RED_STAINED_GLASS_PANE, "物品" + material + "获取失败");
        return new ItemStack(m);
    }

    public static ItemStack buildItem(String material, List<String> lore) {
        Material m = Material.getMaterial(material);
        if (m == null)
            return buildItem(Material.RED_STAINED_GLASS_PANE, "物品" + material + "获取失败");
        ItemStack itemStack = new ItemStack(m);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buildItem(String material, String name, List<String> lore) {
        Material m = Material.getMaterial(material);
        if (m == null)
            return buildItem(Material.RED_STAINED_GLASS_PANE, "物品" + material + "获取失败");
        ItemStack itemStack = new ItemStack(m);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buildItem(ItemStack material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && itemMeta.getLore() != null) {
            List<String> list = itemMeta.getLore();
            list.addAll(lore);
            itemMeta.setLore(list);
        } else {
            itemMeta.setLore(lore);
        }
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buildItem(ItemStack material, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && itemMeta.getLore() != null) {
            List<String> list = itemMeta.getLore();
            list.addAll(lore);
            itemMeta.setLore(list);
        } else {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

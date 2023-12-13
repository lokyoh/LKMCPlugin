package com.lkmc.lkmcplugin.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class MyInventory {
    public static boolean hasEmpty(Player p) {
        ItemStack[] itemStacks = p.getInventory().getContents();
        for (int index = 0; index < 36; index++) {
            if (itemStacks[index] == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean useThisThing(Player p, ItemMeta tar) {
        ItemStack[] itemStacks = p.getInventory().getContents();
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && itemStack.hasItemMeta() && Objects.equals(itemStack.getItemMeta(), tar)) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                return true;
            }
        }
        return false;
    }

    public static boolean giveItem(Player p, ItemStack item) {
        if (hasEmpty(p)) {
            p.getInventory().addItem(item);
            return true;
        }
        return false;
    }

    public static void takeItemUncheck(Player p, ItemStack item, int amount) {
        ItemStack[] itemStacks = p.getInventory().getContents();
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && itemStack.isSimilar(item)) {
                amount -= itemStack.getAmount();
                itemStack.setAmount(amount < 0 ? -amount : 0);
                if (amount <= 0)
                    return;
            }
        }
    }

    public static int getItemAmount(Player p, ItemStack item) {
        ItemStack[] itemStacks = p.getInventory().getContents();
        int amount = 0;
        for (ItemStack is : itemStacks) {
            if (is != null && is.isSimilar(item)) {
                amount += is.getAmount();
            }
        }
        return amount;
    }
}

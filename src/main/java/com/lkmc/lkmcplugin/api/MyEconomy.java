package com.lkmc.lkmcplugin.api;

import com.earth2me.essentials.api.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.util.Collections;

public class MyEconomy {
    public static void giveMoney(Player p, int amount) {
        try {
            Economy.setMoney(p.getUniqueId(), Economy.getMoneyExact(p.getUniqueId()).add(BigDecimal.valueOf(amount)));
        } catch (Exception e) {
            p.sendMessage("给予金币时出现错误");
            ItemStack error = new ItemStack(Material.PAPER);
            ItemMeta errorItemMeta = error.getItemMeta();
            errorItemMeta.setDisplayName(amount + "$债券");
            errorItemMeta.setLore(Collections.singletonList("请找腐竹兑换"));
            error.setItemMeta(errorItemMeta);
            p.getInventory().addItem(error);
        }
    }

    public static void giveMoney(Player p, double amount) {
        try {
            Economy.setMoney(p.getUniqueId(), Economy.getMoneyExact(p.getUniqueId()).add(BigDecimal.valueOf(amount)));
        } catch (Exception e) {
            p.sendMessage("给予金币时出现错误");
            ItemStack error = new ItemStack(Material.PAPER);
            ItemMeta errorItemMeta = error.getItemMeta();
            errorItemMeta.setDisplayName(amount + "$债券");
            errorItemMeta.setLore(Collections.singletonList("请找腐竹兑换"));
            error.setItemMeta(errorItemMeta);
            p.getInventory().addItem(error);
        }
    }

    public static boolean takeMoney(Player p, int amount) {
        try {
            if (Economy.getMoneyExact(p.getUniqueId()).compareTo(BigDecimal.valueOf(amount)) < 0) {
                p.sendMessage("没有足够的金币");
                return false;
            }
            Economy.setMoney(p.getUniqueId(), Economy.getMoneyExact(p.getUniqueId()).subtract(BigDecimal.valueOf(amount)));
            return true;
        } catch (Exception e) {
            p.sendMessage("结算金币时出现错误");
            return false;
        }
    }
}

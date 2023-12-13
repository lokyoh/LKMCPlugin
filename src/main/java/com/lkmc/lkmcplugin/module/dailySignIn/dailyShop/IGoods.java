package com.lkmc.lkmcplugin.module.dailySignIn.dailyShop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IGoods {
    boolean check();

    ItemStack showGoods();

    void buyGoods(Player p);
}
package com.lkmc.lkmcplugin.module.draw;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IPrizeBase {
    int getRate();

    ItemStack win(Player p);
}

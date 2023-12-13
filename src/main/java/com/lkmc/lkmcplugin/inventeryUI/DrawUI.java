package com.lkmc.lkmcplugin.inventeryUI;

import com.lkmc.lkmcplugin.item.UIItem;
import com.lkmc.lkmcplugin.module.draw.DrawBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DrawUI {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6抽奖界面");
        //添加物品
        for (int i = 1; i < 26; i++) {
            inv.setItem(i, UIItem.get("blank"));
        }
        inv.setItem(0, DrawBase.getPrizeList());
        inv.setItem(21, UIItem.get("draw_once"));
        inv.setItem(23, UIItem.get("draw_ten_times"));
        inv.setItem(26, UIItem.get("exit"));
        //打开UI
        p.openInventory(inv);
    }
}

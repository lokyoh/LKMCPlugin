package com.lkmc.lkmcplugin.inventeryUI;

import com.lkmc.lkmcplugin.item.UIItem;
import com.lkmc.lkmcplugin.module.systemShop.SystemShopBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LKShopUI {
    public static void showBuy(Player p, int page) {
    }

    public static void showSell(Player p, int page) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6系统收购商店 第" + page + "页");
        //添加物品
        for (int i = 0; i < Math.min(SystemShopBase.goods.size() - (page - 1) * 45, 45); i++) {
            inv.setItem(i, SystemShopBase.goods.get((page - 1) * 45 + i).showGoods());
        }
        for (int i = 45; i < 53; i++) {
            inv.setItem(i, UIItem.get("blank"));
        }
        inv.setItem(45, UIItem.getCanSell(p));
        inv.setItem(46, UIItem.get("shop_tips"));
        if (page > 1) {
            inv.setItem(48, UIItem.get("previous_page"));
        }
        inv.setItem(49, UIItem.get("fist_page"));
        if (SystemShopBase.goods.size() - (page - 1) * 36 > 0) {
            inv.setItem(50, UIItem.get("next_page"));
        }
        inv.setItem(53, UIItem.get("exit"));
        //打开UI
        p.openInventory(inv);
    }

    public static void chooseSellAmount(Player p, int index) {
        ItemStack tag = new ItemStack(UIItem.getGoodsTag(index));
        Inventory inv = Bukkit.createInventory(null, 18, "§6选择卖出的数量");
        for (int i = 1; i < 17; i++) {
            inv.setItem(i, UIItem.get("blank"));
        }
        inv.setItem(0, tag);
        inv.setItem(4, SystemShopBase.goods.get(index).getGoods());
        inv.setItem(11, UIItem.get("sell1"));
        inv.setItem(12, UIItem.get("sell8"));
        inv.setItem(13, UIItem.get("sell16"));
        inv.setItem(14, UIItem.get("sell32"));
        inv.setItem(15, UIItem.get("sell64"));
        inv.setItem(17, UIItem.get("exit"));
        p.openInventory(inv);
    }
}

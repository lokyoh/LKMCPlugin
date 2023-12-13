package com.lkmc.lkmcplugin.module.dailySignIn.dailyShop;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.MyEconomy;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class GoodsMoney implements IGoods {
    protected int amount;
    protected int price;
    protected ItemStack goods;

    public GoodsMoney(int amount, int price) {
        this.amount = amount;
        this.price = price;
    }

    @Override
    public boolean check() {
        if (amount <= 0) {
            LKMCP.printLog("数量" + amount + "无效");
            return false;
        }
        if (price <= 0) {
            LKMCP.printLog("价格" + price + "无效");
            return false;
        }
        goods = new ItemStack(Material.PAPER);
        ItemMeta meta = goods.getItemMeta();
        if (meta != null && meta.getLore() != null) {
            List<String> list = meta.getLore();
            list.add("售价:" + price);
            meta.setLore(list);
        } else {
            meta.setLore(Collections.singletonList("售价:" + price));
        }
        meta.setDisplayName("金币 " + amount + " $");
        goods.setItemMeta(meta);
        return true;
    }

    @Override
    public ItemStack showGoods() {
        return goods;
    }

    @Override
    public void buyGoods(Player p) {
        if (DailySignInBase.dailyPoint.get(p.getUniqueId()) >= price) {
            try {
                DailySignInBase.usePoint(p, price);
                MyEconomy.giveMoney(p, amount);
            } catch (Exception e) {
                p.sendMessage("出现错误");
                LKMCP.printLog(p.getDisplayName() + "消费" + price + "时出现错误");
            }
        } else
            p.sendMessage("积分不足");
    }
}

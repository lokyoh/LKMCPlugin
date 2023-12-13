package com.lkmc.lkmcplugin.module.dailySignIn.dailyShop;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.MyInventory;
import com.lkmc.lkmcplugin.item.MyItem;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class GoodsLKItem implements IGoods {
    protected String id;
    protected int amount;
    protected int price;
    protected ItemStack goods;

    public GoodsLKItem(String id, int amount, int price) {
        this.id = id;
        this.amount = amount;
        this.price = price;
    }

    @Override
    public boolean check() {
        if (!MyItem.MyItems.containsKey(id)) {
            LKMCP.printLog("找不到物品" + id);
            return false;
        }
        if (amount <= 0 || amount > 64) {
            LKMCP.printLog("数量" + amount + "无效或大于一组，目前不支持物品数量超过一组");
            return false;
        }
        if (price <= 0) {
            LKMCP.printLog("价格" + price + "无效");
            return false;
        }
        goods = new ItemStack(MyItem.get(id));
        goods.setAmount(amount);
        return true;
    }

    @Override
    public ItemStack showGoods() {
        ItemStack show = new ItemStack(goods);
        ItemMeta goodsMeta = show.getItemMeta();
        if (goodsMeta != null && goodsMeta.getLore() != null) {
            List<String> list = goodsMeta.getLore();
            list.add("售价:" + price);
            goodsMeta.setLore(list);
        } else {
            goodsMeta.setLore(Collections.singletonList("售价:" + price));
        }
        show.setItemMeta(goodsMeta);
        return show;
    }

    @Override
    public void buyGoods(Player p) {
        if (DailySignInBase.dailyPoint.get(p.getUniqueId()) >= price) {
            if (!MyInventory.hasEmpty(p)) {
                p.sendMessage("背包空间不足");
                return;
            }
            try {
                DailySignInBase.usePoint(p, price);
                p.getInventory().addItem(goods);
            } catch (Exception e) {
                p.sendMessage("出现错误");
                LKMCP.printLog(p.getDisplayName() + "消费" + price + "时出现错误");
            }
        } else
            p.sendMessage("积分不足");
    }
}

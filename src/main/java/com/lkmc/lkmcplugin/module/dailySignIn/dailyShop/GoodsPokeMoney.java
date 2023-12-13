package com.lkmc.lkmcplugin.module.dailySignIn.dailyShop;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class GoodsPokeMoney extends GoodsMoney implements IGoods {

    public GoodsPokeMoney(int amount, int price) {
        super(amount, price);
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
        goods = new ItemStack(Material.getMaterial("PIXELMON_RELIC_GOLD"));
        ItemMeta meta = goods.getItemMeta();
        if (meta != null && meta.getLore() != null) {
            List<String> list = meta.getLore();
            list.add("售价:" + price);
            meta.setLore(list);
        } else {
            meta.setLore(Collections.singletonList("售价:" + price));
        }
        meta.setDisplayName("宝可币 " + amount);
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
                MyPokemon.givePokeMoney(p, amount);
            } catch (Exception e) {
                p.sendMessage("出现错误");
                LKMCP.printLog(p.getDisplayName() + "消费" + price + "时出现错误");
            }
        } else
            p.sendMessage("积分不足");
    }
}

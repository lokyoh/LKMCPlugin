package com.lkmc.lkmcplugin.module.dailySignIn.dailyShop;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.MyInventory;
import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBall;
import com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBallRegistry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class GoodsPokeBall implements IGoods {
    protected String id;
    protected int amount;
    protected int price;
    ItemStack goods;

    public GoodsPokeBall(String id, int amount, int price) {
        this.id = id;
        this.amount = amount;
        this.price = price;
    }

    @Override
    public boolean check() {
        if (amount <= 0 || amount > 64) {
            LKMCP.printLog("数量" + amount + "无效或大于一组，目前不支持物品数量超过一组");
        }
        if (price <= 0) {
            LKMCP.printLog("价格" + price + "无效");
            return false;
        }
        for (PokeBall pokeBall : PokeBallRegistry.getAll()) {
            if (pokeBall.getName().equals(id)) {
                goods = MyPokemon.getPokeBall(id);
                goods.setAmount(amount);
                return true;
            }
        }
        LKMCP.printLog("找不到宝可球" + id);
        return false;
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

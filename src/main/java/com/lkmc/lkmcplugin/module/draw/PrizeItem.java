package com.lkmc.lkmcplugin.module.draw;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PrizeItem implements IPrizeBase {
    protected int rate;
    protected String id;
    protected int amount;

    public PrizeItem(int rate, String id, int amount) {
        this.rate = rate;
        this.id = id;
        this.amount = amount;
        if (Material.getMaterial(id.toUpperCase()) == null)
            throw new RuntimeException("找不到物品" + id);
        if (amount <= 0 || amount > 64)
            throw new RuntimeException("数量" + amount + "无效或大于一组，目前不支持物品数量超过一组");
    }

    public int getRate() {
        return rate;
    }

    @Override
    public ItemStack win(Player p) {
        ItemStack show = new ItemStack(Material.getMaterial(id.toUpperCase()), amount);
        p.getInventory().addItem(show);
        return show;
    }
}
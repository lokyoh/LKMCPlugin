package com.lkmc.lkmcplugin.module.draw;

import com.lkmc.lkmcplugin.item.MyItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PrizeLKItem implements IPrizeBase {
    int rate;
    String id;
    int amount;

    public PrizeLKItem(int rate, String id, int amount) {
        this.rate = rate;
        this.id = id;
        this.amount = amount;
        if (!MyItem.MyItems.containsKey(id))
            throw new RuntimeException("找不到物品" + id);
        if (amount <= 0 || amount > 64)
            throw new RuntimeException("数量" + amount + "无效或大于一组，目前不支持物品数量超过一组");
    }

    @Override
    public int getRate() {
        return rate;
    }

    @Override
    public ItemStack win(Player p) {
        ItemStack show = new ItemStack(MyItem.get(id));
        show.setAmount(amount);
        p.getInventory().addItem(show);
        return show;
    }
}

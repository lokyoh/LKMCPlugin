package com.lkmc.lkmcplugin.module.draw;

import com.lkmc.lkmcplugin.api.MyEconomy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrizeMoney implements IPrizeBase {
    protected int rate;
    protected int amount;

    public PrizeMoney(int rate, int amount) {
        this.rate = rate;
        this.amount = amount;
        if (amount <= 0)
            throw new RuntimeException("数量" + amount + "无效");
    }

    @Override
    public int getRate() {
        return rate;
    }

    @Override
    public ItemStack win(Player p) {
        ItemStack show = new ItemStack(Material.PAPER);
        ItemMeta showMeta = show.getItemMeta();
        showMeta.setDisplayName("获得金币 " + amount + " $");
        show.setItemMeta(showMeta);
        MyEconomy.giveMoney(p, amount);
        p.sendMessage("获得的金币已经自动打入您的账户");
        return show;
    }
}

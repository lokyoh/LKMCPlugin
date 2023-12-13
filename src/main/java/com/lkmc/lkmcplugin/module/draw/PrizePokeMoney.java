package com.lkmc.lkmcplugin.module.draw;

import com.lkmc.lkmcplugin.api.MyPokemon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrizePokeMoney extends PrizeMoney implements IPrizeBase {

    public PrizePokeMoney(int rate, int amount) {
        super(rate, amount);
    }

    @Override
    public ItemStack win(Player p) {
        ItemStack show = new ItemStack(Material.getMaterial("PIXELMON_RELIC_GOLD"));
        ItemMeta showMeta = show.getItemMeta();
        showMeta.setDisplayName("获得宝可币 " + amount);
        show.setItemMeta(showMeta);
        MyPokemon.givePokeMoney(p, amount);
        p.sendMessage("获得的宝可币已经自动打入您的账户");
        return show;
    }
}

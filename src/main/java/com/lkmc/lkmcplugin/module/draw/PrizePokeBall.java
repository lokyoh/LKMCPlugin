package com.lkmc.lkmcplugin.module.draw;

import com.lkmc.lkmcplugin.api.MyPokemon;
import com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBall;
import com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBallRegistry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PrizePokeBall implements IPrizeBase {
    protected int rate;
    protected String id;
    protected int amount;

    public PrizePokeBall(int rate, String id, int amount) {
        this.rate = rate;
        this.id = id;
        this.amount = amount;
        if (amount <= 0 || amount > 64)
            throw new RuntimeException("数量" + amount + "无效或大于一组，目前不支持物品数量超过一组");
        for (PokeBall pokeBall : PokeBallRegistry.getAll()) {
            if (pokeBall.getName().equals(id))
                return;
        }
        throw new RuntimeException("找不到宝可球" + id);
    }

    @Override
    public int getRate() {
        return rate;
    }

    @Override
    public ItemStack win(Player p) {
        ItemStack show = MyPokemon.getPokeBall(id);
        show.setAmount(amount);
        p.getInventory().addItem(show);
        return show;
    }
}

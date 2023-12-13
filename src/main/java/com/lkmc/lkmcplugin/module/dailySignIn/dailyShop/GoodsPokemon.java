package com.lkmc.lkmcplugin.module.dailySignIn.dailyShop;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class GoodsPokemon implements IGoods {
    protected String species;
    protected String palette;
    protected int price;
    protected ItemStack goods;

    public GoodsPokemon(String species, String palette, int price) {
        this.species = species;
        this.palette = palette;
        this.price = price;
    }

    @Override
    public boolean check() {
        if (!PixelmonSpecies.has(species.toUpperCase())) {
            LKMCP.printLog("找不到宝可梦" + species);
            return false;
        }
        if (price <= 0) {
            LKMCP.printLog("价格" + price + "无效");
            return false;
        }
        Pokemon pokemon = MyPokemon.stringToPokemon(species);
        pokemon.setPalette(palette);
        goods = MyPokemon.getPokemonSprite(pokemon);
        ItemMeta goodsMeta = goods.getItemMeta();
        if (goodsMeta != null && goodsMeta.getLore() != null) {
            List<String> list = goodsMeta.getLore();
            list.add("售价:" + price);
            goodsMeta.setLore(list);
        } else {
            goodsMeta.setLore(Collections.singletonList("售价:" + price));
        }
        goods.setItemMeta(goodsMeta);
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
                Pokemon pokemon = MyPokemon.stringToPokemon(species);
                pokemon.setPalette(palette);
                MyPokemon.givePokemon(p, pokemon);
            } catch (Exception e) {
                p.sendMessage("出现错误");
                LKMCP.printLog(p.getDisplayName() + "消费" + price + "时出现错误");
            }
        } else
            p.sendMessage("积分不足");
    }
}

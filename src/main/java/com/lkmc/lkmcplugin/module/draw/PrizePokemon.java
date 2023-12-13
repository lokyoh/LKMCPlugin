package com.lkmc.lkmcplugin.module.draw;

import com.lkmc.lkmcplugin.api.MyPokemon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PrizePokemon implements IPrizeBase {
    protected int rate;
    protected String species;
    protected String palette;

    public PrizePokemon(int rate, String species, String palette) {
        this.rate = rate;
        this.species = species;
        this.palette = palette;
        if (!PixelmonSpecies.has(species.toUpperCase()))
            throw new RuntimeException("找不到宝可梦" + species);
    }

    public int getRate() {
        return rate;
    }

    @Override
    public ItemStack win(Player p) {
        Pokemon pokemon = MyPokemon.stringToPokemon(species);
        pokemon.setPalette(palette);
        MyPokemon.givePokemon(p, pokemon);
        p.sendMessage("获得的宝可梦已经自动存放在PC中");
        ItemStack show = MyPokemon.getPokemonSprite(pokemon);
        return show;
    }
}

package com.lkmc.lkmcplugin.api;

import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;
import com.pixelmonmod.pixelmon.api.item.JsonItemStack;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.Moveset;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MyPokemon {

    public static Pokemon stringToPokemon(String species) {
        PokemonSpecification spec = PokemonSpecificationProxy.create(species);
        return spec.create();
    }

    public static ItemStack getPokemonSprite(String name) {
        return CraftItemStack.asBukkitCopy(SpriteItemHelper.getPhoto(stringToPokemon(name)));
    }

    public static ItemStack getPokemonSprite(Pokemon pokemon) {
        return CraftItemStack.asBukkitCopy(SpriteItemHelper.getPhoto(pokemon));
    }

    public static void givePokeMoney(Player p, int amount) {
        BankAccount account = BankAccountProxy.getBankAccountUnsafe(p.getUniqueId());
        account.add(amount);
    }

    public static void givePokemon(Player p, String species) {
        StorageProxy.getPCForPlayer(p.getUniqueId()).add(stringToPokemon(species));
    }

    public static void givePokemon(Player p, Pokemon pokemon) {
        StorageProxy.getPCForPlayer(p.getUniqueId()).add(pokemon);
    }

    public static PlayerPartyStorage getPlayerPartyStorage(Player p) {
        return StorageProxy.getParty(p.getUniqueId());
    }

    public static ItemStack getPokemonIcon(Pokemon pokemon) {
        ItemStack icon = getPokemonSprite(pokemon);
        ItemMeta iconItemMeta = icon.getItemMeta();
        iconItemMeta.setDisplayName(pokemon.getNickname());
        icon.setItemMeta(iconItemMeta);
        return icon;
    }

    public static ItemStack getPokeBall(String id) {
        JsonItemStack jsonItemStack = new JsonItemStack();
        jsonItemStack.itemID = "pixelmon:poke_ball";
        jsonItemStack.nbt = "{PokeBallID:" + id + "}";
        return CraftItemStack.asBukkitCopy(jsonItemStack.getItemStack());
    }

    public static void changeMove(PixelmonWrapper pokemon) {
        Moveset move = pokemon.getMoveset();
        Attack[] attacks = move.attacks;
        int index = -1;
        for (int i = 0; i < move.size(); i++) {
            if (attacks[i] != null && attacks[i].isAttack(new Attack("Behemoth Blade").getMove())) {
                index = -1;
                break;
            }
            if (attacks[i] != null && attacks[i].isAttack(new Attack("Iron Head").getMove())) {
                index = i;
            }
        }
        if (index >= 0) {
            Attack attack = new Attack("Behemoth Blade");
            attack.pp = move.get(index).pp / 3;
            attack.ppLevel = move.get(index).ppLevel;
            move.set(index, attack);
        }
    }

    public static void changeBackMove(PixelmonWrapper pokemon) {
        Moveset move = pokemon.getMoveset();
        Attack[] attacks = move.attacks;
        int index = -1;
        for (int i = 0; i < move.size(); i++) {
            if (attacks[i] != null && attacks[i].isAttack(new Attack("Behemoth Blade").getMove())) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            Attack attack = new Attack("Iron Head");

            attack.pp = move.get(index).pp * 3;
            attack.ppLevel = move.get(index).ppLevel;
            move.set(index, attack);
        }
    }

    public static void mega(PixelmonWrapper pokemon) {
        Moveset move = pokemon.getMoveset();
        Attack[] attacks = move.attacks;
        for (int i = 0; i < move.size(); i++) {
            if (attacks[i] != null && attacks[i].isAttack(new Attack("Dragon Ascent").getMove())) {
                pokemon.setForm("mega");
                return;
            }
        }
    }
}

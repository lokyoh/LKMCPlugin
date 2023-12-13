package com.lkmc.lkmcplugin.inventeryUI;

import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.item.UIItem;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShinyTicketUI {
    public static void show(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6闪光修改界面");
        PlayerPartyStorage playerPartyStorage = MyPokemon.getPlayerPartyStorage(player);
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, UIItem.get("blank"));
        }
        inv.setItem(26, UIItem.get("exit"));
        //普通
        ItemStack none = new ItemStack(Material.LIME_WOOL);
        ItemMeta noneMete = none.getItemMeta();
        noneMete.setDisplayName("点击变成闪光");
        none.setItemMeta(noneMete);
        //闪光或特殊材质
        ItemStack special = new ItemStack(Material.RED_WOOL);
        ItemMeta specialMeta = special.getItemMeta();
        specialMeta.setDisplayName("已经是闪光或特殊材质了");
        special.setItemMeta(specialMeta);
        //宝可梦1
        Pokemon pokemon1 = playerPartyStorage.get(0);
        if (pokemon1 != null) {
            inv.setItem(2, MyPokemon.getPokemonIcon(pokemon1));
            if (pokemon1.isPalette("none"))
                inv.setItem(3, none);
            else
                inv.setItem(3, special);
        } else {
            inv.setItem(2, UIItem.get("pokemon_none_icon"));
            inv.setItem(3, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦2
        Pokemon pokemon2 = playerPartyStorage.get(1);
        if (pokemon2 != null) {
            inv.setItem(5, MyPokemon.getPokemonIcon(pokemon2));
            if (pokemon2.isPalette("none"))
                inv.setItem(6, none);
            else
                inv.setItem(6, special);
        } else {
            inv.setItem(5, UIItem.get("pokemon_none_icon"));
            inv.setItem(6, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦3
        Pokemon pokemon3 = playerPartyStorage.get(2);
        if (pokemon3 != null) {
            inv.setItem(11, MyPokemon.getPokemonIcon(pokemon3));
            if (pokemon3.isPalette("none"))
                inv.setItem(12, none);
            else
                inv.setItem(12, special);
        } else {
            inv.setItem(11, UIItem.get("pokemon_none_icon"));
            inv.setItem(12, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦4
        Pokemon pokemon4 = playerPartyStorage.get(3);
        if (pokemon4 != null) {
            inv.setItem(14, MyPokemon.getPokemonIcon(pokemon4));
            if (pokemon4.isPalette("none"))
                inv.setItem(15, none);
            else
                inv.setItem(15, special);
        } else {
            inv.setItem(14, UIItem.get("pokemon_none_icon"));
            inv.setItem(15, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦5
        Pokemon pokemon5 = playerPartyStorage.get(4);
        if (pokemon5 != null) {
            inv.setItem(20, MyPokemon.getPokemonIcon(pokemon5));
            if (pokemon5.isPalette("none"))
                inv.setItem(21, none);
            else
                inv.setItem(21, special);
        } else {
            inv.setItem(20, UIItem.get("pokemon_none_icon"));
            inv.setItem(21, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦6
        Pokemon pokemon6 = playerPartyStorage.get(5);
        if (pokemon6 != null) {
            inv.setItem(23, MyPokemon.getPokemonIcon(pokemon6));
            if (pokemon6.isPalette("none"))
                inv.setItem(24, none);
            else
                inv.setItem(24, special);
        } else {
            inv.setItem(23, UIItem.get("pokemon_none_icon"));
            inv.setItem(24, UIItem.get("pokemon_none_icon"));
        }
        player.openInventory(inv);
    }
}

package com.lkmc.lkmcplugin.inventeryUI;

import com.lkmc.lkmcplugin.api.ItemBuilder;
import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.item.UIItem;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class GrowthTicketUI {
    public static void show(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6体型修改界面");
        PlayerPartyStorage playerPartyStorage = MyPokemon.getPlayerPartyStorage(player);
        ItemStack microscopic = ItemBuilder.buildItem("PIXELMON_XS_EXP_CANDY", "袖珍", Collections.singletonList("点击变为袖珍体型"));
        ItemStack pygmy = ItemBuilder.buildItem("PIXELMON_XS_EXP_CANDY", "迷你", Collections.singletonList("点击变为迷你体型"));
        ItemStack runt = ItemBuilder.buildItem("PIXELMON_S_EXP_CANDY", "侏儒", Collections.singletonList("点击变为侏儒体型"));
        ItemStack small = ItemBuilder.buildItem("PIXELMON_S_EXP_CANDY", "娇小", Collections.singletonList("点击变为娇小体型"));
        ItemStack ordinary = ItemBuilder.buildItem("PIXELMON_M_EXP_CANDY", "普通", Collections.singletonList("点击变为普通体型"));
        ItemStack huge = ItemBuilder.buildItem("PIXELMON_L_EXP_CANDY", "高大", Collections.singletonList("点击变为高大体型"));
        ItemStack giant = ItemBuilder.buildItem("PIXELMON_L_EXP_CANDY", "巨人", Collections.singletonList("点击变为巨人体型"));
        ItemStack enormous = ItemBuilder.buildItem("PIXELMON_XL_EXP_CANDY", "庞大", Collections.singletonList("点击变为庞大体型"));
        ItemStack ginormous = ItemBuilder.buildItem("PIXELMON_XL_EXP_CANDY", "巨大", Collections.singletonList("点击变为巨大体型"));
        for (int i = 0; i < 6; i++) {
            Pokemon pokemon = playerPartyStorage.get(i);
            if (pokemon != null) {
                inv.setItem(i * 9, microscopic);
                inv.setItem(1 + i * 9, pygmy);
                inv.setItem(2 + i * 9, runt);
                inv.setItem(3 + i * 9, small);
                inv.setItem(4 + i * 9, ordinary);
                inv.setItem(5 + i * 9, huge);
                inv.setItem(6 + i * 9, giant);
                inv.setItem(7 + i * 9, enormous);
                inv.setItem(8 + i * 9, ginormous);
                inv.setItem(pokemon.getGrowth().index == 8 ? i * 9 : pokemon.getGrowth().index + 1 + i * 9, MyPokemon.getPokemonIcon(pokemon));
            } else {
                inv.setItem(i * 9, UIItem.get("pokemon_none_icon"));
                inv.setItem(1 + i * 9, UIItem.get("pokemon_none_icon"));
                inv.setItem(2 + i * 9, UIItem.get("pokemon_none_icon"));
                inv.setItem(3 + i * 9, UIItem.get("pokemon_none_icon"));
                inv.setItem(4 + i * 9, UIItem.get("pokemon_none_icon"));
                inv.setItem(5 + i * 9, UIItem.get("pokemon_none_icon"));
                inv.setItem(6 + i * 9, UIItem.get("pokemon_none_icon"));
                inv.setItem(7 + i * 9, UIItem.get("pokemon_none_icon"));
                inv.setItem(8 + i * 9, UIItem.get("pokemon_none_icon"));
            }
        }
        player.openInventory(inv);
    }
}

package com.lkmc.lkmcplugin.inventeryUI;

import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.item.UIItem;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.gender.Gender;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class GenderTicketUI {
    public static void show(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6性别修改界面");
        PlayerPartyStorage playerPartyStorage = MyPokemon.getPlayerPartyStorage(player);
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, UIItem.get("blank"));
        }
        inv.setItem(26, UIItem.get("exit"));
        Map<Gender, ItemStack> gender = new HashMap<>();
        //雄性
        ItemStack male = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta maleMeta = male.getItemMeta();
        maleMeta.setDisplayName("雄性,点击变性");
        male.setItemMeta(maleMeta);
        gender.put(Gender.MALE, male);
        //雌性
        ItemStack female = new ItemStack(Material.PINK_WOOL);
        ItemMeta femaleMeta = female.getItemMeta();
        femaleMeta.setDisplayName("雌性,点击变性");
        female.setItemMeta(femaleMeta);
        gender.put(Gender.FEMALE, female);
        //无性
        ItemStack none = new ItemStack(Material.GRAY_WOOL);
        ItemMeta noneMeta = none.getItemMeta();
        noneMeta.setDisplayName("无性别,点击无效");
        none.setItemMeta(noneMeta);
        gender.put(Gender.NONE, none);
        //宝可梦1
        Pokemon pokemon1 = playerPartyStorage.get(0);
        if (pokemon1 != null) {
            inv.setItem(2, MyPokemon.getPokemonIcon(pokemon1));
            inv.setItem(3, gender.get(pokemon1.getGender()));
        } else {
            inv.setItem(2, UIItem.get("pokemon_none_icon"));
            inv.setItem(3, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦2
        Pokemon pokemon2 = playerPartyStorage.get(1);
        if (pokemon2 != null) {
            inv.setItem(5, MyPokemon.getPokemonIcon(pokemon2));
            inv.setItem(6, gender.get(pokemon2.getGender()));
        } else {
            inv.setItem(5, UIItem.get("pokemon_none_icon"));
            inv.setItem(6, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦3
        Pokemon pokemon3 = playerPartyStorage.get(2);
        if (pokemon3 != null) {
            inv.setItem(11, MyPokemon.getPokemonIcon(pokemon3));
            inv.setItem(12, gender.get(pokemon3.getGender()));
        } else {
            inv.setItem(11, UIItem.get("pokemon_none_icon"));
            inv.setItem(12, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦4
        Pokemon pokemon4 = playerPartyStorage.get(3);
        if (pokemon4 != null) {
            inv.setItem(14, MyPokemon.getPokemonIcon(pokemon4));
            inv.setItem(15, gender.get(pokemon4.getGender()));
        } else {
            inv.setItem(14, UIItem.get("pokemon_none_icon"));
            inv.setItem(15, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦5
        Pokemon pokemon5 = playerPartyStorage.get(4);
        if (pokemon5 != null) {
            inv.setItem(20, MyPokemon.getPokemonIcon(pokemon5));
            inv.setItem(21, gender.get(pokemon5.getGender()));
        } else {
            inv.setItem(20, UIItem.get("pokemon_none_icon"));
            inv.setItem(21, UIItem.get("pokemon_none_icon"));
        }
        //宝可梦6
        Pokemon pokemon6 = playerPartyStorage.get(5);
        if (pokemon6 != null) {
            inv.setItem(23, MyPokemon.getPokemonIcon(pokemon6));
            inv.setItem(24, gender.get(pokemon6.getGender()));
        } else {
            inv.setItem(23, UIItem.get("pokemon_none_icon"));
            inv.setItem(24, UIItem.get("pokemon_none_icon"));
        }
        player.openInventory(inv);
    }
}

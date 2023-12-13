package com.lkmc.lkmcplugin.item;

import com.lkmc.lkmcplugin.api.ItemBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MyItem {
    public static Map<String, ItemStack> MyItems = new HashMap<>();

    public static void init() {
        // ticket
        MyItems.put("ticket", ItemBuilder.buildItem(
                Material.PAPER,
                "§6抽奖券", Arrays.asList("§7腐竹下发的抽奖券", "右键打开抽奖界面")
        ));
        // shiny_ticket
        MyItems.put("shiny_ticket", ItemBuilder.buildItem(
                Material.PAPER, "§6闪光券",
                Arrays.asList("§f可以将宝可梦变为闪光宝可梦", "右键进入选择界面"),
                Collections.singletonList(Enchantment.LOOT_BONUS_BLOCKS)
        ));
        // gender_ticket
        MyItems.put("gender_ticket", ItemBuilder.buildItem(
                Material.PAPER,
                "§6变性券",
                Arrays.asList("§f可以改变宝可梦的性别,然后§k§m尽情玩耍", "右键进入选择界面"),
                Arrays.asList(Enchantment.QUICK_CHARGE, Enchantment.DURABILITY, Enchantment.ARROW_INFINITE)
        ));
        // supplement_card
        MyItems.put("supplement_card", ItemBuilder.buildItem(
                Material.PAPER,
                "§6补签卡",
                Arrays.asList("§f可以进行补签，但不能增加连续签到天数", "补签时放在背包里"),
                Collections.singletonList(Enchantment.LOOT_BONUS_BLOCKS)
        ));
        // growth_ticket
        MyItems.put("growth_ticket", ItemBuilder.buildItem(
                Material.PAPER,
                "§6体型券",
                Arrays.asList("§f可以改变宝可梦的体型", "右键进入选择界面"),
                Collections.singletonList(Enchantment.DURABILITY)
        ));
        //
    }

    public static ItemStack get(String name) {
        return MyItems.get(name);
    }

    public static ItemMeta getMeta(String name) {
        return MyItems.get(name).getItemMeta();
    }
}

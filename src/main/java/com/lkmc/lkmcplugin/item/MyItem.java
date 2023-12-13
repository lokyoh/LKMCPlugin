package com.lkmc.lkmcplugin.item;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyItem {
    public static Map<String, ItemStack> MyItems = new HashMap<>();

    public static void init() {
        // ticket
        ItemStack ticket = new ItemStack(Material.PAPER);
        ItemMeta ticketItemMeta = ticket.getItemMeta();
        ticketItemMeta.setDisplayName("§6抽奖券");
        ticketItemMeta.setLore(Arrays.asList("§7腐竹下发的抽奖券", "右键打开抽奖界面"));
        ticket.setItemMeta(ticketItemMeta);
        MyItems.put("ticket", ticket);
        // shiny_ticket
        ItemStack shinyTicket = new ItemStack(Material.PAPER);
        ItemMeta shinyTicketItemMeta = shinyTicket.getItemMeta();
        shinyTicketItemMeta.setDisplayName("§6闪光券");
        shinyTicketItemMeta.setLore(Arrays.asList("§f可以将宝可梦变为闪光宝可梦", "右键进入选择界面"));
        shinyTicketItemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
        shinyTicket.setItemMeta(shinyTicketItemMeta);
        MyItems.put("shiny_ticket", shinyTicket);
        // gender_ticket
        ItemStack genderTicket = new ItemStack(Material.PAPER);
        ItemMeta genderTicketItemMeta = genderTicket.getItemMeta();
        genderTicketItemMeta.setDisplayName("§6变性券");
        genderTicketItemMeta.setLore(Arrays.asList("§f可以改变宝可梦的性别,然后§k§m尽情玩耍", "右键进入选择界面"));
        genderTicketItemMeta.addEnchant(Enchantment.QUICK_CHARGE, 10, true);
        genderTicketItemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        genderTicketItemMeta.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
        genderTicket.setItemMeta(genderTicketItemMeta);
        MyItems.put("gender_ticket", genderTicket);
        // supplement_card
        ItemStack supplementCard = new ItemStack(Material.PAPER);
        ItemMeta supplementCardItemMeta = supplementCard.getItemMeta();
        supplementCardItemMeta.setDisplayName("§6补签卡");
        supplementCardItemMeta.setLore(Arrays.asList("§f可以进行补签，但不能增加连续签到天数", "补签时放在背包里"));
        supplementCardItemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
        supplementCard.setItemMeta(supplementCardItemMeta);
        MyItems.put("supplement_card", supplementCard);
        // 
        //
    }

    public static ItemStack get(String name) {
        return MyItems.get(name);
    }

    public static ItemMeta getMeta(String name) {
        return MyItems.get(name).getItemMeta();
    }
}

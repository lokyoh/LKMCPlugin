package com.lkmc.lkmcplugin.inventeryUI;

import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.item.UIItem;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import com.lkmc.lkmcplugin.module.dailySignIn.PlayerDailySignInData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class DailySignInUI {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6每日签到界面");
        //添加物品
        for (int i = 28; i < 54; i++) {
            inv.setItem(i, UIItem.get("blank"));
        }
        PlayerDailySignInData data = DailySignInBase.dailySignInDataMap.get(p.getUniqueId());
        for (int i = 0; i < DailySignInBase.getDay() - 1; i++) {
            if (data.noSignIN(i + 1)) {
                inv.setItem(i, UIItem.getDailySignIn("daily_sign_in_past_not_attend", "第" + (i + 1) + "天"));
            } else {
                inv.setItem(i, UIItem.getDailySignIn("daily_sign_in_past_attend", "第" + (i + 1) + "天"));
            }
        }
        if (data.noSignIN(DailySignInBase.getDay())) {
            inv.setItem(DailySignInBase.getDay() - 1, UIItem.getDailySignIn("daily_sign_in_today_not_attend", "第" + DailySignInBase.getDay() + "天"));
        } else {
            inv.setItem(DailySignInBase.getDay() - 1, UIItem.getDailySignIn("daily_sign_in_today_attend", "第" + DailySignInBase.getDay() + "天"));
        }
        for (int i = DailySignInBase.getDay(); i < DailySignInBase.getDays(); i++) {
            inv.setItem(i, UIItem.getDailySignIn("daily_sign_in_future", "第" + (i + 1) + "天"));
        }
        inv.setItem(45, data.getInfo(p));
        inv.setItem(47, UIItem.get("daily_sign_in_prize"));
        inv.setItem(49, UIItem.getPoint(p));
        inv.setItem(51, UIItem.get("daily_shop"));
        inv.setItem(53, UIItem.get("exit"));
        //打开UI
        p.openInventory(inv);
    }

    public static void showShop(Player p, int page) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6每日积分商店 第" + page + "页");
        //添加物品
        for (int i = 0; i < Math.min(DailySignInBase.goods.size() - (page - 1) * 36, 36); i++) {
            inv.setItem(i, DailySignInBase.goods.get((page - 1) * 36 + i).showGoods());
        }
        for (int i = 36; i < 53; i++) {
            inv.setItem(i, UIItem.get("blank"));
        }
        inv.setItem(45, UIItem.getPoint(p));
        if (page > 1) {
            inv.setItem(48, UIItem.get("previous_page"));
        }
        inv.setItem(49, UIItem.get("fist_page"));
        if (DailySignInBase.goods.size() - (page - 1) * 36 > 0) {
            inv.setItem(50, UIItem.get("next_page"));
        }
        inv.setItem(53, UIItem.get("exit"));
        //打开UI
        p.openInventory(inv);
    }

    public static void showGift(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, "§6新手福利");
        inv.setItem(0, new ItemStack(Material.IRON_PICKAXE));
        inv.setItem(1, new ItemStack(Material.IRON_AXE));
        ItemStack res = new ItemStack(Material.WOODEN_HOE);
        ItemMeta resItemMeta = res.getItemMeta();
        resItemMeta.setDisplayName("§6圈地锄头");
        resItemMeta.setLore(Arrays.asList("左键选择第一个点，右键选择第二个点，不用在意高度，高度为正常建筑高度", "框好地后输入/res create [英文名]即可创建领地", "/res set设置领地权限", "/res add [玩家名]为领地添加玩家", "/money查看金币数量"));
        res.setItemMeta(resItemMeta);
        inv.setItem(2, res);
        ItemStack menu = new ItemStack(Material.CLOCK);
        ItemMeta menuMeta = menu.getItemMeta();
        menuMeta.setDisplayName("§6菜单");
        menuMeta.setLore(Collections.singletonList("§5§n右键打开菜单"));
        menu.setItemMeta(menuMeta);
        inv.setItem(3, menu);
        ItemStack pokeBall = MyPokemon.getPokeBall("poke_ball");
        pokeBall.setAmount(20);
        inv.setItem(4, pokeBall);
        ItemStack soup = new ItemStack(Material.getMaterial("FARMERSDELIGHT_CHICKEN_SOUP"));
        soup.setAmount(16);
        inv.setItem(5, soup);
        p.openInventory(inv);
    }
}
